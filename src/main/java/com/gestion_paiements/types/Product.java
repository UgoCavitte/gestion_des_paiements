package com.gestion_paiements.types;

import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.withID;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public final class Product implements Comparable, withID {

    private int id;

    private String shortName;

    private String description;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Product(String shortName, String description) {
        this.id = IDs.getAvailableID(Data.instance.getSetProducts());
        this.shortName = shortName;
        this.description = description;
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

    @Override
    public int compareTo(@NotNull Object o) {

        if (o.getClass() != Product.class) {
            throw new RuntimeException();
        }

        // Products are sorted by their short name
        return this.shortName.compareTo(((Product) o).shortName);
    }
}
