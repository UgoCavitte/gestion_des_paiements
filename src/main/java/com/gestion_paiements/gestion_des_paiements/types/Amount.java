package com.gestion_paiements.gestion_des_paiements.types;

/*
 * This class is used to store the amount and the currency of a money transfer in one place
 */

public final class Amount {

    private double amount;

    private String currency;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Amount(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
