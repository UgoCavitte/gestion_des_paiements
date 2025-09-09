package com.gestion_paiements.types;

import com.gestion_paiements.types.payments.Payment;

import java.util.Set;

public final class Instance {

    private Set<Destination> destinations;

    private Set<Product> products;

    private Set<Client> setClients;

    private Set<Payment> setPayments;

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> Products) {
        this.products = Products;
    }

    public Set<Client> getSetClients() {
        return setClients;
    }

    public void setSetClients(Set<Client> setClients) {
        this.setClients = setClients;
    }

    public Set<Payment> getSetPayments() {
        return setPayments;
    }

    public void setSetPayments(Set<Payment> setPayments) {
        this.setPayments = setPayments;
    }
}
