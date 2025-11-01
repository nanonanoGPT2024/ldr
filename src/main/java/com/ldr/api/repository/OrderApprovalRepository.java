package com.ldr.api.repository;

import com.ldr.api.model.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderApprovalRepository extends JpaRepository<OrderApproval, String> {

    /**
     * Find all OrderApproval by order ID
     * @param orderId the order ID
     * @return List<OrderApproval>
     */
    List<OrderApproval> findByOrderId(String orderId);

    /**
     * Find all OrderApproval by approver ID
     * @param approverId the approver ID
     * @return List<OrderApproval>
     */
    List<OrderApproval> findByApprover(String approverId);

    /**
     * Find all OrderApproval by status
     * @param status the status
     * @return List<OrderApproval>
     */
    List<OrderApproval> findByStatus(String status);

    /**
     * Find all OrderApproval by order ID and status
     * @param orderId the order ID
     * @param status the status
     * @return List<OrderApproval>
     */
    List<OrderApproval> findByOrderIdAndStatus(String orderId, String status);

    /**
     * Find all OrderApproval by approver ID and status
     * @param approverId the approver ID
     * @param status the status
     * @return List<OrderApproval>
     */
    List<OrderApproval> findByApproverAndStatus(String approverId, String status);
}
