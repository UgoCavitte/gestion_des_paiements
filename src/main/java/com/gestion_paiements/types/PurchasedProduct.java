package com.gestion_paiements.types;

public final class PurchasedProduct {

    private double quantity;

    private Product product;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public PurchasedProduct(double quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProductName(Product product) {
        this.product = product;
    }
}
