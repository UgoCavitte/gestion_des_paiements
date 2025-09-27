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

public class AddNewClientController {

    @FXML
    private ComboBox<String> cbCountry;

    @FXML
    private Label labelMessage;

    @FXML
    private TextArea taComment;

    @FXML
    private TextField tfName;

    @FXML
    private void initialize() {
        cbCountry.setItems(FXCollections.observableList(Data.instance.getMapClientsCountries().values().stream().map(Country::getName).toList()));
        // cbCountry.setItems(FXCollections.observableList(Data.instance.getMapClientsCountries().values().stream().toList()));
    }

    @FXML
    private void validate(ActionEvent event) {

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

        Client client = new Client(name, country, comment);
        Data.instance.getSetClients().add(client);

        RefreshableData.refreshTables();

        // TODO Write in memory

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

}
