package com.forguta.ordermanagement.inventory.service;

import com.forguta.libs.saga.core.model.Event;
import com.forguta.libs.saga.core.publisher.EventPublisher;
import com.forguta.ordermanagement.common.dto.inventory.InventoryDto;
import com.forguta.ordermanagement.common.dto.order.OrderDto;
import com.forguta.ordermanagement.common.event.inventory.InventoryRejectedEvent;
import com.forguta.ordermanagement.common.event.inventory.InventoryReservedEvent;
import com.forguta.ordermanagement.common.event.order.OrderCanceledEvent;
import com.forguta.ordermanagement.common.event.order.OrderCreatedEvent;
import com.forguta.ordermanagement.inventory.entity.InventoryConsumption;
import com.forguta.ordermanagement.inventory.model.InventoryConsumptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryConsumptionCommandService {

    private final ProductQueryService productQueryService;
    private final InventoryConsumptionQueryService inventoryConsumptionQueryService;
    private final EventPublisher eventPublisher;

    public void allocateInventoryConsumption(OrderCreatedEvent orderCreatedEvent) {
        OrderDto orderDto = orderCreatedEvent.getDto();
        InventoryDto inventoryDto = InventoryDto.builder()
                .orderId(orderDto.getId())
                .productId(orderDto.getProductId())
                .build();
        productQueryService.getProductById(orderDto.getProductId())
                .filter(product -> product.getAllocatableQuantity() > 0 &&
                        product.getAllocatableQuantity() > orderDto.getQuantity())
                .ifPresentOrElse(product -> {
                            product.setAllocatableQuantity(product.getAllocatableQuantity() - orderDto.getQuantity());
                            productQueryService.updateProduct(product);
                            inventoryConsumptionQueryService.saveInventoryConsumption(InventoryConsumptionRequest.builder()
                                    .orderId(orderDto.getId())
                                    .productId(orderDto.getProductId())
                                    .quantityConsumed(orderDto.getQuantity())
                                    .build());
                            eventPublisher.sendAndForget(Event.builder().body(
                                            InventoryReservedEvent.builder()
                                                    .dto(inventoryDto).build())
                                    .build());
                        }, () -> eventPublisher.sendAndForget(Event.builder()
                                .body(InventoryRejectedEvent.builder()
                                        .dto(inventoryDto)
                                        .build())
                                .build())
                );
    }

    public void deallocateInventoryConsumption(OrderCanceledEvent orderCanceledEvent) {
        OrderDto orderDto = orderCanceledEvent.getDto();
        Long orderId = orderDto.getId();
        InventoryConsumption inventoryConsumption = inventoryConsumptionQueryService.getInventoryConsumptionByOrderId(orderId);
        if(inventoryConsumption != null) {
            productQueryService.getProductById(inventoryConsumption.getProductId())
                    .ifPresent(product -> {
                        product.setAllocatableQuantity(product.getAllocatableQuantity() + inventoryConsumption.getQuantityConsumed());
                        productQueryService.updateProduct(product);
                        inventoryConsumptionQueryService.deleteInventoryConsumptionByOrderId(orderId);
                    });
        }
    }
}
