package com.gestion_paiements.util;

import com.gestion_paiements.types.PurchasedProduct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public abstract class PurchasedProducts {

    static public List<String> fromSetToStrings (Set<PurchasedProduct> set) {
        return set.stream()
                .sorted(Comparator.comparing(p -> p.getProduct().getId()))
                .map(p -> p.getQuantity() + p.getProduct().getShortName()).toList();
    }

}
