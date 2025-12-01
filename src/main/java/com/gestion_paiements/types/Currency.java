package com.gestion_paiements.types;

import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.WithID;

public final class Currency implements WithID {

    private int id;

    private String name;

    public Currency(String name) {
        this.id = IDs.getAvailableID(Data.instance.getSetCurrencies());
        this.name = name;
    }

    public Currency () {

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
