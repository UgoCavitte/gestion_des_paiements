package com.gestion_paiements.types;

import com.gestion_paiements.util.IDs;

import java.util.stream.Collectors;

public final class Country {

    private int id;

    private String name;

    public Country (String name) {
        this.id = IDs.getAvailableID(Data.instance.getSetCountries().stream().map(Country::getId).collect(Collectors.toSet()));
        this.name = name;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
