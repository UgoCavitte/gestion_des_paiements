package com.gestion_paiements.util;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;

import java.util.Objects;

public class Destinations {

    public static Destination fromStringToDestination(String string) {

        return Data.instance.getMapAccountsCountries()
                .values().stream()
                .flatMap(wc -> wc.getAccountsAndPlatforms().values().stream())
                .filter(destination -> Objects.equals(destination.getName(), string)).findFirst().orElse(null);
    }

}
