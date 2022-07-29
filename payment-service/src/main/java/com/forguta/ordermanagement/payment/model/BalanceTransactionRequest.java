package com.forguta.ordermanagement.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceTransactionRequest {
    private Double value;
    private BalanceProcessType balanceProcessType;
}
