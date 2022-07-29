package com.forguta.ordermanagement.payment.service;

import com.forguta.ordermanagement.payment.entity.Balance;
import com.forguta.ordermanagement.payment.model.BalanceProcessType;
import com.forguta.ordermanagement.payment.model.BalanceRequest;
import com.forguta.ordermanagement.payment.model.BalanceTransactionRequest;
import com.forguta.ordermanagement.payment.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BalanceQueryService {

    private final DozerBeanMapper dozerBeanMapper;
    private final BalanceRepository balanceRepository;

    public Optional<Balance> getBalanceById(Long id) {
        return balanceRepository.findById(id);
    }

    public Optional<Balance> getBalanceByUserId(Long userId) {
        return balanceRepository.findByUserId(userId);
    }

    public List<Balance> getBalances() {
        return balanceRepository.findAll();
    }

    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    public Balance saveBalance(BalanceRequest balanceRequest) {
        return this.saveBalance(dozerBeanMapper.map(balanceRequest, Balance.class));
    }

    public Balance updateBalance(Long userId, BalanceTransactionRequest balanceTransactionRequest) {
        return this.getBalanceByUserId(userId).map(balance -> {
            if (BalanceProcessType.PLUS.equals(balanceTransactionRequest.getBalanceProcessType())) {
                balance.setValue(balance.getValue() + balanceTransactionRequest.getValue());
            } else {
                if (balance.getValue() >= balanceTransactionRequest.getValue()) {
                    balance.setValue(balance.getValue() - balanceTransactionRequest.getValue());
                } else {
                    throw new RuntimeException("Balance not enough for process.");
                }
            }
            return this.saveBalance(balance);
        }).orElse(null);
    }
}
