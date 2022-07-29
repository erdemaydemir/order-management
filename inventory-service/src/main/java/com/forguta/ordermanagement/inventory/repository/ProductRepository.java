package com.forguta.ordermanagement.inventory.repository;

import com.forguta.ordermanagement.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
