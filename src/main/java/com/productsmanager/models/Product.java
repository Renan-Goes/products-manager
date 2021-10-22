package com.productsmanager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="PRODUCT_REGIST")
@Getter @Setter @NoArgsConstructor
public class Product {
    
    @Id
    @Column
    private String productId;
    @Column
    private String user;
    @Column
    private String price;
    @Column
    private String name; 

    public Product(String productId, String user, String price, String name) {
        this.productId = productId;
        this.user = user;
        this.price = price;
        this.name = name;
    }
}
