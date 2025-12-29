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
        Data.instance.getSetCurrencies().add(eur);
        Currency rub = new Currency("RUB");
        Data.instance.getSetCurrencies().add(rub);


        // Working countries
        WorkingCountry France = new WorkingCountry("France");
        Data.instance.getMapAccountsCountries().put(France.getName(), France);
        WorkingCountry Russie = new WorkingCountry("Russie");
        Data.instance.getMapAccountsCountries().put(Russie.getName(), Russie);


        // Accounts countries
        Destination LCL = new Destination(DestinationType.bankAccount, France, eur, "LCL");
        France.getAccountsAndPlatforms().put(LCL.getName(), LCL);
        Data.instance.getSetDestinations().add(LCL);
        Destination Stripe = new Destination(DestinationType.platform, France, eur, "Stripe");
        France.getAccountsAndPlatforms().put(Stripe.getName(), Stripe);
        Data.instance.getSetDestinations().add(Stripe);


        // Products
        Product ind = new Product("ind", "Cours individuel");
        Data.instance.getSetProducts().add(ind);
        Product club = new Product("club", "Séance au club de discussion");
        Data.instance.getSetProducts().add(club);


        // Client countries
        Country FranceClient = new Country("France");
        Data.instance.getMapClientsCountries().put("France", FranceClient);
        Country RussieClient = new Country("Russie");
        Data.instance.getMapClientsCountries().put("Russie", RussieClient);
        Country UkraineClient = new Country("Ukraine");
        Data.instance.getMapClientsCountries().put("Ukraine", UkraineClient);


        // Client 1
        Client michel = new Client("Michel", FranceClient, null);
        Data.instance.getSetClients().add(michel);
        PaymentFromClient paymentFromMichel = new PaymentFromClient(
                michel,
                LCL,
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, eur),
                new Amount(9.6, eur),
                List.of(new PurchasedProduct(2, ind)),
                null
                        );

        Data.instance.getSetPayments().add(paymentFromMichel);
        michel.getPayments().add(paymentFromMichel);
        LCL.getTransfers().add(paymentFromMichel);


        // Client 2
        Client jacques = new Client( "Jacques", RussieClient, null);
        Data.instance.getSetClients().add(jacques);
        PaymentFromClient paymentFromJacques = new PaymentFromClient(
                jacques,
                Stripe,
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(10, eur),
                new Amount(9.6, eur),
                List.of(new PurchasedProduct(2, club)),
                null
        );

        Data.instance.getSetPayments().add(paymentFromJacques);
        jacques.getPayments().add(paymentFromJacques);
        Stripe.getTransfers().add(paymentFromJacques);

        // Stripe
        PaymentFromClient toStripe = new PaymentFromClient(
                jacques,
                Stripe,
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                LocalDate.from(Instant.now().atZone(ZoneId.systemDefault())),
                new Amount(15, eur),
                new Amount(14.4, eur),
                List.of(new PurchasedProduct(3, club)),
                null
        );

        Data.instance.getSetPayments().add(toStripe);
        jacques.getPayments().add(toStripe);
        Stripe.getTransfers().add(toStripe);

        RefreshableData.refreshTables();

    }

}
