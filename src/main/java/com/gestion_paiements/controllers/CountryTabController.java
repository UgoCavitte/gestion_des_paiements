package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/// This tab contains data on accounts and platforms in a specific country.
/// This data is controlled by their own tab
/// Details :
/// - main bank account ;
/// - platforms ;
/// - synthesis.

public class CountryTabController {

    // Uses an array list to get them sorted more easily
    ArrayList<Tab> tabsAccounts = new ArrayList<>();

    @FXML
    private TabPane tabPaneAccounts;

    private Country tabCountry;

    public void setTabCountry(Country tabCountry) {
        this.tabCountry = tabCountry;
    }

    // This method creates the tabs for each country in which the user has an account
    @FXML
    private void initialize() {

        // TODO Finish this

        tabsAccounts.clear();

        for (Destination destination : )


    }

}
