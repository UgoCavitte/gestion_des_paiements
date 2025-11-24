package com.gestion_paiements.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_paiements.types.*;
import com.gestion_paiements.types.payments.Payment;

import java.io.File;
import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;

/// This class is used to save data into JSON files
/// You can either save everything, which is not optimal for resources and will create lag when the save will become big
/// enough, either save some data separately

public abstract class Memory {

    static ObjectMapper mapper = new ObjectMapper();

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

    /// Saves a specific [Destination]
    public static void writeSpecificDestination (Destination destination) {
        //
    }

    /// Saves all [Destination]
    public static void writeEveryDestinations () {
        //
    }

    ////////////////////////////////////////////////////
    /// COMPLEX READINGS
    ////////////////////////////////////////////////////

    /// Reads [Client] and returns a [HashSet]
    /// Clients are saved in individual files
    public static HashSet<Client> readClients () {
        //
        return null;
    }

    /// Reads [Payment] and returns a [HashSet]
    public static HashSet<Payment> readPayments () {
        //
        return null;
    }

    ////////////////////////////////////////////////////
    /// NON-COMPLEX SAVINGS AND READINGS
    /// ALL THE FOLLOWING DATA IS SAVED IN ONE FILE
    /// FOR EACH TYPE
    ////////////////////////////////////////////////////

    /// Saves [Product]
    public static void writeProducts () {
        File file = new File("products.json");

        try {
            mapper.writeValue(file, Data.instance.getSetProducts());
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }

    }

    /// Reads [Product] and sets the Instance [HashSet]
    public static void readProducts() {
        File file = new File("products.json");

        try {
            Data.instance.setSetProducts(mapper.readValue(file, new TypeReference<HashSet<Product>>() {}));
        } catch (IOException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }
    }

    /// Saves [WorkingCountry]
    public static void writeWorkingCountries () {
        //
    }

    /// Reads [WorkingCountry] and returns a [HashMap]
    public static HashMap<String, WorkingCountry> readWorkingCountries () {
        //
        return null;
    }

    /// Saves [Country]
    public static void writeCountries () {
        //
    }

    /// Reads [Country] and returns a [HashMap]
    public static HashMap<String, Country> readCountries () {
        //
        return null;
    }

    /// Saves [Currency]
    public static void writeCurrencies () {
        //
    }

    /// Reads [Currency] and returns a [HashMap]
    public static HashSet<Currency> readCurrencies () {
        //
        return null;
    }

    ////////////////////////////////////////////////////
    /// BINDER
    ////////////////////////////////////////////////////

    /// Binds data together to let the program find links faster
    public static void bindData () {
        //
    }

    ////////////////////////////////////////////////////
    /// GENERAL SAVING
    ////////////////////////////////////////////////////

    public static void generalSave () {
        writeProducts();
        System.out.println("Written !");
    }

}
