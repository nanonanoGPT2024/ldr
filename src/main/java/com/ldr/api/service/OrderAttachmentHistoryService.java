package com.ldr.api.service;

import com.ldr.api.model.OrderAttachmentHistory;
import com.ldr.api.repository.OrderAttachmentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderAttachmentHistoryService {

    private final OrderAttachmentHistoryRepository orderAttachmentHistoryRepository;

    @Autowired
    public OrderAttachmentHistoryService(OrderAttachmentHistoryRepository orderAttachmentHistoryRepository) {
        this.orderAttachmentHistoryRepository = orderAttachmentHistoryRepository;
    }

    /**
     * Find all OrderAttachmentHistory by order attachment ID
     * @param orderAttachmentId the order attachment ID
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByOrderAttachmentId(String orderAttachmentId) {
        return orderAttachmentHistoryRepository.findByOrderAttachmentId(orderAttachmentId);
    }
}
