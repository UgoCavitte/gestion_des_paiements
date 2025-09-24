package com.gestion_paiements.data;

import com.gestion_paiements.types.*;
import com.gestion_paiements.types.Currency;
import com.gestion_paiements.types.payments.PaymentFromClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public abstract class SampleData {



    public static void init() {

        // Accounts countries
        Data.instance.getMapAccountsCountries().put("France", new WorkingCountry("France"));
        HashMap<String, Destination> accountsForFrance = new HashMap<>();
        accountsForFrance.put("LCL", new Destination(DestinationType.bankAccount, "LCL"));
        accountsForFrance.put("Stripe", new Destination(DestinationType.platform, "Stripe"));
        Data.instance.getMapAccountsCountries().get("France").setAccountsAndPlatforms(accountsForFrance);

        Data.instance.getMapAccountsCountries().put("Russie", new WorkingCountry("Russie"));


        // Products
        Data.instance.getSetProducts().add(new Product("ind", "Cours individuel"));
        Data.instance.getSetProducts().add(new Product("club", "Séance au club de discussion"));
        Product[] products = Data.instance.getSetProducts().toArray(new Product[0]);

        // Client 1
        Client sampleClient = new Client(0, "Michel", new Country("France"), null);
        PaymentFromClient paymentFromMichel = new PaymentFromClient(
                0,
                sampleClient,
                Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("LCL"),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                Set.of(new PurchasedProduct(2, products[0])),
                null
                        );

        sampleClient.getPayments().add(paymentFromMichel);

        sampleClient.getPayments().add(new PaymentFromClient(
                1,
                sampleClient,
                Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("Stripe"),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(20, "EUR"),
                new Amount(19.2, "EUR"),
                Set.of(new PurchasedProduct(3, products[1])),
                null
        ));

        // Client 2
        Client sampleClient2 = new Client(1, "Jacques", new Country("Russie"), null);
        PaymentFromClient paymentFromJacques = new PaymentFromClient(
                1,
                sampleClient2,
                new Destination(DestinationType.bankAccount, null),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                Set.of(new PurchasedProduct(2, products[0])),
                null
        );
        sampleClient2.getPayments().add(paymentFromJacques);

        Client[] clients = {sampleClient, sampleClient2};
        Data.instance.setSetClients(new HashSet<>(Arrays.stream(clients).toList()));

        // Client countries
        Data.instance.getSetClientsCountries().add(new Country("France"));
        Data.instance.getSetClientsCountries().add(new Country("Russie"));
        Data.instance.getSetClientsCountries().add(new Country("Ukraine"));

        // Payments
        for (PaymentFromClient p : sampleClient.getPayments()) {
            Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("LCL").getTransfers().add(p);
        }


        // Currencies
        Data.instance.getSetCurrencies().add(new com.gestion_paiements.types.Currency("EUR"));
        Data.instance.getSetCurrencies().add(new Currency("RUB"));

    }

}
