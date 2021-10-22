package com.productsmanager.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.productsmanager.models.Product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm {

    @NotEmpty @NotBlank
    private String productId;

    @NotEmpty @NotBlank
    private String user;

    @NotEmpty @NotBlank
    private String price;
    
    @NotEmpty @NotBlank
    private String name;

    public ProductForm(String productId, String user, String price, String name) {
        this.productId = productId;
        this.user = user;
        this.price = price;
        this.name = name;
    }
    
    public Product convert() {
        return new Product(this.productId, this.user, this.price, this.name);
    }
}
