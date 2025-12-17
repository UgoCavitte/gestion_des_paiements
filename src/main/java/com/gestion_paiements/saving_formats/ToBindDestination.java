package com.gestion_paiements.saving_formats;

import com.gestion_paiements.data.Memory;
import com.gestion_paiements.types.Destination;

/// Unbound class for [Destination]

public final class ToBindDestination {

    private String name;

    private int id;

    private int type;

    private int workingCountry;

    private int currency;

    // Default constructor
    public ToBindDestination () {

    }

    // Constructor for me
    public ToBindDestination (Destination destination) {
        this.name = destination.getName();
        this.id = destination.getId();
        this.type = Memory.mapFromDestinationTypeToInteger.get(destination.getDestinationType());
        this.workingCountry = destination.getCountry().getId();
        this.currency = destination.getCurrency().getId();
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWorkingCountry() {
        return workingCountry;
    }

    public void setWorkingCountry(int workingCountry) {
        this.workingCountry = workingCountry;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
}
