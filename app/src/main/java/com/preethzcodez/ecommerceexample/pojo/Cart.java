package com.preethzcodez.ecommerceexample.pojo;

/**
 * Created by Preeth on 1/3/2018
 */

public class Cart {

    private Integer id;
    private Integer itemQuantity;
    private Product product;
    private Variant variant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
