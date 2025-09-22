package com.gestion_paiements.types;

/*
 * Can be a platform or a bank account
 */

import com.gestion_paiements.types.payments.Payment;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public final class Destination {

    private DestinationType destinationType;

    // Can be null if it is a bank account
    private @Nullable String name;

    private HashSet<Payment> transfers = new HashSet<>();

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Destination(DestinationType destinationType, @Nullable String name) {
        this.destinationType = destinationType;
        this.name = name;
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

    public @Nullable String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public HashSet<Payment> getTransfers() {
        return transfers;
    }

    public void setTransfers(HashSet<Payment> transfers) {
        this.transfers = transfers;
    }
}
