package com.gestion_paiements.util;

import com.gestion_paiements.types.Amount;
import com.gestion_paiements.types.Currency;
import com.gestion_paiements.types.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class Currencies {

    public static @NotNull String fromAmountToString (@NotNull Amount amount) {
        return amount.getAmount() + " " + amount.getCurrency().getName();
    }

    public static Currency fromStringToCurrency(String name) {
        return Data.instance.getSetCurrencies().stream().filter(c -> Objects.equals(c.getName(), name)).findFirst().orElse(null);
    }

}
