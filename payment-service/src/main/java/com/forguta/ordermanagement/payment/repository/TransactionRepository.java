package com.forguta.ordermanagement.payment.repository;

import com.forguta.ordermanagement.payment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    void deleteTransactionByOrderId(Long orderId);

    Transaction findTransactionByOrderId(Long orderId);
}
