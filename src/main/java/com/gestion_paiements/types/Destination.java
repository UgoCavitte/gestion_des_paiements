package com.gestion_paiements.types;

/*
 * Can be a platform or a bank account
 */

import com.gestion_paiements.types.payments.Payment;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public final class Destination extends Sender {

    private DestinationType destinationType;

    private HashSet<Payment> transfers = new HashSet<>();

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Destination(DestinationType destinationType, String name) {
        this.destinationType = destinationType;
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

    public HashSet<Payment> getTransfers() {
        return transfers;
    }

    public void setTransfers(HashSet<Payment> transfers) {
        this.transfers = transfers;
    }
}
