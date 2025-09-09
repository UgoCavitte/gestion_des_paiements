package com.gestion_paiements.gestion_des_paiements;

import com.gestion_paiements.gestion_des_paiements.types.Instance;
import com.gestion_paiements.gestion_des_paiements.types.SampleData;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {



        SampleData.init();

        Application.launch(Main.class, args);
    }
}
