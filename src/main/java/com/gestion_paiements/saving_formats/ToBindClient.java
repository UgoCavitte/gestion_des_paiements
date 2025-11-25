package com.gestion_paiements.saving_formats;

import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.payments.Payment;

import java.util.Set;
import java.util.stream.Collectors;

public final class ToBindClient {

    private int id;

    private int country;

    private Set<Integer> payments;

    private String comment;

    // Default constructor

    public ToBindClient() {
    }

    // Constructor for me
    public ToBindClient (Client client) {
        this.id = client.getId();
        this.country = client.getCountry().getId();
        this.payments = client.getPayments().stream().map(Payment::getId).collect(Collectors.toSet());
        this.comment = client.getComment();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
