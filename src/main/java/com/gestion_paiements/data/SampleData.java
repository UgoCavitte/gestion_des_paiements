package com.gestion_paiements.data;

import com.gestion_paiements.types.*;
import com.gestion_paiements.types.Currency;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;

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


        // Working countries
        WorkingCountry France = new WorkingCountry("France");
        Data.instance.getMapAccountsCountries().put(France.getName(), France);
        WorkingCountry Russie = new WorkingCountry("Russie");
        Data.instance.getMapAccountsCountries().put(Russie.getName(), Russie);


        // Accounts countries
        Destination LCL = new Destination(DestinationType.bankAccount, France, eur, "LCL");
        Destination Stripe = new Destination(DestinationType.platform, France, eur, "Stripe");
        France.getAccountsAndPlatforms().put(LCL.getName(), LCL);
        France.getAccountsAndPlatforms().put(Stripe.getName(), Stripe);


        // Products
        Product ind = new Product("ind", "Cours individuel");
        Product club = new Product("club", "Séance au club de discussion");
        Data.instance.getSetProducts().add(ind);
        Data.instance.getSetProducts().add(club);


        // Client countries
        Country FranceClient = new Country("France");
        Country RussieClient = new Country("Russie");
        Country UkraineClient = new Country("Ukraine");
        Data.instance.getMapClientsCountries().put("France", FranceClient);
        Data.instance.getMapClientsCountries().put("Russie", RussieClient);
        Data.instance.getMapClientsCountries().put("Ukraine", UkraineClient);


        // Client 1
        Client sampleClient = new Client("Michel", FranceClient, null);
        Data.instance.getSetClients().add(sampleClient);
        PaymentFromClient paymentFromMichel = new PaymentFromClient(
                sampleClient,
                LCL,
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, eur),
                new Amount(9.6, eur),
                List.of(new PurchasedProduct(2, ind)),
                StatusPaymentFromClient.NOT_ON_A_PLATFORM,
                null
                        );

        Data.instance.getSetPayments().add(paymentFromMichel);
        sampleClient.getPayments().add(paymentFromMichel);
        LCL.getTransfers().add(paymentFromMichel);


        // Client 2
        Client sampleClient2 = new Client( "Jacques", RussieClient, null);
        Data.instance.getSetClients().add(sampleClient2);
        PaymentFromClient paymentFromJacques = new PaymentFromClient(
                sampleClient2,
                LCL,
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, eur),
                new Amount(9.6, eur),
                List.of(new PurchasedProduct(2, club)),
                StatusPaymentFromClient.NOT_ON_A_PLATFORM,
                null
        );

        Data.instance.getSetPayments().add(paymentFromJacques);
        sampleClient2.getPayments().add(paymentFromJacques);
        LCL.getTransfers().add(paymentFromJacques);

        // Stripe
        PaymentFromClient toStripe = new PaymentFromClient(
                sampleClient,
                Stripe,
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, eur),
                new Amount(9.6, eur),
                List.of(new PurchasedProduct(2, club)),
                StatusPaymentFromClient.SENT_TO_A_PLATFORM,
                null
        );

        Data.instance.getSetPayments().add(toStripe);
        sampleClient.getPayments().add(toStripe);
        Stripe.getTransfers().add(toStripe);



        // Payments
        for (PaymentFromClient p : sampleClient.getPayments()) {
            Data.instance.getMapAccountsCountries().get("France").getAccountsAndPlatforms().get("LCL").getTransfers().add(p);
        }



    }

}
