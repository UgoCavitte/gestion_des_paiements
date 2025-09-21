package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Data;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/*
 * This controller controls the main pane.
 * This includes the top menu and the tab container.
 * Since there can be any number of tabs, these are controlled in another controller file.
 * There also is a pane on the right-hand side to show the list of clients. This list can be sorted.
 */

public class MainController {

    @FXML
    private AnchorPane mainTabPane;

    //////////////////////////////
    /// TABLEAU CLIENTS
    //////////////////////////////

    @FXML
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, Integer> tableClientsColumnID;

    @FXML
    private TableColumn<Client, String> tableClientsColumnName;

    @FXML
    private TableColumn<Client, String> tableClientsColumnCountry;

    @FXML
    private TableColumn<Client, String> tableClientColumnLastPaymentDate;

    // This method must :
    // - set TabPanes inside the pane ;
    // - set the TableView content.
    @FXML
    private void initialize() {

        // PANE WITH COUNTRIES
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-tab-view.fxml"));
            Parent parent = fxmlLoader.load();
            mainTabPane.getChildren().clear();
            mainTabPane.getChildren().add(parent);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


        // TABLE VIEW CONTENT
        tableClientsColumnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        tableClientsColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableClientsColumnCountry.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        tableClientColumnLastPaymentDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPayments().stream().sorted().toList().getLast().getDateReceived().toString()));

        List<Client> clients = Data.instance.getSetClients().stream().toList();
        tableClients.setItems(FXCollections.observableList(clients));

    }

    @FXML
    private void openParameters() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("parameters-view.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Paramètres");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent, 1200, 800);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
