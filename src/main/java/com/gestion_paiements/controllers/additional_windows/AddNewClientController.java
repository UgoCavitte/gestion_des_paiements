package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Objects;

public class AddNewClientController {

    @FXML
    private ComboBox<Country> cbCountry;

    @FXML
    private Label labelMessage;

    @FXML
    private TextArea taComment;

    @FXML
    private TextField tfName;

    @FXML
    private void validate() {

        String name = tfName.getText().trim();
        Country country = cbCountry.getValue();
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
        // TODO Write in memory

    }

}
