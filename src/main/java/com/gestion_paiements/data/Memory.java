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
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.PaymentFromPlatform;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// This class is used to save data into JSON files
/// You can either save everything, which is not optimal for resources and will create lag when the save will become big
/// enough, either save some data separately

public abstract class Memory {

    static final ObjectMapper mapper = new ObjectMapper();

    static List<ToBindWorkingCountry> unboundWorkingCountries = new ArrayList<>();
    static List<ToBindClient> unboundClients = new ArrayList<>();
    static List<ToBindPayment> unboundPayments = new ArrayList<>();
    static List<ToBindDestination> unboundDestinations = new ArrayList<>();

    static final Path clientsDirPath = Paths.get("data", "clients");
    static final Path paymentsDirPath = Paths.get("data", "payments");
    public static final Path dataPath = Paths.get("data");

    static final String currenciesFileName = "currencies.json";
    static final String destinationsFileName = "destinations.json";
    static final String workingCountriesFileName = "working_countries.json";
    static final String productsFileName = "products.json";
    static final String countriesFileName = "countries.json";

    ////////////////////////////////////////////////////
    /// MAPS
    ////////////////////////////////////////////////////

    public static final Map<Integer, DestinationType> mapFromIntegerToDestinationType = Map.of(
            0, DestinationType.bankAccount,
            1, DestinationType.platform
    );

    public static final Map<DestinationType, Integer> mapFromDestinationTypeToInteger = Map.of(
            DestinationType.bankAccount, 0,
            DestinationType.platform, 1
    );

    public static final Map<Integer, Class<?>> mapFromIntegerToSenderType = Map.of(
            0, Client.class,
            1, Destination.class
    );

    public static final Map<Class<?>, Integer> mapFromSenderTypeToInteger = Map.of(
            Client.class, 0,
            Destination.class, 1
    );

    public static final Map<StatusPaymentFromClient, Integer> mapFromStatusPaymentToInteger = Map.of(
            StatusPaymentFromClient.SENT_TO_THE_BANK, 0,
            StatusPaymentFromClient.SENT_TO_A_PLATFORM, 1,
            StatusPaymentFromClient.NOT_ON_A_PLATFORM, 2
    );

    public static final Map<Integer, StatusPaymentFromClient> mapFromIntegerToStatusPayment = Map.of(
            0, StatusPaymentFromClient.SENT_TO_THE_BANK,
            1, StatusPaymentFromClient.SENT_TO_A_PLATFORM,
            2, StatusPaymentFromClient.NOT_ON_A_PLATFORM
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
            throw new RuntimeException("Error while serializing payments: " + e.getMessage());
        }
    }

    public static void writeSpecificPayment (Payment payment) {
        try {
            Files.createDirectories(paymentsDirPath);
            File file = paymentsDirPath.resolve("payment_" + payment.getId() + ".json").toFile();
            mapper.writeValue(file, new ToBindPayment(payment));
        } catch (IOException e) {
            throw new RuntimeException("Error while serializing payment " + payment.getId() + " from " + payment.getSender().getName() + ": " + e.getMessage());
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
            throw new RuntimeException("Error while serializing Clients elements: " + e.getMessage());
        }
    }

    /// Writes a specific [Client]
    public static void writeSpecificClient (Client client) {

        try {
            Files.createDirectories(clientsDirPath);
            File file = clientsDirPath.resolve("client_" + client.getId() + ".json").toFile();
            mapper.writeValue(file, new ToBindClient(client));
        } catch (IOException e) {
            throw new RuntimeException("Error while serializing Client" + client.getId() + ": " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing Client elements: " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing Payment elements: " + e.getMessage());
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
            throw new RuntimeException("Error while serializing WorkingCountry elements: " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing WorkingCountry elements: " + e.getMessage());
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
            throw new RuntimeException("Error while serializing Product elements: " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing Product elements: " + e.getMessage());
        }
    }

    /// Saves [Country]
    public static void writeCountries () {

        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(countriesFileName).toFile();
            mapper.writeValue(file, Data.instance.getMapClientsCountries().values());
        } catch (IOException e) {
            throw new RuntimeException("Error while serializing Country elements: " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing Country elements: " + e.getMessage());
        }
    }

    /// Saves [Currency]
    public static void writeCurrencies () {

        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(currenciesFileName).toFile();
            mapper.writeValue(file, Data.instance.getSetCurrencies());
        } catch (IOException e) {
            throw new RuntimeException("Error while serializing Currency elements: " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing Currency elements: " + e.getMessage());
        }
    }

    /// Saves all [Destination] in one file
    public static void writeDestinations () {
        try {
            Files.createDirectories(dataPath);
            File file = dataPath.resolve(destinationsFileName).toFile();
            mapper.writeValue(file, Data.instance.getSetDestinations().stream().map(ToBindDestination::new).collect(Collectors.toSet()));
        } catch (IOException e) {
            throw new RuntimeException("Error while serializing Destination elements: " + e.getMessage());
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
            throw new RuntimeException("Error while deserializing Destination elements: " + e.getMessage());
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

        // Saves in the instance
        // This must be done now to let code use Data.instance
        Data.instance.setSetClients(new HashSet<>(clients));
        Data.instance.setMapAccountsCountries(workingCountries.stream()
                .collect(Collectors.toMap(
                        WorkingCountry::getName,
                        e -> e,
                        (u, v) -> u,
                        HashMap::new
                ))
        );
        Data.instance.setSetDestinations(new HashSet<>(destinations));

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
                    Data.instance.getMapAccountsCountries() // TODO This is not set at this moment
                            .values().stream()
                            .filter(c -> c.getId() == unboundDestinations.get(finalI).getWorkingCountry())
                            .findFirst().orElse(null)
            );

            if (destinations.get(i).getCountry() == null) {
                throw new RuntimeException("WorkingCountry not found with the given ID " + unboundDestinations.get(i).getWorkingCountry() + " while binding Destination elements.");
            }

        }


        // -------------------------
        // HARD BINDING: payments
        // -------------------------

        // We start by separating types of Payment elements
        List<ToBindPayment> unboundPaymentsFromClients = unboundPayments.stream()
                .filter(p -> mapFromIntegerToSenderType.get(p.getSenderType()) == Client.class)
                .toList();
        List<PaymentFromClient> paymentsFromClients = unboundPaymentsFromClients.stream()
                .map(PaymentFromClient::new)
                .toList();

        List<ToBindPayment> unboundPaymentsFromPlatforms = unboundPayments.stream()
                .filter(p -> mapFromIntegerToSenderType.get(p.getSenderType()) == Destination.class)
                .toList();
        List<PaymentFromPlatform> paymentsFromPlatforms = unboundPaymentsFromPlatforms.stream()
                .map(PaymentFromPlatform::new)
                .toList();

        // Saves payments directly to Data.instance to let the code use it
        Data.instance.setSetPayments(new HashSet<>(Stream.concat(paymentsFromClients.stream(), paymentsFromPlatforms.stream()).collect(Collectors.toSet())));

        // PaymentFromClient elements binding
        // Need to bind: dateSent, dateReceived, amountSent, amountReceived, destination, sender + purchasedProducts, status
        for (int i = 0; i < paymentsFromClients.size(); i++) {
            PaymentFromClient currentPayment = paymentsFromClients.get(i);
            ToBindPayment currentToBind = unboundPaymentsFromClients.get(i);

            // Dates
            currentPayment.setDateSent(LocalDate.parse(currentToBind.getDateSent()));
            currentPayment.setDateReceived(LocalDate.parse(currentToBind.getDateReceived()));

            // Amounts
            currentPayment.setSentAmount(
                    new Amount(
                            currentToBind.getAmountSent(),
                            Data.instance.getSetCurrencies().stream().filter(p -> p.getId() == currentToBind.getCurrencySent()).findFirst().orElse(null)
                    )
            );

            if (currentPayment.getSentAmount().getCurrency() == null) {
                throw new RuntimeException("Currency not found with the given ID (" + currentToBind.getCurrencySent() + ") while binding PaymentFromClient element with the ID " + currentToBind.getId());
            }

            currentPayment.setReceivedAmount(
                    new Amount(
                            currentToBind.getAmountReceived(),
                            Data.instance.getSetCurrencies().stream().filter(p -> p.getId() == currentToBind.getCurrencyReceived()).findFirst().orElse(null)
                    )
            );

            if (currentPayment.getSentAmount().getCurrency() == null) {
                throw new RuntimeException("Currency not found with the given ID while binding PaymentFromClient element with the ID " + currentToBind.getId());
            }

            // Destination
            currentPayment.setDestination(
                    Data.instance.getSetDestinations().stream()
                            .filter(p -> p.getId() == currentToBind.getDestination())
                            .findFirst().orElse(null)
            );

            if (currentPayment.getDestination() == null) {
                throw new RuntimeException("Destination not found with the given ID while binding PaymentFromClient element with the ID " + currentToBind.getId());
            }

            // Sender
            currentPayment.setSender(
                    Data.instance.getSetClients().stream()
                            .filter(p -> p.getId() == currentToBind.getSenderID())
                            .findFirst().orElse(null)
            );

            if (currentPayment.getSender() == null) {
                throw new RuntimeException("Sender (Client) not found with the given ID while binding PaymentFromClient element with the ID " + currentToBind.getId());
            }

            // Purchased products
            currentPayment.setProducts(new ArrayList<>());
            for (int j = 0; j < currentToBind.getPurchasedProducts().size(); j++) {
                int finalJ = j;
                currentPayment.getProducts().add(
                        new PurchasedProduct(
                                Data.instance.getSetProducts().stream().filter(p -> p.getId() == currentToBind.getPurchasedProducts().get(finalJ)).findFirst().orElse(null),
                                currentToBind.getPurchasedProductsQuantity().get(finalJ)
                        )
                );

                if (currentPayment.getProducts().get(j).getProduct() == null) {
                    throw new RuntimeException("Product not found with the given ID (" + currentToBind.getPurchasedProducts().get(j) + ") while serializing PurchasedProduct element with the ID " + currentPayment.getId());
                }
                if (currentPayment.getProducts().get(j).getQuantity() == 0) {
                    throw new RuntimeException("Quantity could not be set for the given value (" + currentToBind.getPurchasedProductsQuantity().get(j) + ") while serializing PurchasedProduct element with the ID " + currentPayment.getId());
                }

            }

            // Status
            currentPayment.setStatus(mapFromIntegerToStatusPayment.get(currentToBind.getStatusPaymentFromClient()));

            if (currentPayment.getStatus() == null) {
                throw new RuntimeException("Status could not be set for the given ID (" + currentToBind.getStatusPaymentFromClient() + ") while binding PaymentFromClient element with the ID " + currentPayment.getId());
            }

        }

        // PaymentFromPlatform elements binding
        // Need to bind: dateSent, dateReceived, amountSent, amountReceived, destination, sender + sentPayments, commission
        for (int i = 0; i < paymentsFromPlatforms.size(); i++) {
            PaymentFromPlatform currentPayment = paymentsFromPlatforms.get(i);
            ToBindPayment currentToBind = unboundPaymentsFromPlatforms.get(i);

            // Dates
            currentPayment.setDateSent(LocalDate.parse(currentToBind.getDateSent()));
            currentPayment.setDateReceived(LocalDate.parse(currentToBind.getDateReceived()));

            // Destination
            currentPayment.setDestination(
                    Data.instance.getSetDestinations().stream()
                            .filter(p -> p.getId() == currentToBind.getDestination())
                            .findFirst().orElse(null)
            );

            if (currentPayment.getDestination() == null) {
                throw new RuntimeException("Destination not found with the given ID while binding PaymentFromPlatform element with the ID " + currentToBind.getId());
            }

            // Sender
            currentPayment.setSender(
                    Data.instance.getSetDestinations().stream()
                            .filter(p -> p.getId() == currentToBind.getSenderID())
                            .findFirst().orElse(null)
            );

            if (currentPayment.getSender() == null) {
                throw new RuntimeException("Sender (Platform) not found with the given ID while binding PaymentFromPlatform element with the ID " + currentToBind.getId());
            }

            // Commission
            currentPayment.setCommission(
                    new Amount(
                            currentToBind.getCommissionAmount(),
                            Data.instance.getSetCurrencies().stream().filter(c -> c.getId() == currentToBind.getCommissionCurrency()).findFirst().orElse(null)
                    )
            );

            if (currentPayment.getCommission().getCurrency() == null) {
                throw new RuntimeException("Currency not found with the given ID while binding Commission parameter for a PaymentFromPlatform element with the ID " + currentToBind.getId());
            }

            // Sent payments
            HashSet<PaymentFromClient> sentPaymentsBound = new HashSet<>();
            for (int j : currentToBind.getSentPayments()) {
                Payment found = Data.instance.getSetPayments().stream().filter(p -> p.getId() == j).findFirst().orElse(null);
                if (found == null) throw new RuntimeException("Payment not found with the given ID while binding sent PaymentFromClient elements for a PaymentFromPlatform element with the ID " + currentToBind.getId());
                sentPaymentsBound.add((PaymentFromClient) found);
            }
            currentPayment.setSentPayments(sentPaymentsBound);
        }


        // -------------------------
        // LATER BINDING: payments for Destination and Client elements (Sender elements)
        // -------------------------

        // Bind Payment elements for Destination elements and Client elements
        Data.instance.getSetDestinations().forEach(d -> d.getTransfers().clear());
        Data.instance.getSetClients().forEach(c -> c.getPayments().clear());
        for (Payment payment : Data.instance.getSetPayments()) {
            Destination destination = Data.instance.getSetDestinations().stream().filter(d -> d.getId() == payment.getDestination().getId()).findFirst().orElse(null);
            if (destination == null) throw new RuntimeException("Destination not found for the given ID while binding Payment elements in Destination elements. Payment ID: " + payment.getId() + ". Destination ID: " + payment.getDestination().getId());
            destination.getTransfers().add(payment);
            if (payment instanceof PaymentFromClient paymentFromClient) {
                Client client = Data.instance.getSetClients().stream().filter(c -> c.getId() == paymentFromClient.getSender().getId()).findFirst().orElse(null);
                if (client == null) throw new RuntimeException("Client not found for the given ID while binding Payment elements in Client elements. Payment ID: " + payment.getId() + ". Client ID: " + payment.getSender().getId());
                client.getPayments().add(paymentFromClient);
            }
        }


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
    public static void generalRead (BiConsumer<Double, String> progressCallback) throws InterruptedException {
        double totalSteps = 8.0;

        progressCallback.accept(0.0, "Lecture des produits...");
        readProducts();

        progressCallback.accept(1.0 / totalSteps, "Lecture des pays...");
        readCountries();

        progressCallback.accept(2.0 / totalSteps, "Lecture des devises...");
        readCurrencies();

        progressCallback.accept(3.0 / totalSteps, "Lecture des pays de travail...");
        readWorkingCountries();

        progressCallback.accept(4.0 / totalSteps, "Lecture des clients...");
        readClients();

        progressCallback.accept(5.0 / totalSteps, "Lecture des paiements...");
        readPayments();

        progressCallback.accept(6.0 / totalSteps, "Lecture des comptes et plateformes...");
        readDestinations();

        progressCallback.accept(7.0 / totalSteps, "Synchronisation des données lues...");
        bindData();

        progressCallback.accept(1.0, "Données chargées !");
        Thread.sleep(300);

        System.out.println("Read!");

        RefreshableData.refreshTables();
    }

}
