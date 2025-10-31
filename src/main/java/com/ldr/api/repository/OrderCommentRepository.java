package com.ldr.api.repository;

import com.ldr.api.model.OrderComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderCommentRepository extends JpaRepository<OrderComment, String> {

    /**
     * Find all OrderComment by order ID
     * @param orderId the order ID
     * @return List<OrderComment>
     */
    List<OrderComment> findByOrderId(String orderId);

    /**
     * Find all OrderComment by user ID
     * @param userId the user ID
     * @return List<OrderComment>
     */
    List<OrderComment> findByUserId(String userId);

    /**
     * Find all OrderComment by comment type
     * @param commentType the comment type
     * @return List<OrderComment>
     */
    List<OrderComment> findByCommentType(String commentType);

    /**
     * Find all OrderComment by created at between start and end date
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderComment>
     */
    List<OrderComment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find all OrderComment by is deleted
     * @param isDeleted the deleted flag
     * @return List<OrderComment>
     */
    List<OrderComment> findByIsDeleted(boolean isDeleted);
}
