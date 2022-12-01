package com.forguta.ordermanagement.payment.service;

import com.forguta.libs.saga.core.model.Event;
import com.forguta.libs.saga.core.publisher.EventPublisher;
import com.forguta.ordermanagement.common.dto.order.OrderDto;
import com.forguta.ordermanagement.common.dto.payment.PaymentDto;
import com.forguta.ordermanagement.common.event.order.OrderCanceledEvent;
import com.forguta.ordermanagement.common.event.order.OrderCreatedEvent;
import com.forguta.ordermanagement.common.event.payment.PaymentRejectedEvent;
import com.forguta.ordermanagement.common.event.payment.PaymentReservedEvent;
import com.forguta.ordermanagement.payment.entity.Transaction;
import com.forguta.ordermanagement.payment.model.BalanceProcessType;
import com.forguta.ordermanagement.payment.model.BalanceTransactionRequest;
import com.forguta.ordermanagement.payment.model.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionCommandService {

    private final BalanceQueryService balanceQueryService;
    private final TransactionQueryService transactionQueryService;
    private final EventPublisher eventPublisher;

    public void doTransaction(OrderCreatedEvent orderCreatedEvent) {
        OrderDto orderDto = orderCreatedEvent.getDto();
        PaymentDto paymentDto = PaymentDto.builder()
                .orderId(orderDto.getId())
                .userId(orderDto.getUserId())
                .amount(orderDto.getAmount())
                .build();
        balanceQueryService.getBalanceByUserId(orderDto.getUserId())
                .filter(balance -> balance.getValue() > 0 &&
                        balance.getValue() > orderDto.getAmount())
                .ifPresentOrElse(balance -> {
                            balanceQueryService.updateBalance(orderDto.getUserId(), BalanceTransactionRequest.builder()
                                    .balanceProcessType(BalanceProcessType.MINUS)
                                    .value(orderDto.getAmount()).build());
                            transactionQueryService.saveTransaction(TransactionRequest.builder()
                                    .orderId(orderDto.getId())
                                    .userId(orderDto.getUserId())
                                    .amount(orderDto.getAmount())
                                    .build());
                            eventPublisher.sendAndForget(Event.builder().body(
                                            PaymentReservedEvent.builder()
                                                    .dto(paymentDto).build())
                                    .build());
                        }, () -> eventPublisher.sendAndForget(Event.builder()
                                .body(PaymentRejectedEvent.builder()
                                        .dto(paymentDto)
                                        .build())
                                .build())
                );
    }

    public void abortTransaction(OrderCanceledEvent orderCanceledEvent) {
        OrderDto orderDto = orderCanceledEvent.getDto();
        Long orderId = orderDto.getId();
        Transaction transaction = transactionQueryService.getTransactionByOrderId(orderId);
        if (transaction != null) {
            balanceQueryService.getBalanceById(transaction.getUserId())
                    .ifPresent(balance -> {
                        balanceQueryService.updateBalance(orderDto.getUserId(), BalanceTransactionRequest.builder()
                                .balanceProcessType(BalanceProcessType.PLUS)
                                .value(orderDto.getAmount()).build());
                        transactionQueryService.deleteTransactionByOrderId(orderId);
                    });
        }
    }
}
