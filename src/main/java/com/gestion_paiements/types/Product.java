package com.gestion_paiements.types;

import com.gestion_paiements.util.IDs;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Product implements Comparable {

    private int id;

    private String shortName;

    private String description;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public Product(String shortName, String description) {

        System.out.println("Creating a product");
        System.out.println(Data.instance.getSetProducts());


        this.id = IDs.getAvailableID(new HashSet<>(Data.instance.getSetProducts().stream().map(Product::getId).collect(Collectors.toSet())));
        this.shortName = shortName;
        this.description = description;
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
