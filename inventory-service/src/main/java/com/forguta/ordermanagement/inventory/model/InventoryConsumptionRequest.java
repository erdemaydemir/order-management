package com.forguta.ordermanagement.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryConsumptionRequest {
    private Long orderId;
    private Long productId;
    private Integer quantityConsumed;
}
