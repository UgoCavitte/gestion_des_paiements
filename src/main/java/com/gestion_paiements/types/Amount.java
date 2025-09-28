package com.gestion_paiements.types;

/*
 * This class is used to store the amount and the currency of a money transfer in one place
 */

import org.jetbrains.annotations.NotNull;

public final class Amount implements Comparable<Amount> {

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

    @Override
    public int compareTo(@NotNull Amount amount) {
        return Double.compare(this.amount, amount.amount);
    }
}
