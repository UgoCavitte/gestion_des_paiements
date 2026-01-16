package com.gestion_paiements.util;

import com.gestion_paiements.types.PurchasedProduct;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class PurchasedProducts {

    static public @NotNull String fromListToString(@NotNull List<PurchasedProduct> set) {
        if (set.isEmpty()) return "";

        ArrayList<PurchasedProduct> list = new ArrayList<>(set);

        StringBuilder string = new StringBuilder(Formatters.decimalsOnlyIfNecessary.format(list.getFirst().getQuantity()) + " " + list.getFirst().getProduct().getShortName());
        list.removeFirst();

        if (list.isEmpty()) return string.toString();

        for (PurchasedProduct p : list) {
            string.append(", ").append(Formatters.decimalsOnlyIfNecessary.format(p.getQuantity())).append(" ").append(p.getProduct().getShortName());
        }

        return string.toString();
    }

}
