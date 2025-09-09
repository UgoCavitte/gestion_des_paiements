package com.gestion_paiements.gestion_des_paiements.types;

import com.gestion_paiements.gestion_des_paiements.types.payments.PaymentFromClient;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

public abstract class SampleData {

    static Instance instance = new Instance();

    public static void init() {
        // Products
        Product[] products = new Product[2];
        products[0] = new Product("ind", "Cours individuel");
        products[1] = new Product("club", "Séance au club de discussion");
        instance.setProducts(Set.of(products));

        // Client
        Client sampleClient = new Client(0, "Michel", null);
        PaymentFromClient paymentFromMichel = new PaymentFromClient(
                0,
                sampleClient,
                new Destination(DestinationType.bankAccount, "France", null),
                Date.from(Instant.parse("20250909")),
                        Date.from(Instant.parse("20250909")),
                new Amount(10, "EUR"),
                new Amount(9.6, "EUR"),
                Set.of(new PurchasedProduct(2, products[0])),
                null
                        );
        sampleClient.setPayments(Set.of(paymentFromMichel));

    }

}
