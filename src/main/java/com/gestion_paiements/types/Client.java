package com.gestion_paiements.types;

import com.gestion_paiements.types.payments.PaymentFromClient;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/*
 * This is the Client class.
 * A client has :
 * - an ID ;
 * - a name ;
 * - a set of payments.
 */

public final class Client extends Sender {

    // Used to store data
    private int ID;

    private Country country;

    // This must be set separately when the app is being opened
    private Set<PaymentFromClient> payments;

    // Any other information about the client
    private @Nullable String comment;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Client(int ID, String name, Country country, @Nullable String comment) {
        this.ID = ID;
        super.setName(name);
        this.country = country;
        this.comment = comment;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<PaymentFromClient> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentFromClient> payments) {
        this.payments = payments;
    }

    public @Nullable String getComment() {
        return comment;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }
}
