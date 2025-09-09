package com.gestion_paiements.gestion_des_paiements.types;

/*
 * Can be a platform or a bank account
 */

import org.jetbrains.annotations.Nullable;

public final class Destination {

    private DestinationType destinationType;

    private String country;

    // Can be null if it is a bank account
    private @Nullable String name;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Destination(DestinationType destinationType, String country, @Nullable String name) {
        this.destinationType = destinationType;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
