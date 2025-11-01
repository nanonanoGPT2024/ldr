package com.ldr.api.service;

import com.ldr.api.dto.FileUploadResponse;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.*;
import com.ldr.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderAttachmentService {

    private final OrderAttachmentRepository orderAttachmentRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final OrderAttachmentHistoryRepository orderAttachmentHistoryRepository;

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Value("${file.upload.max-size}")
    private long maxFileSize;

    @Autowired
    public OrderAttachmentService(OrderAttachmentRepository orderAttachmentRepository,
            OrderDataRepository orderDataRepository,
            UserRepository userRepository,
            DocumentRepository documentRepository,
            OrderAttachmentHistoryRepository orderAttachmentHistoryRepository) {
        this.orderAttachmentRepository = orderAttachmentRepository;
        this.orderDataRepository = orderDataRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.orderAttachmentHistoryRepository = orderAttachmentHistoryRepository;

        // Create upload directory if it doesn't exist
        try {
            if (uploadDir != null && !uploadDir.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    /**
     * Upload file and save to both order_attachment and order_attachment_history
     * tables
     * Logic: If OrderAttachment exists for the same order+document combination,
     * update it.
     * If not exists, insert new. Always insert new history record.
     *
     * @param file             the multipart file to upload
     * @param orderId          the order ID
     * @param documentId       the document ID (optional)
     * @param keterangan       description/notes for the attachment
     * @param uploaderUsername the username of the uploader
     * @return FileUploadResponse containing upload result
     */
    @Transactional
    public FileUploadResponse uploadFile(MultipartFile file, String orderId, String documentId,
            String keterangan, String uploaderUsername) {
        try {
            // Validate file
            validateFile(file);

            // Find order and user
            OrderData order = orderDataRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

            User uploader = userRepository.findByUsername(uploaderUsername)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + uploaderUsername));

            // Find document if provided
            Document document = null;
            if (documentId != null && !documentId.trim().isEmpty()) {
                document = documentRepository.findById(documentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;
            String filePath = uploadDir + uniqueFilename;

            // Save file to disk
            Path targetPath = Paths.get(filePath);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Check if attachment already exists for this order+document combination
            OrderAttachment existingAttachment = findExistingAttachment(orderId, documentId);

            OrderAttachment savedAttachment;
            ChangeType changeType;
            String changeReason;

            if (existingAttachment != null) {
                // Update existing attachment
                existingAttachment.setKeterangan(keterangan);
                existingAttachment.setFileName(uniqueFilename);
                existingAttachment.setOriginalFileName(originalFilename);
                existingAttachment.setFilePath(filePath);
                existingAttachment.setFileSize(file.getSize());
                existingAttachment.setFileType(getFileType(fileExtension));
                existingAttachment.setMimeType(file.getContentType());
                // Note: OrderAttachment doesn't have updatedBy field, only uploadedBy

                savedAttachment = orderAttachmentRepository.save(existingAttachment);
                changeType = ChangeType.UPDATED;
                changeReason = "File replaced with new upload";

                // Note: Keep old file for history download functionality
                // Old files are not deleted to maintain download capability from history
                // records
            } else {
                // Create new attachment
                OrderAttachment attachment = new OrderAttachment();
                attachment.setId(UUID.randomUUID().toString());
                attachment.setOrder(order);
                attachment.setDocument(document);
                attachment.setKeterangan(keterangan);
                attachment.setFileName(uniqueFilename);
                attachment.setOriginalFileName(originalFilename);
                attachment.setFilePath(filePath);
                attachment.setFileSize(file.getSize());
                attachment.setFileType(getFileType(fileExtension));
                attachment.setMimeType(file.getContentType());
                attachment.setUploadedBy(uploader.getId());
                attachment.setActive(true);

                savedAttachment = orderAttachmentRepository.save(attachment);
                changeType = ChangeType.CREATED;
                changeReason = "Initial file upload";
            }

            // Always create new history record
            OrderAttachmentHistory history = new OrderAttachmentHistory();
            history.setId(UUID.randomUUID().toString());
            history.setOrderAttachment(savedAttachment);
            history.setOrder(order);
            history.setFileName(uniqueFilename);
            history.setOriginalFileName(originalFilename);
            history.setFilePath(filePath);
            history.setFileSize(file.getSize());
            history.setMimeType(file.getContentType());
            history.setDocument(document);
            history.setKeterangan(keterangan);
            history.setVersionNumber(getNextVersionNumber(savedAttachment.getId()));
            history.setActive(true);
            history.setUploadedBy(uploader.getId());
            history.setUploadedAt(LocalDateTime.now());
            history.setChangeType(changeType);
            history.setChangedBy(uploader.getId());
            history.setChangedAt(LocalDateTime.now());
            history.setChangeReason(changeReason);

            orderAttachmentHistoryRepository.save(history);

            String fileUrl = "/api/files/" + uniqueFilename; // Assuming there's a file serving endpoint

            String message = existingAttachment != null ? "File updated successfully" : "File uploaded successfully";
            return new FileUploadResponse(true, message, savedAttachment, fileUrl);

        } catch (IOException e) {
            throw new ValidationException("Failed to save file: " + e.getMessage());
        } catch (Exception e) {
            throw new ValidationException("Upload failed: " + e.getMessage());
        }
    }


    /**
     * Find existing attachment for the same order and document combination
     *
     * @param orderId    the order ID
     * @param documentId the document ID (can be null)
     * @return OrderAttachment if exists, null otherwise
     */
    private OrderAttachment findExistingAttachment(String orderId, String documentId) {
        List<OrderAttachment> attachments = orderAttachmentRepository.findByOrderId(orderId);

        if (documentId != null && !documentId.trim().isEmpty()) {
            // Find attachment with matching document
            return attachments.stream()
                    .filter(att -> att.getDocument() != null && documentId.equals(att.getDocument().getId()))
                    .findFirst()
                    .orElse(null);
        } else {
            // Find attachment without document (general attachment)
            return attachments.stream()
                    .filter(att -> att.getDocument() == null)
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * Get next version number for attachment history
     *
     * @param attachmentId the attachment ID
     * @return next version number
     */
    private Long getNextVersionNumber(String attachmentId) {
        List<OrderAttachmentHistory> histories = orderAttachmentHistoryRepository.findByOrderAttachmentId(attachmentId);
        return histories.stream()
                .mapToLong(OrderAttachmentHistory::getVersionNumber)
                .max()
                .orElse(0L) + 1;
    }

    /**
     * Validate uploaded file
     *
     * @param file the multipart file
     * @throws ValidationException if validation fails
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("File cannot be empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new ValidationException("File size exceeds maximum limit of " + (maxFileSize / (1024 * 1024)) + "MB");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || filename.trim().isEmpty()) {
            throw new ValidationException("Filename cannot be empty");
        }

        String extension = getFileExtension(filename).toLowerCase();
        if (!isValidImageExtension(extension)) {
            throw new ValidationException("Only image files (jpg, jpeg, png, gif) are allowed");
        }
    }


    /**
     * Get file extension from filename
     *
     * @param filename the filename
     * @return file extension without dot
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    /**
     * Get file type based on extension
     *
     * @param extension file extension
     * @return file type description
     */
    private String getFileType(String extension) {
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "JPEG Image";
            case "png":
                return "PNG Image";
            case "gif":
                return "GIF Image";
            default:
                return "Image";
        }
    }

    /**
     * Check if file extension is valid for images
     *
     * @param extension file extension
     * @return true if valid image extension
     */
    private boolean isValidImageExtension(String extension) {
        return extension.equals("jpg") || extension.equals("jpeg") ||
                extension.equals("png") || extension.equals("gif");
    }
}
