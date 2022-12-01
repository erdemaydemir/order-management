package com.forguta.ordermanagement.order.service;

import com.forguta.ordermanagement.common.constant.InventoryStatus;
import com.forguta.ordermanagement.common.constant.PaymentStatus;
import com.forguta.ordermanagement.order.entity.Order;
import com.forguta.ordermanagement.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderInventoryStatusById(Long id, InventoryStatus inventoryStatus) {
        return this.getOrderById(id).map(order -> {
            order.setInventoryStatus(inventoryStatus);
            return this.saveOrder(order);
        }).orElseThrow();
    }

    public Order updateOrderPaymentStatusById(Long id, PaymentStatus paymentStatus) {
        return this.getOrderById(id).map(order -> {
            order.setPaymentStatus(paymentStatus);
            return this.saveOrder(order);
        }).orElseThrow();
    }
}
