package com.gestion_paiements.util;

import com.gestion_paiements.types.PurchasedProduct;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PurchasedProducts {

    static public String fromSetToString(Set<PurchasedProduct> set) {
        if (set.isEmpty()) return "";

        ArrayList<PurchasedProduct> list = new ArrayList<>(set);

        StringBuilder string = new StringBuilder(list.getFirst().getQuantity() + " " + list.getFirst().getProduct().getShortName());
        list.removeFirst();

        if (list.isEmpty()) return string.toString();

        for (PurchasedProduct p : list) {
            string.append(", ").append(p.getQuantity()).append(" ").append(p.getProduct().getShortName());
        }

        return string.toString();
    }

}
