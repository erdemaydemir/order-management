package com.forguta.ordermanagement.order.processor;

import com.forguta.libs.saga.core.process.annotation.Processor;
import com.forguta.ordermanagement.common.constant.PaymentStatus;
import com.forguta.ordermanagement.common.dto.payment.PaymentDto;
import com.forguta.ordermanagement.common.event.inventory.InventoryRejectedEvent;
import com.forguta.ordermanagement.common.event.payment.PaymentRejectedEvent;
import com.forguta.ordermanagement.common.event.payment.PaymentReservedEvent;
import com.forguta.ordermanagement.order.service.OrderCommandService;
import com.forguta.ordermanagement.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentEventProcessor {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @Transactional
    @Processor(PaymentReservedEvent.class)
    public void updateOrderPaymentStatusForReserved(PaymentReservedEvent paymentReservedEvent) {
        updatePaymentStatusByEventType(paymentReservedEvent.getDto(), PaymentStatus.RESERVED);

    }

    @Transactional
    @Processor(PaymentRejectedEvent.class)
    public void updateOrderPaymentStatusForRejected(PaymentRejectedEvent paymentRejectedEvent) {
        updatePaymentStatusByEventType(paymentRejectedEvent.getDto(), PaymentStatus.REJECTED);
    }

    private void updatePaymentStatusByEventType(PaymentDto paymentDto, PaymentStatus paymentStatus) {
        Long orderId = paymentDto.getOrderId();
        orderQueryService.updateOrderPaymentStatusById(orderId, paymentStatus);
        orderCommandService.checkOrderByStatuses(orderId);
    }
}