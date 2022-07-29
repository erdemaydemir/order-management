package com.forguta.ordermanagement.order.repository;

import com.forguta.ordermanagement.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
