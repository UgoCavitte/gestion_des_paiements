package com.gestion_paiements.types;

/*
 * Can be a platform or a bank account
 */

import com.gestion_paiements.saving_formats.ToBindDestination;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.WithID;

import java.util.HashSet;
import java.util.Map;

public final class Destination extends Sender implements WithID {

    public static Map<DestinationType, String> destinationTypeLabels = Map.of(DestinationType.bankAccount, "Compte en banque",
            DestinationType.platform, "Plateforme");

    private int ID;

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
        this.ID = IDs.getAvailableID(Data.instance.getSetDestinations());
    }

    public Destination (ToBindDestination toBind) {
        this.ID = toBind.getId();
        this.setName(toBind.getName());
    }

    public Destination () {

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

    @Override
    public int getId() {
        return this.ID;
    }

    @Override
    public void setId(int id) {
        this.ID = id;
    }
}
