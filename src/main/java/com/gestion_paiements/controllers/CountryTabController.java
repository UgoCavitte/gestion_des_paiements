package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ArrayList;

/// This tab contains data on accounts and platforms in a specific country.
/// This data is controlled by their own tab
/// Details :
/// - main bank account ;
/// - platforms ;
/// - synthesis.

public class CountryTabController {

    // Uses an array list to get them sorted more easily
    final ArrayList<Tab> tabsAccounts = new ArrayList<>();

    @FXML
    private TabPane tabPaneAccounts;

    private WorkingCountry tabCountry;

    public void setTabCountry(WorkingCountry tabCountry) {
        this.tabCountry = tabCountry;
    }

    // This method creates the tabs for each country in which the user has an account
    @FXML
    private void initialize() {

        tabsAccounts.clear();

        for (Destination destination : tabCountry.getAccountsAndPlatforms().values()) {
            Tab tab = new Tab(destination.getName());
            try {

                FXMLLoader loader;

                if (destination.getDestinationType() == DestinationType.bankAccount) {
                    loader = new FXMLLoader(Main.class.getResource( "bank-account-view.fxml"));
                    BankAccountController controller = new BankAccountController();
                    controller.setAccount(destination);
                    loader.setController(controller);
                }

                else {
                    loader = new FXMLLoader(Main.class.getResource( "platform-view.fxml"));
                    PlatformController controller = new PlatformController();
                    controller.setPlatform(destination);
                    loader.setController(controller);
                }

                Parent parent = loader.load();
                tab.setContent(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            tabsAccounts.add(tab);
        }

        tabPaneAccounts.getTabs().clear();
        tabPaneAccounts.getTabs().addAll(tabsAccounts);


    }

}
