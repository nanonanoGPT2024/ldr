package com.ldr.api.repository;

import com.ldr.api.model.ChangeType;
import com.ldr.api.model.OrderAttachmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderAttachmentHistoryRepository extends JpaRepository<OrderAttachmentHistory, String> {

    /**
     * Find all OrderAttachmentHistory by order attachment ID
     * @param orderAttachmentId the order attachment ID
     * @return List<OrderAttachmentHistory>
     */
    List<OrderAttachmentHistory> findByOrderAttachmentId(String orderAttachmentId);

    /**
     * Find all OrderAttachmentHistory by order ID
     * @param orderId the order ID
     * @return List<OrderAttachmentHistory>
     */
    List<OrderAttachmentHistory> findByOrderId(String orderId);

    /**
     * Find all OrderAttachmentHistory by document ID
     * @param documentId the document ID
     * @return List<OrderAttachmentHistory>
     */
    List<OrderAttachmentHistory> findByDocumentId(String documentId);

    /**
     * Find all OrderAttachmentHistory by changed by user ID
     * @param changedBy the user ID who changed the attachment
     * @return List<OrderAttachmentHistory>
     */
    List<OrderAttachmentHistory> findByChangedById(String changedBy);

    /**
     * Find all OrderAttachmentHistory by change type
     * @param changeType the change type
     * @return List<OrderAttachmentHistory>
     */
    List<OrderAttachmentHistory> findByChangeType(ChangeType changeType);

    /**
     * Find all OrderAttachmentHistory by changed at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAttachmentHistory>
     */
    List<OrderAttachmentHistory> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
