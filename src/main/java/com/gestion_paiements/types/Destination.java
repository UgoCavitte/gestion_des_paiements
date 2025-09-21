package com.gestion_paiements.types;

/*
 * Can be a platform or a bank account
 */

import org.jetbrains.annotations.Nullable;

public final class Destination {

    private DestinationType destinationType;

    // Can be null if it is a bank account
    private @Nullable String name;

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
}
