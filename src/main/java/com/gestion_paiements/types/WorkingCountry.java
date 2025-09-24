package com.gestion_paiements.types;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public final class WorkingCountry extends Country {

    HashMap<String, Destination> accountsAndPlatforms = new HashMap<>();

    public WorkingCountry (String name) {
        super (name);
    }

    public WorkingCountry (String name, @NotNull Set<Destination> accountsAndPlatforms) {
        super (name);
        accountsAndPlatforms.forEach(a -> this.accountsAndPlatforms.put(a.getName(), a));
    }

    public HashMap<String, Destination> getAccountsAndPlatforms() {
        return accountsAndPlatforms;
    }

    public void setAccountsAndPlatforms(HashMap<String, Destination> accountsAndPlatforms) {
        this.accountsAndPlatforms = accountsAndPlatforms;
    }
}
