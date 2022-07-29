package com.forguta.ordermanagement.common.event.inventory;

import com.forguta.libs.saga.core.model.EventPayload;
import com.forguta.ordermanagement.common.dto.inventory.InventoryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InventoryRejectedEvent extends EventPayload<InventoryDto> {
}
