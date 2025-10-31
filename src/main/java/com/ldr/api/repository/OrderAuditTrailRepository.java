package com.ldr.api.repository;

import com.ldr.api.model.OrderAuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderAuditTrailRepository extends JpaRepository<OrderAuditTrail, String> {

    /**
     * Find all OrderAuditTrail by order ID
     * @param orderId the order ID
     * @return List<OrderAuditTrail>
     */
    List<OrderAuditTrail> findByOrderDataId(String orderId);

    /**
     * Find all OrderAuditTrail by table name
     * @param tableName the table name
     * @return List<OrderAuditTrail>
     */
    List<OrderAuditTrail> findByTableName(String tableName);

    /**
     * Find all OrderAuditTrail by operation type
     * @param operationType the operation type
     * @return List<OrderAuditTrail>
     */
    List<OrderAuditTrail> findByOperationType(OrderAuditTrail.OperationType operationType);

    /**
     * Find all OrderAuditTrail by changed by user ID
     * @param changedBy the user ID who changed the data
     * @return List<OrderAuditTrail>
     */
    List<OrderAuditTrail> findByChangedById(String changedBy);

    /**
     * Find all OrderAuditTrail by changed at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAuditTrail>
     */
    List<OrderAuditTrail> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
