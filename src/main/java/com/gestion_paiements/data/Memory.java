package com.gestion_paiements.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_paiements.saving_formats.ToBindClient;
import com.gestion_paiements.saving_formats.ToBindDestination;
import com.gestion_paiements.saving_formats.ToBindPayment;
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
    static Set<ToBindPayment> unboundPayments = new HashSet<>();

    static Path clientsDirPath = Paths.get("data", "clients");
    static Path paymentsDirPath = Paths.get("data", "payments");
    static Path dataPath = Paths.get("data");

    static String currenciesFileName = "currencies.json";
    static String destinationsFileName = "destinations.json";
    static String workingCountriesFileName = "working_countries.json";
    static String productsFileName = "products.json";
    static String countriesFileName = "countries.json";

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

    /// Saves or resaves all [Payment]
    // TODO Redo the saving process not to conflict with the files quantity limit (is it really needed?)
    public static void writePayments () {

        try {
            Files.createDirectories(paymentsDirPath);

            for (Payment payment : Data.instance.getSetPayments()) {
                ToBindPayment toSave = new ToBindPayment(payment);
                String fileName = "payment_" + payment.getId() + ".json";
                File file = paymentsDirPath.resolve(fileName).toFile();
                mapper.writeValue(file, toSave);
            }
        } catch (IOException e) {
            System.out.println("Error while serializing payments: " + e.getMessage());
        }
    }

    public static void writeSpecificPayment (Payment payment) {
        try {
            Files.createDirectories(paymentsDirPath);
            File file = paymentsDirPath.resolve("payment_" + payment.getId() + ".json").toFile();
            mapper.writeValue(file, new ToBindPayment(payment));
        } catch (IOException e) {
            System.out.println("Error while serializing payment " + payment.getId() + " from " + payment.getSender().getName() + ": " + e.getMessage());
        }
    }

    /// Saves or resaves all data for [Client] elements in separate files in a separate directory
    public static void writeClients () {

        try {
            Files.createDirectories(clientsDirPath);

            for (Client client : Data.instance.getSetClients()) {
                ToBindClient toSave = new ToBindClient(client);
                String fileName = "client_" + client.getId() + ".json";
                File file = clientsDirPath.resolve(fileName).toFile();
                mapper.writeValue(file, toSave);
            }

        } catch (IOException e) {
            System.out.println("Error while serializing Clients elements: " + e.getMessage());
        }
    }

    /// Writes a specific [Client]
    public static void writeSpecificClient (Client client) {

        try {
            Files.createDirectories(clientsDirPath);
            File file = clientsDirPath.resolve("client_" + client.getId() + ".json").toFile();
            mapper.writeValue(file, new ToBindClient(client));
        } catch (IOException e) {
            System.out.println("Error while serializing Client" + client.getId() + ": " + e.getMessage());
        }
    }

    /// Saves all [Destination] in one file
    public static void writeDestinations () {
        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(destinationsFileName).toFile();
            mapper.writeValue(file, Data.instance.getSetDestinations().stream().map(ToBindDestination::new));
        } catch (IOException e) {
            System.out.println("Error while serializing Destination elements: " + e.getMessage());
        }
    }

    ////////////////////////////////////////////////////
    /// COMPLEX READINGS
    ////////////////////////////////////////////////////

    /// Reads all [Client]
    public static void readClients () {

        if (!Files.exists(clientsDirPath)) {
            System.out.println("Client data directory not found: " + clientsDirPath);
            return;
        }

        try (Stream<Path> pathStream = Files.list(clientsDirPath)) {

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
            System.out.println("Error while deserializing Client elements: " + e.getMessage());
        }
    }

    /// Reads [Payment] elements and set this class HashSet of [ToBindPayment]
    public static void readPayments () {

        if (!Files.exists(paymentsDirPath)) {
            System.out.println("Payments data directory not found: " + paymentsDirPath);
            return;
        }

        try (Stream<Path> pathStream = Files.list(paymentsDirPath)) {

            unboundPayments = pathStream
                    .filter(path -> !Files.isDirectory(path) && path.toString().endsWith(".json"))
                    .map(path -> {
                        try {
                            return mapper.readValue(path.toFile(), ToBindPayment.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            System.out.println("Error while deserializing Payment elements: " + e.getMessage());
        }
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

        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(workingCountriesFileName).toFile();
            mapper.writeValue(file, toSave);
        } catch (IOException e) {
            System.out.println("Error while serializing WorkingCountry elements: " + e.getMessage());
        }

    }

    /// Reads [WorkingCountry] and sets the Memory set of [ToBindWorkingCountry]
    public static void readWorkingCountries () {

        if (!Files.exists(dataPath)) {
            System.out.println("Data directory not found: " + dataPath);
            return;
        }

        try {
            File file = dataPath.resolve(workingCountriesFileName).toFile();
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

        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(productsFileName).toFile();
            mapper.writeValue(file, Data.instance.getSetProducts());
        } catch (IOException e) {
            System.out.println("Error while serializing Product elements: " + e.getMessage());
        }

    }

    /// Reads [Product] and sets the Instance [HashSet]
    public static void readProducts() {

        if (!Files.exists(dataPath)) {
            System.out.println("Data directory not found: " + dataPath);
            return;
        }

        try {
            File file = dataPath.resolve(productsFileName).toFile();
            Data.instance.setSetProducts(mapper.readValue(file, new TypeReference<HashSet<Product>>() {}));
        } catch (IOException e) {
            System.out.println("Error while deserializing Product elements: " + e.getMessage());
        }
    }

    /// Saves [Country]
    public static void writeCountries () {

        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(countriesFileName).toFile();
            mapper.writeValue(file, Data.instance.getMapClientsCountries().values());
        } catch (IOException e) {
            System.out.println("Error while serializing Country elements: " + e.getMessage());
        }
    }

    /// Reads [Country] and returns a [HashMap]
    public static void readCountries () {

        if (!Files.exists(dataPath)) {
            System.out.println("Data directory not found: " + dataPath);
            return;
        }

        try {
            File file = dataPath.resolve(countriesFileName).toFile();

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

        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(currenciesFileName).toFile();
            mapper.writeValue(file, Data.instance.getSetCurrencies());
        } catch (IOException e) {
            System.out.println("Error while serializing Currency elements: " + e.getMessage());
        }
    }

    /// Reads [Currency] and sets the Instance [HashMap]
    public static void readCurrencies () {

        if (!Files.exists(dataPath)) {
            System.out.println("Data directory not found: " + dataPath);
            return;
        }

        try {
            File file = dataPath.resolve(currenciesFileName).toFile();
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
        writePayments();
        System.out.println("Written !");
    }

    /// Reads all saved data and sets the Instance
    public static void generalRead () {
        readProducts();
        readCountries();
        readCurrencies();
        readWorkingCountries();
        readPayments();
        System.out.println("Read!");
    }

}
