package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.SampleData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Objects;

public class CountriesController {

    @FXML
    private Button buttonSubmitCountry;

    @FXML
    private ListView<String> listViewCountries;

    @FXML
    private TextField textFieldNewCountry;

    // Used to show error messages
    @FXML
    private TextArea commentSection;

    @FXML
    private void initialize() {
        listViewCountries.setItems(FXCollections.observableList(SampleData.instance.getSetCountries().stream().sorted().toList()));
    }

    // Adds the country to the list if possible, otherwise shows a message
    @FXML
    void validateCountry() {
        String input = textFieldNewCountry.getText();

        if (Objects.equals(input, "")) {
            commentSection.setText("Veuillez entrer le nom d'un pays");
        }

        else if (SampleData.instance.getSetCountries().contains(input)) {
            commentSection.setText("Pays déjà existant");
        }

        else {
            SampleData.instance.getSetCountries().add(input);
            commentSection.setText("Pays ajouté");
            // TODO update the listview to show the result
        }

        // Resets the input
        textFieldNewCountry.setText("");
    }


}