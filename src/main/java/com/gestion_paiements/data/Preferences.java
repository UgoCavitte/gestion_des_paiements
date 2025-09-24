package com.gestion_paiements.data;

public abstract class Preferences {

    public static class ColumnsToShow {
        // Bank account
        public static boolean BAId = true;
        public static boolean BADateSent = true;
        public static boolean BADateReceived = true;
        public static boolean BAAmountSent = true;
        public static boolean BAAmountReceived = true;
        public static boolean BASender = true;
        public static boolean BAProducts = true;

        // Platform
        public static boolean PId = true;
        public static boolean PDateSent = true;
        public static boolean PDateReceived = true;
        public static boolean PAmountSent = true;
        public static boolean PAmountReceived = true;
        public static boolean PSender = true;
        public static boolean PProducts = true;
        public static boolean PSentToBank = true;
    }

}
