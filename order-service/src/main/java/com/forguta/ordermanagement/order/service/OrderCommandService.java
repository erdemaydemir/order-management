package com.forguta.ordermanagement.order.service;

import com.forguta.libs.saga.core.model.Event;
import com.forguta.libs.saga.core.publisher.EventPublisher;
import com.forguta.ordermanagement.common.constant.InventoryStatus;
import com.forguta.ordermanagement.common.constant.OrderStatus;
import com.forguta.ordermanagement.common.constant.PaymentStatus;
import com.forguta.ordermanagement.common.dto.order.OrderDto;
import com.forguta.ordermanagement.common.event.order.OrderCanceledEvent;
import com.forguta.ordermanagement.common.event.order.OrderCompletedEvent;
import com.forguta.ordermanagement.common.event.order.OrderCreatedEvent;
import com.forguta.ordermanagement.order.entity.Order;
import com.forguta.ordermanagement.order.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderCommandService {

    private final DozerBeanMapper dozerBeanMapper;
    private final OrderQueryService orderQueryService;
    private final EventPublisher eventPublisher;

    public Order createOrder(OrderRequest orderRequest) {
        Order order = dozerBeanMapper.map(orderRequest, Order.class);
        order.setStatus(OrderStatus.CREATED);
        order.setAmount(orderRequest.getQuantity() * 10d);
        Order savedOrder = orderQueryService.saveOrder(order);
        eventPublisher.sendAndForget(Event.builder().body(
                        OrderCreatedEvent.builder()
                                .dto(dozerBeanMapper.map(savedOrder, OrderDto.class))
                                .build())
                .build());
        return savedOrder;
    }

    @Transactional
    public void checkOrderByStatuses(Long orderId){
        orderQueryService.getOrderById(orderId)
                .ifPresent(order -> {
                    InventoryStatus inventoryStatus = order.getInventoryStatus();
                    PaymentStatus paymentStatus = order.getPaymentStatus();
                    if(Objects.isNull(inventoryStatus) || Objects.isNull(paymentStatus))
                        return;
                    if (PaymentStatus.RESERVED.equals(paymentStatus) && InventoryStatus.RESERVED.equals(inventoryStatus)) {
                        completeOrder(orderId);
                    } else {
                        cancelOrder(orderId);
                    }
                });
    }

    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderQueryService.getOrderById(orderId).orElseThrow();
        order.setStatus(OrderStatus.COMPLETED);
        order = orderQueryService.saveOrder(order);
        eventPublisher.sendAndForget(Event.builder().body(
                        OrderCompletedEvent.builder()
                                .dto(dozerBeanMapper.map(order, OrderDto.class))
                                .build())
                .build());
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderQueryService.getOrderById(orderId).orElseThrow();
        order.setStatus(OrderStatus.CANCELED);
        order = orderQueryService.saveOrder(order);
        eventPublisher.sendAndForget(Event.builder().body(
                        OrderCanceledEvent.builder()
                                .dto(dozerBeanMapper.map(order, OrderDto.class))
                                .build())
                .build());
    }
}
