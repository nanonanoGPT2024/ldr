package com.ldr.api.repository;

import com.ldr.api.model.OrderNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderNotificationRepository extends JpaRepository<OrderNotification, String> {

    /**
     * Find OrderNotification by order ID
     * @param orderId the order ID
     * @return List<OrderNotification>
     */
    List<OrderNotification> findByOrder_Id(String orderId);

    /**
     * Find OrderNotification by user ID
     * @param userId the user ID
     * @return List<OrderNotification>
     */
    List<OrderNotification> findByUser_Id(String userId);

    /**
     * Find OrderNotification by notification type
     * @param notificationType the notification type
     * @return List<OrderNotification>
     */
    List<OrderNotification> findByNotificationType(OrderNotification.NotificationType notificationType);

    /**
     * Find OrderNotification by read status
     * @param isRead the read status
     * @return List<OrderNotification>
     */
    List<OrderNotification> findByIsRead(boolean isRead);

    /**
     * Find OrderNotification by created at between
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderNotification>
     */
    List<OrderNotification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
