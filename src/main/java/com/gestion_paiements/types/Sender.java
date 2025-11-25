package com.gestion_paiements.types;

import com.gestion_paiements.util.WithID;

/// Used to make Client and Destination (Platform) compatible

public abstract class Sender implements WithID {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
