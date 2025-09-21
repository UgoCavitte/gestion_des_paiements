package com.gestion_paiements.types;

import java.util.HashSet;
import java.util.Set;

public final class WorkingCountry extends Country {

    HashSet<Destination> accounts = new HashSet<>();

    public WorkingCountry (String name) {
        super (name);
    }

    public WorkingCountry (String name, Set<Destination> accounts) {
        super (name);
        this.accounts.addAll(accounts);
    }

    public HashSet<Destination> getAccounts() {
        return accounts;
    }

    public void setAccounts(HashSet<Destination> accounts) {
        this.accounts = accounts;
    }
}
