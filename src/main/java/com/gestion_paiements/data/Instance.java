package com.gestion_paiements.data;

import com.gestion_paiements.types.*;
import com.gestion_paiements.types.payments.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.HashMap;
import java.util.HashSet;

public final class Instance {

    // Data is saved this way:
    // - Clients are saved with their payments ;
    // - Destinations are saved without PaymentFromClient elements (they must be added while loading data) ;

    private HashSet<Product> setProducts = new HashSet<>();

    private ObservableSet<Client> setClients = FXCollections.observableSet(new HashSet<>());

    private HashSet<Destination> setDestinations = new HashSet<>();

    private HashMap<String, Country> mapClientsCountries = new HashMap<>();

    private HashMap<String, WorkingCountry> mapAccountsCountries = new HashMap<>();

    private HashSet<Currency> setCurrencies = new HashSet<>();

    private ObservableSet<Payment> setPayments = FXCollections.observableSet(new HashSet<>());

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public HashSet<Product> getSetProducts() {
        return setProducts;
    }

    public void setSetProducts(HashSet<Product> Products) {
        this.setProducts = Products;
    }

    public ObservableSet<Client> getSetClients() {
        return setClients;
    }

    public void setSetClients(HashSet<Client> setClients) {
        this.setClients = FXCollections.observableSet(setClients);
    }

    public HashSet<Destination> getSetDestinations() {
        return setDestinations;
    }

    public void setSetDestinations(HashSet<Destination> setDestinations) {
        this.setDestinations = setDestinations;
    }

    public HashMap<String, Country> getMapClientsCountries() {
        return mapClientsCountries;
    }

    public void setMapClientsCountries(HashMap<String, Country> setClientsCountries) {
        this.mapClientsCountries = setClientsCountries;
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

    public ObservableSet<Payment> getSetPayments() {
        return setPayments;
    }

    public void setSetPayments(HashSet<Payment> setPayments) {
        this.setPayments = FXCollections.observableSet(setPayments);
    }
}
