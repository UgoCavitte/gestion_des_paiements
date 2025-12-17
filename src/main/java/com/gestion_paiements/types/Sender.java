package com.gestion_paiements.types;

import com.gestion_paiements.util.WithID;

/// Used to make Client and Destination (Platform) compatible

public abstract class Sender implements WithID {

    public String getName() {
        return "Name not set";
    }

    public void setName(String name) {

    }
}
