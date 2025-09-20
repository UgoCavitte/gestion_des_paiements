package com.gestion_paiements.controllers;

import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;

/*
 * This is the general tab controller.
 * It lets switch between different countries.
 * Inside it, you can either check the main bank account or different sources.
 */

public class MainTabPaneController {

    // Uses an array list to get them sorted more easily
    ArrayList<Tab> tabsCountries = new ArrayList<>();

    @FXML
    private TabPane tabPaneAccounts;

    // This method creates the tabs for each country in which the user has an account
    @FXML
    private void initialize() {

        tabsCountries.clear();

        for (Country country : Data.instance.getSetAccountsCountries()) {
            tabsCountries.add(new Tab(country.getName()));
            // TODO This is minimalistic for the moment
        }

        tabPaneAccounts.getTabs().clear();
        tabPaneAccounts.getTabs().addAll(tabsCountries);
    }

}
