package com.gestion_paiements.util;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Dates {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final StringConverter<LocalDate> dateStringConverter = new StringConverter<>() {
        @Override
        public String toString(LocalDate localDate) {
            if (localDate != null) {
                return Dates.dateFormatter.format(localDate);
            }
            return "";
        }

        @Override
        public LocalDate fromString(String s) {
            if (s != null && !s.isEmpty()) {
                return LocalDate.parse(s, Dates.dateFormatter);
            }

            return null;
        }
    };

    static public String fromDateToString (LocalDate date) {
        return dateFormatter.format(date);
    }

}
