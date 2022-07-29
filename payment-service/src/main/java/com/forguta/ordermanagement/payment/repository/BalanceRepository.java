package com.forguta.ordermanagement.payment.repository;


import com.forguta.ordermanagement.payment.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByUserId(Long userId);
}
