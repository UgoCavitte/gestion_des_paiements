package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * This is the general tab controller.
 * It lets switch between different countries.
 * Inside it, you can either check the main bank account or different sources.
 */

public class MainTabPaneController {

    // Uses an array list to get them sorted more easily
    ArrayList<Tab> tabsCountries = new ArrayList<>();

    @FXML
    private TabPane tabPaneCountries;

    // This method creates the tabs for each country in which the user has an account
    @FXML
    private void initialize() {

        tabsCountries.clear();

        for (Country country : Data.instance.getMapAccountsCountries().values()) {

            Tab tab = new Tab(country.getName());
            try {

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("country-tab-view.fxml"));
                CountryTabController controller = new CountryTabController();
                controller.setTabCountry(country);
                Parent parent = loader.load();
                tab.setContent(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            tabsCountries.add(tab);
        }

        tabPaneCountries.getTabs().clear();
        tabPaneCountries.getTabs().addAll(tabsCountries);
    }

}
