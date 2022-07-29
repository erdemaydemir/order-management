package com.forguta.ordermanagement.common.event.order;

import com.forguta.libs.saga.core.model.EventPayload;
import com.forguta.ordermanagement.common.dto.order.OrderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderCompletedEvent extends EventPayload<OrderDto> {
}
