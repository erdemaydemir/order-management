package com.forguta.ordermanagement.inventory.processor;

import com.forguta.libs.saga.core.process.annotation.Processor;
import com.forguta.ordermanagement.common.event.order.OrderCanceledEvent;
import com.forguta.ordermanagement.common.event.order.OrderCreatedEvent;
import com.forguta.ordermanagement.inventory.service.InventoryConsumptionCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderEventProcessor {

    private final InventoryConsumptionCommandService inventoryConsumptionCommandService;

    @Transactional
    @Processor(OrderCreatedEvent.class)
    public void allocateInventoryConsumption(OrderCreatedEvent orderCreatedEvent) {
        inventoryConsumptionCommandService.allocateInventoryConsumption(orderCreatedEvent);
    }

    @Transactional
    @Processor(OrderCanceledEvent.class)
    public void deallocateInventoryConsumption(OrderCanceledEvent orderCanceledEvent) {
        inventoryConsumptionCommandService.deallocateInventoryConsumption(orderCanceledEvent);
    }
}
