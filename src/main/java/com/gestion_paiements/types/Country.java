package com.gestion_paiements.types;

import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.withID;

public class Country implements withID {

    private int id;

    private String name;

    public Country (String name) {
        this.id = IDs.getAvailableID(Data.instance.getSetClientsCountries());
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
}
