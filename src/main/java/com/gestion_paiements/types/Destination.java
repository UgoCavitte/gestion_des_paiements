package com.gestion_paiements.types;

/*
 * Can be a platform or a bank account
 */

import com.gestion_paiements.types.payments.Payment;

import java.util.HashSet;

public final class Destination extends Sender {

    private DestinationType destinationType;

    private WorkingCountry country;

    private Currency currency;

    private HashSet<Payment> transfers = new HashSet<>();

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Destination(DestinationType destinationType, WorkingCountry country, Currency currency, String name) {
        this.destinationType = destinationType;
        this.country = country;
        this.currency = currency;
        super.setName(name);
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public HashSet<Payment> getTransfers() {
        return transfers;
    }

    public void setTransfers(HashSet<Payment> transfers) {
        this.transfers = transfers;
    }

    public WorkingCountry getCountry() {
        return country;
    }

    public void setCountry(WorkingCountry country) {
        this.country = country;
    }
}
