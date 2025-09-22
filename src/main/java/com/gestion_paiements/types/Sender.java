package com.gestion_paiements.types;

/// Used to make Client and Destination (Platform) compatible

public abstract class Sender {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
