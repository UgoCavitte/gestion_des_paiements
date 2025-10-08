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


        // Currencies
        Currency eur = new Currency("EUR");
        Currency rub = new Currency("RUB");
        Data.instance.getSetCurrencies().add(eur);
        Data.instance.getSetCurrencies().add(rub);

        // Accounts countries
        Data.instance.getMapAccountsCountries().put("France", new WorkingCountry("France"));
        HashMap<String, Destination> accountsForFrance = new HashMap<>();
        accountsForFrance.put("LCL", new Destination(DestinationType.bankAccount, eur, "LCL"));
        accountsForFrance.put("Stripe", new Destination(DestinationType.platform, eur, "Stripe"));
        Data.instance.getMapAccountsCountries().get("France").setAccountsAndPlatforms(accountsForFrance);

        Data.instance.getMapAccountsCountries().put("Russie", new WorkingCountry("Russie"));


        // Products
        Data.instance.getSetProducts().add(new Product("ind", "Cours individuel"));
        Data.instance.getSetProducts().add(new Product("club", "Séance au club de discussion"));
        Product[] products = Data.instance.getSetProducts().toArray(new Product[0]);

        // Client 1
        Client sampleClient = new Client("Michel", new Country("France"), null);
        PaymentFromClient paymentFromMichel = new PaymentFromClient(
                sampleClient,
                Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("LCL"),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                List.of(new PurchasedProduct(2, products[0])),
                null
                        );

        sampleClient.getPayments().add(paymentFromMichel);

        sampleClient.getPayments().add(new PaymentFromClient(
                sampleClient,
                Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("Stripe"),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(20, "EUR"),
                new Amount(19.2, "EUR"),
                List.of(new PurchasedProduct(3, products[1])),
                null
        ));

        // Client 2
        Client sampleClient2 = new Client( "Jacques", new Country("Russie"), null);
        PaymentFromClient paymentFromJacques = new PaymentFromClient(
                sampleClient2,
                new Destination(DestinationType.bankAccount, eur, null),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                List.of(new PurchasedProduct(2, products[0])),
                null
        );
        sampleClient2.getPayments().add(paymentFromJacques);

        Client[] clients = {sampleClient, sampleClient2};
        Data.instance.setSetClients(new HashSet<>(Arrays.stream(clients).toList()));

        // Client countries
        Data.instance.getMapClientsCountries().put("France", new Country("France"));
        Data.instance.getMapClientsCountries().put("Russie", new Country("Russie"));
        Data.instance.getMapClientsCountries().put("Ukraine", new Country("Ukraine"));

        // Payments
        for (PaymentFromClient p : sampleClient.getPayments()) {
            Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("LCL").getTransfers().add(p);
        }



    }

}
