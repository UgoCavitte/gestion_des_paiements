package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.util.Refreshable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TableClientsController implements Refreshable {

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

    @FXML
    private void initialize() {
        // TABLE VIEW CONTENT
        tableClientsColumnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        tableClientsColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableClientsColumnCountry.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry().getName()));
        tableClientColumnLastPaymentDate.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPayments().isEmpty()) return new SimpleStringProperty("");
            return new SimpleStringProperty(cellData.getValue().getPayments().stream().sorted().toList().getLast().getDateReceived().toString());
        });

        List<Client> clients = Data.instance.getSetClients().stream().toList();
        tableClients.setItems(FXCollections.observableList(clients));
    }


    @FXML
    void onSortClients() {
        // TODO
    }

    @FXML
    private void addNewClient () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additional_windows/add-new-client.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un nouveau client");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent
                    //, 1200, 800
                    );
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshElement() {
        tableClients.setItems(FXCollections.observableList(Data.instance.getSetClients().stream().toList()));
        tableClients.refresh();
        System.out.println("Refreshing");
    }
}
