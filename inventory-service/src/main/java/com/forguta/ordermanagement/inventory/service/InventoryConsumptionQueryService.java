package com.forguta.ordermanagement.inventory.service;

import com.forguta.ordermanagement.inventory.entity.InventoryConsumption;
import com.forguta.ordermanagement.inventory.model.InventoryConsumptionRequest;
import com.forguta.ordermanagement.inventory.repository.InventoryConsumptionRepository;
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
public class InventoryConsumptionQueryService {

    private final DozerBeanMapper dozerBeanMapper;
    private final InventoryConsumptionRepository inventoryConsumptionRepository;

    public List<InventoryConsumption> getInventoryConsumptions() {
        return inventoryConsumptionRepository.findAll();
    }

    public InventoryConsumption saveInventoryConsumption(InventoryConsumption inventoryConsumption) {
        return inventoryConsumptionRepository.save(inventoryConsumption);
    }

    public InventoryConsumption saveInventoryConsumption(InventoryConsumptionRequest inventoryConsumptionRequest) {
        return this.saveInventoryConsumption(dozerBeanMapper.map(inventoryConsumptionRequest, InventoryConsumption.class));
    }

    public void deleteInventoryConsumptionByOrderId(Long orderId) {
        inventoryConsumptionRepository.deleteInventoryConsumptionByOrderId(orderId);
    }

    public InventoryConsumption getInventoryConsumptionByOrderId(Long orderId) {
        return inventoryConsumptionRepository.findInventoryConsumptionByOrderId(orderId);
    }
}
