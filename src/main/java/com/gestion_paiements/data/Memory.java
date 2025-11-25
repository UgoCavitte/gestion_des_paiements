package com.gestion_paiements.data;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_paiements.saving_formats.ToBindClient;
import com.gestion_paiements.saving_formats.ToBindPayments;
import com.gestion_paiements.saving_formats.ToBindWorkingCountry;
import com.gestion_paiements.types.*;
import com.gestion_paiements.types.Currency;
import com.gestion_paiements.types.payments.Payment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// This class is used to save data into JSON files
/// You can either save everything, which is not optimal for resources and will create lag when the save will become big
/// enough, either save some data separately

public abstract class Memory {

    static ObjectMapper mapper = new ObjectMapper();

    static Set<ToBindWorkingCountry> unboundWorkingCountries = new HashSet<>();
    static Set<ToBindClient> unboundClients = new HashSet<>();

    ////////////////////////////////////////////////////
    /// MAPS
    ////////////////////////////////////////////////////

    public static Map<Integer, DestinationType> mapFromIntegerToDestinationType = Map.of(
            0, DestinationType.bankAccount,
            1, DestinationType.platform
    );

    public static Map<DestinationType, Integer> mapFromDestinationTypeToInteger = Map.of(
            DestinationType.bankAccount, 0,
            DestinationType.platform, 1
    );

    public static Map<Integer, Class<?>> mapFromIntegerToSenderType = Map.of(
            0, Client.class,
            1, Destination.class
    );

    public static Map<Class<?>, Integer> mapFromSenderTypeToInteger = Map.of(
            Client.class, 0,
            Destination.class, 1
    );

    ////////////////////////////////////////////////////
    /// COMPLEX SAVINGS
    ////////////////////////////////////////////////////

    /// Saves or resaves all [Payment] for a specific [WorkingCountry]
    public static void writePayments (WorkingCountry country) {
        // TODO
    }

    /// Saves or resaves all data for [Client] elements in separate files in a separate directory
    public static void writeClients () {
        Path clientDirPath = Paths.get("data", "clients");

        try {
            Files.createDirectories(clientDirPath);

            for (Client client : Data.instance.getSetClients()) {
                ToBindClient toSave = new ToBindClient(client);
                String fileName = "client_" + client.getId() + ".json";
                File file = clientDirPath.resolve(fileName).toFile();
                mapper.writeValue(file, toSave);
            }

        } catch (IOException e) {
            System.out.println("Error while serializing Clients elements: " + e.getMessage());
        }
    }

    /// Writes a specific [Client]
    public static void writeSpecificClient (Client client) {
        Path clientDirPath = Paths.get("data", "clients");

        try {
            Files.createDirectories(clientDirPath);
            File file = clientDirPath.resolve("client_" + client.getId() + ".json").toFile();
            mapper.writeValue(file, new ToBindClient(client));
        } catch (IOException e) {
            System.out.println("Error while serializing Client" + client.getId() + ": " + e.getMessage());
        }
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

    /// Reads [Client] and sets instance [HashSet]
    public static void readClients () {
        Path clientDirPath = Paths.get("data", "client");

        if (!Files.exists(clientDirPath)) {
            System.out.println("Client data directory not found: " + clientDirPath);
            return;
        }

        try (Stream<Path> pathStream = Files.list(clientDirPath)) {

            unboundClients = pathStream
                    .filter(path -> !Files.isDirectory(path) && path.toString().endsWith(".json"))
                    .map(path -> {
                        try {
                            return mapper.readValue(path.toFile(), ToBindClient.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            System.out.println("Error while deserializing Clients elements: " + e.getMessage());
        }
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
            System.out.println("Error while serializing WorkingCountry elements: " + e.getMessage());
        }

    }

    /// Reads [WorkingCountry] and sets the Memory set of [ToBindWorkingCountry]
    public static void readWorkingCountries () {
        File file = new File("working_countries.json");

        try {
            unboundWorkingCountries = mapper.readValue(file, new TypeReference<Set<ToBindWorkingCountry>>() {});
        } catch (IOException e) {
            System.out.println("Error while deserializing WorkingCountry elements: " + e.getMessage());
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
            System.out.println("Error while serializing Product elements: " + e.getMessage());
        }

    }

    /// Reads [Product] and sets the Instance [HashSet]
    public static void readProducts() {
        File file = new File("products.json");

        try {
            Data.instance.setSetProducts(mapper.readValue(file, new TypeReference<HashSet<Product>>() {}));
        } catch (IOException e) {
            System.out.println("Error while deserializing Product elements: " + e.getMessage());
        }
    }

    /// Saves [Country]
    public static void writeCountries () {
        File file = new File("countries.json");

        try {
            mapper.writeValue(file, Data.instance.getMapClientsCountries().values());
        } catch (IOException e) {
            System.out.println("Error while serializing Country elements: " + e.getMessage());
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
            System.out.println("Error while deserializing Country elements: " + e.getMessage());
        }
    }

    /// Saves [Currency]
    public static void writeCurrencies () {
        File file = new File("currencies.json");

        try {
            mapper.writeValue(file, Data.instance.getSetCurrencies());
        } catch (IOException e) {
            System.out.println("Error while serializing Currency elements: " + e.getMessage());
        }
    }

    /// Reads [Currency] and sets the Instance [HashMap]
    public static void readCurrencies () {
        File file = new File("currencies.json");

        try {
            Data.instance.setSetCurrencies(mapper.readValue(file, new TypeReference<HashSet<Currency>>() {}));
        } catch (IOException e) {
            System.out.println("Error while deserializing Currency elements: " + e.getMessage());
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
        writeWorkingCountries();
        writeClients();
        System.out.println("Written !");
    }

    /// Reads all saved data and sets the Instance
    public static void generalRead () {
        readProducts();
        readCountries();
        readCurrencies();
        readWorkingCountries();
        System.out.println("Read!");
    }

}
