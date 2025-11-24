package com.gestion_paiements.types;

import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.WithID;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class Country implements WithID, Comparable<Country> {

    private int id;

    private String name;

    // Needed for deserialization
    public Country () {

    }

    public Country (String name) {
        this.id = IDs.getAvailableID(new HashSet<>(Data.instance.getMapClientsCountries().values()));
        this.name = name;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NotNull Country country) {
        return this.name.compareTo(country.getName());
    }
}
