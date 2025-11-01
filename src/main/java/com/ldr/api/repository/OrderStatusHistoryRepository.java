package com.ldr.api.repository;

import com.ldr.api.model.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, String> {

    /**
     * Find all OrderStatusHistory by order ID
     * @param orderId the order ID
     * @return List<OrderStatusHistory>
     */
    List<OrderStatusHistory> findByOrderId(String orderId);

    /**
     * Find all OrderStatusHistory by changed by user ID
     * @param changedBy the user ID who changed the status
     * @return List<OrderStatusHistory>
     */
    List<OrderStatusHistory> findByChangedBy(String changedBy);

    /**
     * Find all OrderStatusHistory by changed at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderStatusHistory>
     */
    List<OrderStatusHistory> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
