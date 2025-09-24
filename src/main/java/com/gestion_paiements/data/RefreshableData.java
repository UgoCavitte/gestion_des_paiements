package com.gestion_paiements.data;

import com.gestion_paiements.util.Refreshable;

import java.util.ArrayList;

public abstract class RefreshableData {

    private static ArrayList<Refreshable> toRefresh = new ArrayList<>();

    public static void refreshTables() {
        System.out.println("Refreshing");
        toRefresh.forEach(Refreshable::refreshElement);
    }

    public static ArrayList<Refreshable> getToRefresh() {
        return toRefresh;
    }
}
