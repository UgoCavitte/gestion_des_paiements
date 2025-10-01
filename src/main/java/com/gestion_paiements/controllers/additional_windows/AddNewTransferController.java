package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddNewTransferController {

    @FXML
    private TextArea areaComment;

    @FXML
    private ComboBox<String> boxAccount;

    @FXML
    private ComboBox<String> boxClient;

    @FXML
    private ComboBox<String> boxCountry;

    @FXML
    private DatePicker dateReceived;

    @FXML
    private DatePicker dateSent;

    @FXML
    private TextField fieldAmountReceived;

    @FXML
    private TextField fieldAmountSent;

    @FXML
    private void initialize () {
        // Boxes
        boxClient.setItems(FXCollections.observableList(Data.instance.getSetClients().stream().map(Client::getName).sorted().toList()));
        boxCountry.setItems(FXCollections.observableList(Data.instance.getMapAccountsCountries().keySet().stream().toList()));
    }

    @FXML
    void countrySelected() {

    }

    @FXML
    void dateSentSelected() {
        if (dateReceived.getValue() == null) dateReceived.setValue(dateSent.getValue());
    }

    @FXML
    void validate(ActionEvent event) {

    }

}
