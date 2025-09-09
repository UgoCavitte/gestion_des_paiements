package com.gestion_paiements.gestion_des_paiements.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

/*
 * This controller controls the main pane.
 * This includes the top menu and the tab container.
 * Since there can be any number of tabs, these are controlled in another controller file.
 * There also is a pane on the right-hand side to show the list of clients. This list can be sorted.
 */

public class MainController {

    @FXML
    private TabPane mainTabPane;

    @FXML
    private TableView<Object> tableClients;

    // This method must :
    // - set TabPanes inside the pane ;
    // - set the TableView content.
    @FXML
    private void initialize() {
        // TODO
    }

    @FXML
    private void ajouterUnPays() {
        // TODO
    }

    @FXML
    private void rechargerDonnees() {
        // TODO
    }

    @FXML
    private void fermerFenetre() {
        // TODO
    }

    @FXML
    void onSortClients() {
        // TODO
    }
}
