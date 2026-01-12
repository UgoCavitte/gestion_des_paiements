package com.gestion_paiements.types;

import com.gestion_paiements.saving_formats.ToBindWorkingCountry;
import com.gestion_paiements.util.IDs;
import com.gestion_paiements.util.WithID;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class WorkingCountry implements WithID {

    int id;

    String name;

    HashMap<String, Destination> accountsAndPlatforms = new HashMap<>();

    public WorkingCountry (String name) {
        this.name = name;
        this.id = IDs.getAvailableID(new HashSet<>(Data.instance.getMapAccountsCountries().values()));
    }

    public WorkingCountry (String name, @NotNull Set<Destination> accountsAndPlatforms) {
        this.name = name;
        accountsAndPlatforms.forEach(a -> this.accountsAndPlatforms.put(a.getName(), a));
        this.id = IDs.getAvailableID(new HashSet<>(Data.instance.getMapAccountsCountries().values()));
    }

    public WorkingCountry (ToBindWorkingCountry toBind) {
        this.setName(toBind.getName());
        this.setId(toBind.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Destination> getAccountsAndPlatforms() {
        return accountsAndPlatforms;
    }

    public void setAccountsAndPlatforms(HashMap<String, Destination> accountsAndPlatforms) {
        this.accountsAndPlatforms = accountsAndPlatforms;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
