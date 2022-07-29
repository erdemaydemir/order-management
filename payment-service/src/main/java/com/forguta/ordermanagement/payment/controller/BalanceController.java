package com.forguta.ordermanagement.payment.controller;

import com.forguta.ordermanagement.payment.entity.Balance;
import com.forguta.ordermanagement.payment.model.BalanceRequest;
import com.forguta.ordermanagement.payment.model.BalanceTransactionRequest;
import com.forguta.ordermanagement.payment.service.BalanceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/balances")
public class BalanceController {

    private final BalanceQueryService palanceQueryService;

    @PostMapping
    public Balance createBalance(@RequestBody BalanceRequest balanceRequest) {
        return this.palanceQueryService.saveBalance(balanceRequest);
    }

    @PutMapping("/{userId}")
    public Balance updateBalance(@PathVariable Long userId, @RequestBody BalanceTransactionRequest orderRequest) {
        return this.palanceQueryService.updateBalance(userId, orderRequest);
    }

    @GetMapping
    public List<Balance> getBalances() {
        return this.palanceQueryService.getBalances();
    }
}
