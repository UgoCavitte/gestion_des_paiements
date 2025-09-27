package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

        labelID.setText(String.valueOf(selectedClient.getId()));
        tfName.setText(selectedClient.getName());
        taComment.setText(selectedClient.getComment());
        cbCountry.setValue(selectedClient.getCountry().getName());

    }

    @FXML
    void validate(ActionEvent event) {

    }

}
