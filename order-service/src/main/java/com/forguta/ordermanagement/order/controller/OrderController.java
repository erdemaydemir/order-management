package com.forguta.ordermanagement.order.controller;

import com.forguta.ordermanagement.order.model.OrderRequest;
import com.forguta.ordermanagement.order.entity.Order;
import com.forguta.ordermanagement.order.service.OrderCommandService;
import com.forguta.ordermanagement.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return this.orderCommandService.createOrder(orderRequest);
    }

    @GetMapping
    public List<Order> getOrders() {
        return this.orderQueryService.getOrders();
    }
}
