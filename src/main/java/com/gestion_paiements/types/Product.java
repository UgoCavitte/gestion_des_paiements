package com.gestion_paiements.types;

public final class Product {

    private String shortName;

    private String description;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Product(String shortName, String description) {
        this.shortName = shortName;
        this.description = description;
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
