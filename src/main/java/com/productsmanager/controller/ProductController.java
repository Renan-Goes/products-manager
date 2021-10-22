package com.productsmanager.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.productsmanager.exception.ExceptionDetails;
import com.productsmanager.forms.ProductForm;
import com.productsmanager.models.Product;
import com.productsmanager.repository.ProductRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    @Transactional
	@CacheEvict(value="productRegister", allEntries=true)
    public ResponseEntity<?> insertProduct(@RequestBody @Valid ProductForm form) {
        Product product = form.convert();

        Product foundProduct = productRepository.findByProductId(product.getProductId());

        if(foundProduct != null) {
            ExceptionDetails exception = new ExceptionDetails("Bad Request", 400, 
                    "Product with id already registered.", "productId is probably incorrect.", new Date());
    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
        }

        productRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    @Cacheable("productRegister")
    public ResponseEntity<?> listProducts() {
        List<Product> savedProducts = productRepository.findAll();
        return ResponseEntity.ok().body(savedProducts);
    }

    @GetMapping(path="/{productId}")
    public ResponseEntity<?> findProduct(@PathVariable String productId) {
        Product foundProduct = productRepository.findByProductId(productId);
        if(foundProduct == null) {
            ExceptionDetails exception = new ExceptionDetails("Not Found", 404, 
                    "Product could not be found.", "Product is not in the database.", new Date());
                    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }
        
        return ResponseEntity.ok().body(foundProduct);
    }

    @DeleteMapping("/removeProduct/{productId}")
    @Transactional
    public ResponseEntity<?> removeProduct(@PathVariable String productId) {

        Product foundProduct = productRepository.findByProductId(productId);

        if(foundProduct == null) {
            ExceptionDetails exception = new ExceptionDetails("Not Found", 404, 
                    "The product you're trying to delete could not be found.", 
                    "Chech if the ID is correct or if the product exists.", new Date());
                    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }

        productRepository.deleteById(productId);
        
        ExceptionDetails exception = new ExceptionDetails("OK", 200, 
                "The product was deleted from the database.",
                "The product" + foundProduct.getName() + ", from user" + foundProduct.getUser() + 
                " was successfully deleted.", new Date());
                
        return ResponseEntity.ok().body(exception);
    }

    @PutMapping("/updateProduct/{productId}")
    @Transactional
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductForm form, 
            @PathVariable String productId) {

        Product productWithUpdateValues = form.convert();
        
        Product foundProduct = productRepository.findByProductId(productId);

        if(foundProduct == null) {
            ExceptionDetails exception = new ExceptionDetails("Not Found", 404, 
                    "The product you're trying to update could not be found.", 
                    "Chech if the ID is correct or if the product exists.", new Date());
                    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }
        
        foundProduct.setName(productWithUpdateValues.getName());
        foundProduct.setPrice(productWithUpdateValues.getPrice());
        foundProduct.setUser(productWithUpdateValues.getUser());

        productRepository.save(foundProduct);

        return ResponseEntity.ok().body(foundProduct);        
    }
}
