package com.ldr.api.repository;

import com.ldr.api.model.OrderAssignmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderAssignmentHistoryRepository extends JpaRepository<OrderAssignmentHistory, String> {

    /**
     * Find all OrderAssignmentHistory by order ID
     * @param orderId the order ID
     * @return List<OrderAssignmentHistory>
     */
    List<OrderAssignmentHistory> findByOrderId(String orderId);

    /**
     * Find all OrderAssignmentHistory by assigned to user ID
     * @param assignedToId the user ID who was assigned to
     * @return List<OrderAssignmentHistory>
     */
    List<OrderAssignmentHistory> findByAssignedToId(String assignedToId);

    /**
     * Find all OrderAssignmentHistory by assigned at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAssignmentHistory>
     */
    List<OrderAssignmentHistory> findByAssignedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
