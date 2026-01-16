package com.gestion_paiements.util;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public abstract class Formatters {

    public static final DecimalFormat decimalsOnlyIfNecessary = new DecimalFormat("0.##");

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}
