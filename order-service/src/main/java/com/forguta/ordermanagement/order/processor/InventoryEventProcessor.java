package com.forguta.ordermanagement.order.processor;

import com.forguta.libs.saga.core.process.annotation.Processor;
import com.forguta.ordermanagement.common.constant.InventoryStatus;
import com.forguta.ordermanagement.common.dto.inventory.InventoryDto;
import com.forguta.ordermanagement.common.event.inventory.InventoryRejectedEvent;
import com.forguta.ordermanagement.common.event.inventory.InventoryReservedEvent;
import com.forguta.ordermanagement.order.service.OrderCommandService;
import com.forguta.ordermanagement.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryEventProcessor {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @Processor(InventoryReservedEvent.class)
    public void updateOrderInventoryStatusForReserved(InventoryReservedEvent inventoryReservedEvent) {
        updateInventoryStatusByEventType(inventoryReservedEvent.getDto(), InventoryStatus.RESERVED);
    }

    @Processor(InventoryRejectedEvent.class)
    public void updateOrderInventoryStatusForRejected(InventoryRejectedEvent inventoryRejectedEvent) {
        updateInventoryStatusByEventType(inventoryRejectedEvent.getDto(), InventoryStatus.REJECTED);
    }

    private void updateInventoryStatusByEventType(InventoryDto inventoryDto, InventoryStatus inventoryStatus) {
        Long orderId = inventoryDto.getOrderId();
        orderQueryService.updateOrderInventoryStatusById(orderId, inventoryStatus);
        orderCommandService.checkOrderByStatuses(orderId);
    }
}
