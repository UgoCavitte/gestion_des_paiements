package com.gestion_paiements.types;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public final class WorkingCountry extends Country {

    HashMap<String, Destination> accounts = new HashMap<>();

    public WorkingCountry (String name) {
        super (name);
    }

    public WorkingCountry (String name, @NotNull Set<Destination> accounts) {
        super (name);
        accounts.forEach(a -> this.accounts.put(a.getName(), a));
    }

    public HashMap<String, Destination> getAccounts() {
        return accounts;
    }

    public void setAccounts(HashMap<String, Destination> accounts) {
        this.accounts = accounts;
    }
}
