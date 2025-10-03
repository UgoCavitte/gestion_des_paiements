package com.gestion_paiements.util;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Product;

import java.util.Objects;

public abstract class Products {

    public static Product fromStringToProduct(String string) {
        return Data.instance.getSetProducts().stream().filter(product -> Objects.equals(product.getShortName(), string)).findFirst().orElse(null);
    }

}
