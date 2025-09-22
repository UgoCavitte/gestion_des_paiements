package com.gestion_paiements.util;

import com.gestion_paiements.types.Amount;
import com.gestion_paiements.types.Currency;

public abstract class Currencies {

    public static String fromAmountToString (Amount amount) {
        return amount.getAmount() + amount.getCurrency();
    }

}
