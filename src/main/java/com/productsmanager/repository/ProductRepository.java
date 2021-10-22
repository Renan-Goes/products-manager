package com.productsmanager.repository;

import com.productsmanager.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    Product findByProductId(String productId);
}
