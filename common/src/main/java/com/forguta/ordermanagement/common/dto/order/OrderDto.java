package com.forguta.ordermanagement.common.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDto implements Serializable {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Double amount;
}
