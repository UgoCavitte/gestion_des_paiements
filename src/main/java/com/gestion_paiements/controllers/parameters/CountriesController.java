package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.stream.Collectors;

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

    // A mutable ObservableList to hold the countries for the ListView
    private ObservableList<String> countryList;

    @FXML
    private void initialize() {
        countryList = FXCollections.observableArrayList(SampleData.instance.getSetCountries().stream().sorted().collect(Collectors.toList()));
        listViewCountries.setItems(countryList);
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
            countryList.add(input);
            FXCollections.sort(countryList);
            commentSection.setText("Pays ajouté");
        }

        // Resets the input
        textFieldNewCountry.setText("");
    }
}