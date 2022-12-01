package com.forguta.ordermanagement.inventory.service;

import com.forguta.ordermanagement.inventory.model.ProductRequest;
import com.forguta.ordermanagement.inventory.entity.Product;
import com.forguta.ordermanagement.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ProductQueryService {

    private final DozerBeanMapper dozerBeanMapper;
    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product saveProduct(ProductRequest productRequest) {
        return this.saveProduct(dozerBeanMapper.map(productRequest, Product.class));
    }

    public Product updateProduct(Long productId, ProductRequest productRequest) {
        return this.getProductById(productId).map(product -> {
            dozerBeanMapper.map(productRequest, product);
            return this.saveProduct(product);
        }).orElseThrow();
    }

    public Product updateProduct(Product newProduct) {
        return this.getProductById(newProduct.getId()).map(oldProduct -> {
            dozerBeanMapper.map(oldProduct, newProduct);
            return this.saveProduct(newProduct);
        }).orElseThrow();
    }
}
