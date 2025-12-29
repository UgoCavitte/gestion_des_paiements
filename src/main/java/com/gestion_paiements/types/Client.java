package com.gestion_paiements.types;

import com.gestion_paiements.saving_formats.ToBindClient;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.WithID;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

/*
 * This is the Client class.
 * A client has :
 * - an ID ;
 * - a name ;
 * - a set of payments.
 */

public final class Client extends Sender implements WithID {

    private int ID;

    private Country country;

    private boolean active;

    // This must be set separately when the app is being opened
    private HashSet<PaymentFromClient> payments = new HashSet<>();

    // Any other information about the client
    private @Nullable String comment;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Client(String name, Country country, @Nullable String comment) {
        this.ID = IDs.getAvailableID(Data.instance.getSetClients());
        super.setName(name);
        this.country = country;
        this.active = true;
        this.comment = comment;
    }

    public Client (ToBindClient toBind) {
        this.ID = toBind.getId();
        this.active = toBind.isActive();
        this.comment = toBind.getComment();
        this.setName(toBind.getName());
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public HashSet<PaymentFromClient> getPayments() {
        return payments;
    }

    public void setPayments(HashSet<PaymentFromClient> payments) {
        this.payments = payments;
    }

    public @Nullable String getComment() {
        return comment;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }

    @Override
    public int getId() {
        return this.ID;
    }

    @Override
    public void setId(int id) {
        this.ID = id;
    }
}
