package com.forguta.ordermanagement.payment.service;

import com.forguta.ordermanagement.payment.entity.Transaction;
import com.forguta.ordermanagement.payment.model.TransactionRequest;
import com.forguta.ordermanagement.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class TransactionQueryService {

    private final DozerBeanMapper dozerBeanMapper;
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction saveTransaction(TransactionRequest transactionRequest) {
        return this.saveTransaction(dozerBeanMapper.map(transactionRequest, Transaction.class));
    }

    public void deleteTransactionByOrderId(Long orderId) {
        transactionRepository.deleteTransactionByOrderId(orderId);
    }

    public Transaction getTransactionByOrderId(Long orderId) {
        return transactionRepository.findTransactionByOrderId(orderId);
    }
}
