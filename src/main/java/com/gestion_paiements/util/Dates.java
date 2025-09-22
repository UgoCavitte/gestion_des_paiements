package com.gestion_paiements.util;

import java.time.LocalDate;

public abstract class Dates {

    static public String fromDateToString (LocalDate date) {
        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
    }

}
