package com.forguta.ordermanagement.common.event.payment;

import com.forguta.libs.saga.core.model.EventPayload;
import com.forguta.ordermanagement.common.dto.payment.PaymentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentRejectedEvent extends EventPayload<PaymentDto> {
}
