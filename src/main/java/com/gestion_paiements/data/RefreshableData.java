package com.gestion_paiements.data;

import com.gestion_paiements.util.Refreshable;

import java.util.ArrayList;

public abstract class RefreshableData {

    private static final ArrayList<Refreshable> toRefresh = new ArrayList<>();

    public static void refreshTables() {
        ArrayList<Refreshable> snapshot = new ArrayList<>(toRefresh);

        snapshot.forEach(Refreshable::refreshElement);
    }

    public static ArrayList<Refreshable> getToRefresh() {
        return toRefresh;
    }
}
