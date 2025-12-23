package com.gestion_paiements.util;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;

import java.util.Objects;

public class Destinations {

    public static Destination fromStringToDestination(String string) {
        return Data.instance.getSetDestinations().stream().filter(d -> Objects.equals(d.getName(), string)).findFirst().orElse(null);
    }

}
