package com.gestion_paiements.gestion_des_paiements.types;

public final class PurchasedProduct {

    private int quantity;

    private Product product;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public PurchasedProduct(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProductName(Product product) {
        this.product = product;
    }
}
