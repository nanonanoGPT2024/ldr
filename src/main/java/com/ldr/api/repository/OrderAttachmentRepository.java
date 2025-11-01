package com.ldr.api.repository;

import com.ldr.api.model.OrderAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderAttachmentRepository extends JpaRepository<OrderAttachment, String> {

    /**
     * Find all OrderAttachment by order ID
     * @param orderId the order ID
     * @return List<OrderAttachment>
     */
    List<OrderAttachment> findByOrderId(String orderId);

    /**
     * Find all OrderAttachment by document ID
     * @param documentId the document ID
     * @return List<OrderAttachment>
     */
    List<OrderAttachment> findByDocumentId(String documentId);

    /**
     * Find all OrderAttachment by uploaded by user ID
     * @param uploadedBy the user ID who uploaded
     * @return List<OrderAttachment>
     */
    List<OrderAttachment> findByUploadedBy(String uploadedBy);

    /**
     * Find all OrderAttachment by created at between start and end date
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAttachment>
     */
    List<OrderAttachment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find all OrderAttachment by is active
     * @param isActive the active flag
     * @return List<OrderAttachment>
     */
    List<OrderAttachment> findByIsActive(boolean isActive);

    /**
     * Find all OrderAttachment by is deleted
     * @param isDeleted the deleted flag
     * @return List<OrderAttachment>
     */
    List<OrderAttachment> findByIsDeleted(boolean isDeleted);
}
