package com.gestion_paiements.types;

import com.gestion_paiements.types.payments.PaymentFromClient;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class SampleData {

    public static void init() {

        // Products
        Data.instance.getSetProducts().add(new Product("ind", "Cours individuel"));
        Data.instance.getSetProducts().add(new Product("club", "Séance au club de discussion"));
        Product[] products = Data.instance.getSetProducts().toArray(new Product[0]);

        // Client 1
        Client sampleClient = new Client(0, "Michel", "France", null);
        PaymentFromClient paymentFromMichel = new PaymentFromClient(
                0,
                sampleClient,
                new Destination(DestinationType.bankAccount, "France", null),
                Date.from(Instant.now()),
                        Date.from(Instant.now()),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                Set.of(new PurchasedProduct(2, products[0])),
                null
                        );
        sampleClient.setPayments(Set.of(paymentFromMichel));

        // Client 2
        Client sampleClient2 = new Client(1, "Jacques", "Canada", null);
        PaymentFromClient paymentFromJacques = new PaymentFromClient(
                1,
                sampleClient2,
                new Destination(DestinationType.bankAccount, "France", null),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                Set.of(new PurchasedProduct(2, products[0])),
                null
        );
        sampleClient2.setPayments(Set.of(paymentFromJacques));

        Client[] clients = {sampleClient, sampleClient2};
        Data.instance.setSetClients(new HashSet<>(Arrays.stream(clients).toList()));

        // Countries
        String[] countries = {"France", "Russie", "Ukraine", "Kazakhstan"};
        Data.instance.setSetCountries(new HashSet<>(Arrays.stream(countries).toList()));

        // Currencies
        String[] currencies = {"EUR", "RUB"};
        Data.instance.setSetCurrencies(new HashSet<>(Arrays.stream(currencies).toList()));

    }

}
