package com.ldr.api.service;

import com.ldr.api.dto.FileUploadResponse;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.*;
import com.ldr.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
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
     * Find all OrderAttachment entities
     * 
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findAll() {
        return orderAttachmentRepository.findAll();
    }

    /**
     * Find OrderAttachment by ID
     * 
     * @param id the OrderAttachment ID
     * @return OrderAttachment
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderAttachment findById(String id) {
        return orderAttachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderAttachment not found with id: " + id));
    }

    /**
     * Save a new OrderAttachment
     * 
     * @param orderAttachment the OrderAttachment to save
     * @return saved OrderAttachment
     * @throws ValidationException if validation fails
     */
    public OrderAttachment save(OrderAttachment orderAttachment) {
        validateOrderAttachment(orderAttachment);

        try {
            return orderAttachmentRepository.save(orderAttachment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Failed to save OrderAttachment due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderAttachment
     * 
     * @param id              the OrderAttachment ID
     * @param orderAttachment the updated OrderAttachment
     * @return updated OrderAttachment
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException       if validation fails
     */
    public OrderAttachment update(String id, OrderAttachment orderAttachment) {
        OrderAttachment existingOrderAttachment = findById(id);

        validateOrderAttachment(orderAttachment);

        // Update fields
        existingOrderAttachment.setOrder(orderAttachment.getOrder());
        existingOrderAttachment.setDocument(orderAttachment.getDocument());
        existingOrderAttachment.setKeterangan(orderAttachment.getKeterangan());
        existingOrderAttachment.setFileName(orderAttachment.getFileName());
        existingOrderAttachment.setOriginalFileName(orderAttachment.getOriginalFileName());
        existingOrderAttachment.setFilePath(orderAttachment.getFilePath());
        existingOrderAttachment.setFileSize(orderAttachment.getFileSize());
        existingOrderAttachment.setFileType(orderAttachment.getFileType());
        existingOrderAttachment.setMimeType(orderAttachment.getMimeType());
        existingOrderAttachment.setUploadedBy(orderAttachment.getUploadedBy());
        existingOrderAttachment.setActive(orderAttachment.isActive());

        try {
            return orderAttachmentRepository.save(existingOrderAttachment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Failed to update OrderAttachment due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderAttachment by ID (soft delete)
     * 
     * @param id the OrderAttachment ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderAttachment orderAttachment = findById(id);
        orderAttachment.setDeleted(true);
        orderAttachment.setDeletedAt(LocalDateTime.now());
        orderAttachmentRepository.save(orderAttachment);
    }

    /**
     * Find all OrderAttachment by order ID
     * 
     * @param orderId the order ID
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByOrderId(String orderId) {
        return orderAttachmentRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderAttachment by document ID
     * 
     * @param documentId the document ID
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByDocumentId(String documentId) {
        return orderAttachmentRepository.findByDocumentId(documentId);
    }

    /**
     * Find all OrderAttachment by uploaded by user ID
     * 
     * @param uploadedBy the user ID who uploaded
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByUploadedBy(String uploadedBy) {
        return orderAttachmentRepository.findByUploadedById(uploadedBy);
    }

    /**
     * Find all OrderAttachment by created at between start and end date
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderAttachmentRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Find all OrderAttachment by is active
     * 
     * @param isActive the active flag
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByIsActive(boolean isActive) {
        return orderAttachmentRepository.findByIsActive(isActive);
    }

    /**
     * Find all OrderAttachment by is deleted
     * 
     * @param isDeleted the deleted flag
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByIsDeleted(boolean isDeleted) {
        return orderAttachmentRepository.findByIsDeleted(isDeleted);
    }

    /**
     * Validate OrderAttachment entity
     * 
     * @param orderAttachment the OrderAttachment to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderAttachment(OrderAttachment orderAttachment) {
        if (orderAttachment == null) {
            throw new ValidationException("OrderAttachment cannot be null");
        }

        if (orderAttachment.getId() == null || orderAttachment.getId().trim().isEmpty()) {
            throw new ValidationException("OrderAttachment ID is required");
        }

        if (orderAttachment.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderAttachment.getOrder().getId())
                .orElseThrow(() -> new ValidationException(
                        "Order not found with id: " + orderAttachment.getOrder().getId()));

        if (orderAttachment.getUploadedBy() == null) {
            throw new ValidationException("Uploaded by user is required");
        }

        // Validate user exists
        User user = userRepository.findById(orderAttachment.getUploadedBy().getId())
                .orElseThrow(() -> new ValidationException(
                        "User not found with id: " + orderAttachment.getUploadedBy().getId()));

        if (orderAttachment.getFileName() == null || orderAttachment.getFileName().trim().isEmpty()) {
            throw new ValidationException("File name is required");
        }

        if (orderAttachment.getFileName().length() > 255) {
            throw new ValidationException("File name cannot exceed 255 characters");
        }

        if (orderAttachment.getOriginalFileName() == null || orderAttachment.getOriginalFileName().trim().isEmpty()) {
            throw new ValidationException("Original file name is required");
        }

        if (orderAttachment.getOriginalFileName().length() > 255) {
            throw new ValidationException("Original file name cannot exceed 255 characters");
        }

        if (orderAttachment.getFilePath() == null || orderAttachment.getFilePath().trim().isEmpty()) {
            throw new ValidationException("File path is required");
        }

        if (orderAttachment.getFilePath().length() > 500) {
            throw new ValidationException("File path cannot exceed 500 characters");
        }

        if (orderAttachment.getFileType() != null && orderAttachment.getFileType().length() > 100) {
            throw new ValidationException("File type cannot exceed 100 characters");
        }

        if (orderAttachment.getMimeType() != null && orderAttachment.getMimeType().length() > 100) {
            throw new ValidationException("Mime type cannot exceed 100 characters");
        }

        if (orderAttachment.getKeterangan() != null && orderAttachment.getKeterangan().length() > 1000) {
            throw new ValidationException("Keterangan cannot exceed 1000 characters");
        }

        // Validate document if provided
        if (orderAttachment.getDocument() != null) {
            Document document = documentRepository.findById(orderAttachment.getDocument().getId())
                    .orElseThrow(() -> new ValidationException(
                            "Document not found with id: " + orderAttachment.getDocument().getId()));
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

                savedAttachment = save(existingAttachment);
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
                attachment.setUploadedBy(uploader);
                attachment.setActive(true);

                savedAttachment = save(attachment);
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
            history.setUploadedBy(uploader);
            history.setUploadedAt(LocalDateTime.now());
            history.setChangeType(changeType);
            history.setChangedBy(uploader);
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
