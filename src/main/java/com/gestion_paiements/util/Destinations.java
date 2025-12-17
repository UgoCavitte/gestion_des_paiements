package com.gestion_paiements.util;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Destinations {

    public static Destination fromStringToDestination(String string) {
        /*
        return Data.instance.getMapAccountsCountries()
                .values().stream()
                .flatMap(wc -> wc.getAccountsAndPlatforms().values().stream())
                .filter(destination -> Objects.equals(destination.getName(), string)).findFirst().orElse(null);*/

        Set<String> toPrint = Data.instance.getSetDestinations().stream().map(Destination::getName).collect(Collectors.toSet());
        System.out.println(toPrint);

        return Data.instance.getSetDestinations().stream().filter(d -> Objects.equals(d.getName(), string)).findFirst().orElse(null);
    }

}
