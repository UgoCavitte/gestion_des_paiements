package com.gestion_paiements.types;

import com.gestion_paiements.saving_formats.ToBindWorkingCountry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public final class WorkingCountry {

    String name;

    HashMap<String, Destination> accountsAndPlatforms = new HashMap<>();

    public WorkingCountry (String name) {
        this.name = name;
    }

    public WorkingCountry (String name, @NotNull Set<Destination> accountsAndPlatforms) {
        this.name = name;
        accountsAndPlatforms.forEach(a -> this.accountsAndPlatforms.put(a.getName(), a));
    }

    public WorkingCountry (ToBindWorkingCountry toBind) {
        this.setName(toBind.getName());
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
}
