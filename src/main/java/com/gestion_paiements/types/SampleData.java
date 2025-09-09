package com.gestion_paiements.types;

import com.gestion_paiements.types.payments.PaymentFromClient;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

public abstract class SampleData {

    public static Instance instance = new Instance();

    public static void init() {

        // Products
        Product[] products = new Product[2];
        products[0] = new Product("ind", "Cours individuel");
        products[1] = new Product("club", "Séance au club de discussion");
        instance.setProducts(Set.of(products));

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

        instance.setSetClients(Set.of(sampleClient, sampleClient2));

    }

}
