package com.gestion_paiements.data;

import com.gestion_paiements.types.*;
import com.gestion_paiements.types.payments.Payment;

import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;

/// This class is used to save data into JSON files
/// You can either save everything, which is not optimal for resources and will create lag when the save will become big
/// enough, either save some data separately

public abstract class Memory {

    ////////////////////////////////////////////////////
    /// COMPLEX SAVINGS
    ////////////////////////////////////////////////////

    /// Saves or resaves all [Payment] for a specific [WorkingCountry]
    public static void writePayments (WorkingCountry country) {
        //
    }

    /// Takes no parameter, save every [Payment] for every country
    public static void writePaymentsForEveryCountry () {
        Data.instance.getMapAccountsCountries().values().forEach(Memory::writePayments);
    }

    /// Saves or resaves all data for a specific [Client]
    public static void writeClient (Client client) {
        //
    }

    /// Saves all [Client] for a specific country
    public static void writeClientsForSpecificCountry (WorkingCountry country) {
        //
    }

    /// Saves all [Client] for all countries
    public static void writeClientsForEveryCountry () {
        Data.instance.getMapAccountsCountries().values().forEach(Memory::writeClientsForSpecificCountry);
    }

    ////////////////////////////////////////////////////
    /// COMPLEX READINGS
    ////////////////////////////////////////////////////

    /// Reads [Client] and returns a [HashSet]
    public static HashSet<Client> readClients () {
        //
    }

    /// Reads [Payment] and returns a [HashSet]
    public static HashSet<Payment> readPayments () {
        //
    }

    ////////////////////////////////////////////////////
    /// NON-COMPLEX SAVINGS AND READINGS
    ////////////////////////////////////////////////////

    /// Saves [Product]
    public static void writeProducts () {
        //
    }

    /// Reads [Product] and returns a [HashSet]
    public static HashSet<Product> readProducts() {
        //
    }

    /// Saves [WorkingCountry]
    public static void writeWorkingCountries () {
        //
    }

    /// Reads [WorkingCountry] and returns a [HashMap]
    public static HashMap<String, WorkingCountry> readWorkingCountries () {
        //
    }

    /// Saves [Country]
    public static void writeCountries () {
        //
    }

    /// Reads [Country] and returns a [HashMap]
    public static HashMap<String, Country> readCountries () {
        //
    }

    /// Saves [Currency]
    public static void writeCurrencies () {
        //
    }

    /// Reads [Currency] and returns a [HashMap]
    public static HashSet<Currency> readCurrencies () {
        //
    }

    ////////////////////////////////////////////////////
    /// BINDER
    ////////////////////////////////////////////////////

    /// Binds data together to let the program find links faster
    public static void bindData () {
        //
    }

}
