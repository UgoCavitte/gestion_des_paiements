package com.gestion_paiements.data;

import com.gestion_paiements.types.*;

import java.util.HashMap;
import java.util.HashSet;

public final class Instance {

    // Data is saved this way:
    // - Clients are saved with their payments ;
    // - Destinations are saved without PaymentFromClient elements (they must be added while loading data) ;

    private HashSet<Product> setProducts = new HashSet<>();

    private HashSet<Client> setClients = new HashSet<>();

    private HashSet<Country> setClientsCountries = new HashSet<>();

    private HashMap<String, WorkingCountry> mapAccountsCountries = new HashMap<>();

    private HashSet<Currency> setCurrencies = new HashSet<>();

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public HashSet<Product> getSetProducts() {
        return setProducts;
    }

    public void setSetProducts(HashSet<Product> Products) {
        this.setProducts = Products;
    }

    public HashSet<Client> getSetClients() {
        return setClients;
    }

    public void setSetClients(HashSet<Client> setClients) {
        this.setClients = setClients;
    }

    public HashSet<Country> getSetClientsCountries() {
        return setClientsCountries;
    }

    public void setSetClientsCountries(HashSet<Country> setClientsCountries) {
        this.setClientsCountries = setClientsCountries;
    }

    public HashMap<String, WorkingCountry> getMapAccountsCountries() {
        return mapAccountsCountries;
    }

    public void setMapAccountsCountries(HashMap<String, WorkingCountry> mapAccountsCountries) {
        this.mapAccountsCountries = mapAccountsCountries;
    }

    public HashSet<Currency> getSetCurrencies() {
        return setCurrencies;
    }

    public void setSetCurrencies(HashSet<Currency> setCurrencies) {
        this.setCurrencies = setCurrencies;
    }
}
