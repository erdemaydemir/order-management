package com.forguta.ordermanagement.inventory.controller;

import com.forguta.ordermanagement.inventory.model.ProductRequest;
import com.forguta.ordermanagement.inventory.entity.Product;
import com.forguta.ordermanagement.inventory.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductQueryService productQueryService;

    @PostMapping
    public Product createProduct(@RequestBody ProductRequest orderRequest) {
        return this.productQueryService.saveProduct(orderRequest);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductRequest orderRequest) {
        return this.productQueryService.updateProduct(id, orderRequest);
    }

    @GetMapping
    public List<Product> getProducts() {
        return this.productQueryService.getProducts();
    }
}
