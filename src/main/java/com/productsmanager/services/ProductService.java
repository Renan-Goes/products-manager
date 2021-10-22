package com.productsmanager.services;

import com.productsmanager.models.Product;
import com.productsmanager.repository.ProductRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter @Setter
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> insertProduct(Product product) {
        System.out.println("Before save");
        System.out.println(product.getProductId() + ", " + product.getUser() + ", " + 
                product.getPrice() + ", " + product.getName());
        productRepository.save(product);
        System.out.println("After save");
        return ResponseEntity.ok().body(product);
    }
    
}
