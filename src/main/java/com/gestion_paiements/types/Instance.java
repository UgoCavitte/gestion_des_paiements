package com.gestion_paiements.types;

import com.gestion_paiements.types.payments.Payment;

import java.util.HashSet;
import java.util.Set;

public final class Instance {

    private HashSet<Destination> destinations = new HashSet<>();

    private HashSet<Product> setProducts = new HashSet<>();

    private HashSet<Client> setClients = new HashSet<>();

    private HashSet<Payment> setPayments = new HashSet<>();

    private HashSet<String> setCountries = new HashSet<>();

    private HashSet<String> setCurrencies = new HashSet<>();

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(HashSet<Destination> destinations) {
        this.destinations = destinations;
    }

    public HashSet<Product> getSetProducts() {
        return setProducts;
    }

    public void setSetProducts(HashSet<Product> Products) {
        this.setProducts = Products;
    }

    public HashSet<Client> getSetClients() {
        return setClients;
    }

    public void setSetClients(HashSet<Client> setClients) {
        this.setClients = setClients;
    }

    public HashSet<Payment> getSetPayments() {
        return setPayments;
    }

    public void setSetPayments(HashSet<Payment> setPayments) {
        this.setPayments = setPayments;
    }

    public HashSet<String> getSetCountries() {
        return setCountries;
    }

    public void setSetCountries(HashSet<String> setCountries) {
        this.setCountries = setCountries;
    }

    public HashSet<String> getSetCurrencies() {
        return setCurrencies;
    }

    public void setSetCurrencies(HashSet<String> setCurrencies) {
        this.setCurrencies = setCurrencies;
    }
}
