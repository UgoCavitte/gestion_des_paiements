package com.gestion_paiements.saving_formats;

import com.gestion_paiements.types.WorkingCountry;
import com.gestion_paiements.util.WithID;

import java.util.Set;
import java.util.stream.Collectors;

/// This class is used to save and read Data in memory
/// This convert it to a WorkingCountry, a binding with accounts is necessary

public final class ToBindWorkingCountry {

    private Set<Integer> destinations;

    private String name;

    private int id;

    // Default constructor for serialization
    public ToBindWorkingCountry () {

    }

    // Constructor to convert a WorkingCountry
    public ToBindWorkingCountry (WorkingCountry workingCountry) {
        this.name = workingCountry.getName();
        this.id = workingCountry.getId();
        destinations = workingCountry.getAccountsAndPlatforms().values().stream().map(WithID::getId).collect(Collectors.toSet());
    }

    public Set<Integer> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Integer> destinations) {
        this.destinations = destinations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
