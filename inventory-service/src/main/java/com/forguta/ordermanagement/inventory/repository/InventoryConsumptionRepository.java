package com.forguta.ordermanagement.inventory.repository;

import com.forguta.ordermanagement.inventory.entity.InventoryConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryConsumptionRepository extends JpaRepository<InventoryConsumption, Long> {

    void deleteInventoryConsumptionByOrderId(Long orderId);

    InventoryConsumption findInventoryConsumptionByOrderId(Long orderId);
}
