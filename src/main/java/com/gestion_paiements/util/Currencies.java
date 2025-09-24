package com.gestion_paiements.util;

import com.gestion_paiements.types.Amount;
import org.jetbrains.annotations.NotNull;

public abstract class Currencies {

    public static @NotNull String fromAmountToString (@NotNull Amount amount) {
        return amount.getAmount() + " " + amount.getCurrency();
    }

}
