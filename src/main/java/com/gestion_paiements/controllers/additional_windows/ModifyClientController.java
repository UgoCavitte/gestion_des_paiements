package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

public class ModifyClientController {

    Client selectedClient;

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    @FXML
    private ComboBox<String> cbCountry;

    @FXML
    private Label labelID;

    @FXML
    private Label labelMessage;

    @FXML
    private TextArea taComment;

    @FXML
    private TextField tfName;

    @FXML
    private void initialize() {

        cbCountry.setItems(FXCollections.observableList(Data.instance.getMapClientsCountries().values().stream()
                .map(Country::getName)
                .sorted()
                .toList()));

        labelID.setText("ID : " + selectedClient.getId());
        tfName.setText(selectedClient.getName());
        taComment.setText(selectedClient.getComment());
        cbCountry.setValue(selectedClient.getCountry().getName());

    }

    @FXML
    void validate(ActionEvent event) {

        String name = tfName.getText().trim();
        Country country = Data.instance.getMapClientsCountries().get(cbCountry.getValue());
        String comment = taComment.getText();

        if (Objects.equals(name, "")) {
            labelMessage.setText("Veuillez insérer un nom");
            return;
        }

        else if (Data.instance.getSetClients().stream().map(Client::getName).toList().contains(name)) {
            labelMessage.setText("Il existe déjà un client avec ce nom");
            return;
        }

        else if (country == null) {
            labelMessage.setText("Veuillez choisir un pays");
            return;
        }

        selectedClient.setName(name);
        selectedClient.setCountry(country);
        selectedClient.setComment(comment);

        RefreshableData.refreshTables();

        // TODO Write in memory

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

}
