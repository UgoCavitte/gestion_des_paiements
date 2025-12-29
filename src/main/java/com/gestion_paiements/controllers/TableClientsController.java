package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.controllers.additional_windows.ModifyClientController;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.util.Refreshable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TableClientsController implements Refreshable {

    Client selectedClient;

    @FXML
    private Button buttonDeleteClient;

    @FXML
    private Button buttonModifyClient;

    @FXML
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, Boolean> tableClientsColumnActive;

    @FXML
    private TableColumn<Client, Integer> tableClientsColumnID;

    @FXML
    private TableColumn<Client, String> tableClientsColumnName;

    @FXML
    private TableColumn<Client, String> tableClientsColumnCountry;

    @FXML
    private TableColumn<Client, String> tableClientColumnLastPaymentDate;

    @FXML
    private TableColumn<Client, String> tableClientColumnComment;

    @FXML
    private void initialize() {
        // TABLE VIEW CONTENT
        tableClientsColumnActive.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();

            BooleanProperty property = new SimpleBooleanProperty(client.isActive());

            property.addListener((_, _, isActive) -> {
                client.setActive(isActive);
            });

            // TODO save ?
            return property;
        });
        tableClientsColumnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tableClientsColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableClientsColumnCountry.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry().getName()));
        tableClientColumnLastPaymentDate.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPayments().isEmpty()) return new SimpleStringProperty("");
            return new SimpleStringProperty(cellData.getValue().getPayments().stream().sorted().toList().getLast().getDateReceived().toString());
        });
        tableClientColumnComment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

        tableClientsColumnActive.setCellFactory(CheckBoxTableCell.forTableColumn(tableClientsColumnActive));

        tableClients.setEditable(true);
        tableClientsColumnActive.setEditable(true);

        // SELECTION LISTENER
        tableClients.getSelectionModel().selectedItemProperty().addListener((_, _, newS) -> {
            if (newS == null) {
                buttonDeleteClient.setDisable(true);
                buttonModifyClient.setDisable(true);
                return;
            }

            selectedClient = newS;
            buttonDeleteClient.setDisable(false);
            buttonModifyClient.setDisable(false);

        });

        ObservableList<Client> masterData = FXCollections.observableArrayList(Data.instance.getSetClients().stream().toList());
        SortedList<Client> sortedData = new SortedList<>(masterData);
        sortedData.comparatorProperty().bind(tableClients.comparatorProperty());
        tableClients.setItems(sortedData);

    }

    @FXML
    private void addNewClient () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additional_windows/add-new-client.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un nouveau client");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void modifyClient () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additional_windows/modify-client.fxml"));

            ModifyClientController controller = new ModifyClientController();
            controller.setSelectedClient(selectedClient);
            fxmlLoader.setController(controller);

            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Modifier un client");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // A client must not be deleted if it has any payment on its name
    // This method deletes only clients created by mistake (without any payment)
    @FXML
    private void deleteClient () {
        if (selectedClient.getPayments().isEmpty()) {
            Data.instance.getSetClients().remove(selectedClient);
            refreshElement();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("warnings/cannot-delete-client.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Opération impossible");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refreshElement() {
        ObservableList<Client> masterData = (ObservableList<Client>) ((SortedList<Client>) tableClients.getItems()).getSource();
        masterData.clear();
        masterData.addAll(Data.instance.getSetClients().stream().toList());
    }
}
