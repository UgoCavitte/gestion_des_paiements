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

    static List<ToBindWorkingCountry> unboundWorkingCountries = new ArrayList<>();
    static List<ToBindClient> unboundClients = new ArrayList<>();
    static List<ToBindPayment> unboundPayments = new ArrayList<>();
    static List<ToBindDestination> unboundDestinations = new ArrayList<>();

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
                    .toList();

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
                    .toList();

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
            unboundWorkingCountries = mapper.readValue(file, new TypeReference<List<ToBindWorkingCountry>>() {});
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

    /// Reads all [Destination] elements from one file
    public static void readDestinations () {

        if (!Files.exists(dataPath)) {
            System.out.println("Data directory not found: " + dataPath);
            return;
        }

        try {
            File file = dataPath.resolve(destinationsFileName).toFile();
            unboundDestinations = mapper.readValue(file, new TypeReference<List<ToBindDestination>>() {});
        } catch (IOException e) {
            System.out.println("Error while deserializing Destination elements: " + e.getMessage());
        }
    }

    ////////////////////////////////////////////////////
    /// BINDER
    ////////////////////////////////////////////////////

    /// Binds data together to let the program find links faster
    /// We need to keep everything in order to synchronize already bound and unbound data from elements
    public static void bindData () {

        // We start by making the not unbound elements
        List<WorkingCountry> workingCountries = unboundWorkingCountries.stream().map(WorkingCountry::new).toList();
        List<Destination> destinations = unboundDestinations.stream().map(Destination::new).toList();
        List<Client> clients = unboundClients.stream().map(Client::new).toList();

        // -------------------------
        // EASY BINDING: currency, country, destinationType
        // -------------------------

        // Client elements only need their country to be bound
        for (int i = 0 ; i < clients.size() ; i++) {
            int finalI = i;
            clients.get(i)
                    .setCountry(Data.instance.getMapClientsCountries()
                                            .values().stream()
                                            .filter(t -> t.getId() == unboundClients.get(finalI).getCountry())
                                            .findFirst().orElse(null));
            if (clients.get(i).getCountry() == null) {
                throw new RuntimeException("Country not found with the given ID while binding Client elements.");
            }
        }

        // WorkingCountry elements only need their destination to be bound
        for (int i = 0; i < workingCountries.size(); i++) {
            int finalI = i;

            Set<Destination> destinationsForThisCountry = destinations.stream()
                                                            .filter(d -> unboundWorkingCountries.get(finalI).getDestinations().contains(d.getId()))
                                                            .collect(Collectors.toSet());

            workingCountries.get(i)
                    .setAccountsAndPlatforms(
                            destinationsForThisCountry.stream()
                            .collect(Collectors.toMap(Destination::getName, e -> e, (u, v) -> u, HashMap::new)));
        }


        // Destination elements need their currency, type and working country
        for (int i = 0; i < destinations.size(); i++) {
            int finalI = i;

            Destination currentDestination = destinations.get(i);

            // CURRENCY
            currentDestination.setCurrency(
                    Data.instance.getSetCurrencies().stream()
                            .filter(c -> c.getId() == unboundDestinations.get(finalI).getCurrency())
                            .findFirst().orElse(null)
            );

            if (destinations.get(i).getCurrency() == null) {
                throw new RuntimeException("Currency not found with the given ID while binding Destination elements.");
            }

            // TYPE
            currentDestination.setDestinationType(
                    mapFromIntegerToDestinationType.get(unboundDestinations.get(finalI).getType())
            );

            if (destinations.get(i).getDestinationType() == null) {
                throw new RuntimeException("DestinationType not found with the given ID while binding Destination elements.");
            }

            // WORKING COUNTRY
            currentDestination.setCountry(
                    Data.instance.getMapAccountsCountries()
                            .values().stream()
                            .filter(c -> c.getId() == unboundDestinations.get(finalI).getWorkingCountry())
                            .findFirst().orElse(null)
            );

            if (destinations.get(i).getCountry() == null) {
                throw new RuntimeException("WorkingCountry not found with the given ID while binding Destination elements.");
            }

        }


        // -------------------------
        // HARD BINDING: payments
        // -------------------------


        // Payment elements need their sender
        // We start by sorting [Payment] elements by type
       // List<Payment> payments = unboundPayments.stream().map(Payment::new).toList();

        // WorkingCountry elements need their platforms

        // Destination elements need their WorkingCountry, currency and transfers

        // Client elements need their payments and country
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
        writeDestinations();
        System.out.println("Written !");
    }

    /// Reads all saved data and sets the Instance
    public static void generalRead () {
        readProducts();
        readCountries();
        readCurrencies();
        readWorkingCountries();
        readPayments();
        readDestinations();
        System.out.println("Read!");
    }

}
