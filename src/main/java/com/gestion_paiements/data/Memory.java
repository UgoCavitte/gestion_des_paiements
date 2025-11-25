package com.gestion_paiements.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_paiements.saving_formats.ToBindWorkingCountry;
import com.gestion_paiements.types.*;
import com.gestion_paiements.types.Currency;
import com.gestion_paiements.types.payments.Payment;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/// This class is used to save data into JSON files
/// You can either save everything, which is not optimal for resources and will create lag when the save will become big
/// enough, either save some data separately

public abstract class Memory {

    static ObjectMapper mapper = new ObjectMapper();

    static Set<ToBindWorkingCountry> unboundWorkingCountries = new HashSet<>();

    ////////////////////////////////////////////////////
    /// COMPLEX SAVINGS
    ////////////////////////////////////////////////////

    /// Saves or resaves all [Payment] for a specific [WorkingCountry]
    public static void writePayments (WorkingCountry country) {
        // TODO
    }

    /// Takes no parameter, save every [Payment] for every country
    public static void writePaymentsForEveryCountry () {
        Data.instance.getMapAccountsCountries().values().forEach(Memory::writePayments);
    }

    /// Saves or resaves all data for a specific [Client]
    public static void writeClient (Client client) {
        // TODO
    }

    /// Saves all [Client] for a specific country
    public static void writeClientsForSpecificCountry (WorkingCountry country) {
        // TODO
    }

    /// Saves all [Client] for all countries
    public static void writeClientsForEveryCountry () {
        Data.instance.getMapAccountsCountries().values().forEach(Memory::writeClientsForSpecificCountry);
    }

    /// Saves a specific [Destination]
    public static void writeSpecificDestination (Destination destination) {
        // TODO
    }

    /// Saves all [Destination]
    public static void writeEveryDestinations () {
        // TODO
    }

    ////////////////////////////////////////////////////
    /// COMPLEX READINGS
    ////////////////////////////////////////////////////

    /// Reads [Client] and returns a [HashSet]
    /// Clients are saved in individual files
    public static HashSet<Client> readClients () {
        // TODO
        return null;
    }

    /// Reads [Payment] and returns a [HashSet]
    public static HashSet<Payment> readPayments () {
        // TODO
        return null;
    }

    ////////////////////////////////////////////////////
    /// QUITE COMPLEX SAVINGS AND READINGS
    /// ALL THE FOLLOWING DATA IS SAVED IN ONE FILE
    /// FOR EACH TYPE
    ////////////////////////////////////////////////////

    /// Saves [WorkingCountry]
    public static void writeWorkingCountries () {
        Set<ToBindWorkingCountry> toSave = Data.instance.getMapAccountsCountries()
                                                        .values().stream()
                                                        .map(ToBindWorkingCountry::new)
                                                        .collect(Collectors.toSet());

        File file = new File("working_countries.json");

        try {
            mapper.writeValue(file, toSave);
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }

    }

    /// Reads [WorkingCountry] and sets the Memory set of [ToBindWorkingCountry]
    public static void readWorkingCountries () {
        File file = new File("working_countries.json");

        try {
            unboundWorkingCountries = mapper.readValue(file, new TypeReference<Set<ToBindWorkingCountry>>() {});
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
    }

    ////////////////////////////////////////////////////
    /// SIMPLE SAVINGS AND READINGS (ALL DATA WRITTEN)
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

    /// Saves [Country]
    public static void writeCountries () {
        File file = new File("countries.json");

        try {
            mapper.writeValue(file, Data.instance.getMapClientsCountries().values());
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
    }

    /// Reads [Country] and returns a [HashMap]
    public static void readCountries () {
        File file = new File("countries.json");

        try {
            List<Country> loaded = mapper.readValue(file, new TypeReference<ArrayList<Country>>() {});
            List<String> labels = loaded.stream().map(Country::getName).toList();
            HashMap<String, Country> map = new HashMap<>();
            for (int i = 0; i < loaded.size(); i++) {
                map.put(labels.get(i), loaded.get(i));
            }
            Data.instance.setMapClientsCountries(map);
        } catch (IOException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }
    }

    /// Saves [Currency]
    public static void writeCurrencies () {
        File file = new File("currencies.json");

        try {
            mapper.writeValue(file, Data.instance.getSetCurrencies());
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
    }

    /// Reads [Currency] and sets the Instance [HashMap]
    public static void readCurrencies () {
        File file = new File("currencies.json");

        try {
            Data.instance.setSetCurrencies(mapper.readValue(file, new TypeReference<HashSet<Currency>>() {}));
        } catch (IOException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }
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

    /// Writes all the data for this Instance into files
    public static void generalSave () {
        writeProducts();
        writeCountries();
        writeCurrencies();
        System.out.println("Written !");
    }

    /// Reads all saved data and sets the Instance
    public static void generalRead () {
        readProducts();
        readCountries();
        readCurrencies();
    }

}
