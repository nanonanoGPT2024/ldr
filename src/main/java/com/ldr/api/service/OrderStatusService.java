package com.ldr.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldr.api.model.OrderStatus;
import com.ldr.api.repository.OrderStatusRepository;

@Service
@Transactional
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    /**
     * Find all active OrderStatus
     * @return List<OrderStatus>
     */
    @Transactional(readOnly = true)
    public List<OrderStatus> findByIsActive() {
        return orderStatusRepository.findByIsActive(true);
    }
}
