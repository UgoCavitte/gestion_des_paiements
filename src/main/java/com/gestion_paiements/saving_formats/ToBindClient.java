package com.gestion_paiements.saving_formats;

import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.payments.Payment;

/// Unbound class for [Client]
/// Only [Payment] elements are not saved, because they will be bound to clients from saved payments

public final class ToBindClient {

    private String name;

    private int id;

    private int country;

    private boolean active;

    private String comment;

    // Default constructor
    public ToBindClient() {
    }

    // Constructor for me
    public ToBindClient (Client client) {
        this.name = client.getName();
        this.id = client.getId();
        this.country = client.getCountry().getId();
        this.active = client.isActive();
        this.comment = client.getComment();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
