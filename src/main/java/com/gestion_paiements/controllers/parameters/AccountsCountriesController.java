package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.Country;
import com.gestion_paiements.types.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Objects;
import java.util.stream.Collectors;

public class AccountsCountriesController {

    @FXML
    private ListView<String> listViewCountries;

    @FXML
    private TextField textFieldNewCountry;

    // Used to show error messages
    @FXML
    private Label commentSection;

    // A mutable ObservableList to hold the countries for the ListView
    private ObservableList<String> countryList;

    @FXML
    private void initialize() {
        countryList = FXCollections.observableArrayList(Data.instance.getSetAccountsCountries().stream().map(Country::getName).sorted().collect(Collectors.toList()));
        listViewCountries.setItems(countryList);
    }

    // Adds the country to the list if possible, otherwise shows a message
    @FXML
    void validateCountry() {
        String input = textFieldNewCountry.getText();

        if (Objects.equals(input, "")) {
            commentSection.setText("Veuillez entrer le nom d'un pays");
            return;
        }

        else if (Data.instance.getSetAccountsCountries().stream().map(Country::getName).collect(Collectors.toSet()).contains(input)) {
            commentSection.setText("Pays déjà existant");
            return;
        }

        else {
            Data.instance.getSetAccountsCountries().add(new Country(input));
            countryList.add(input);
            FXCollections.sort(countryList);
            commentSection.setText("Pays ajouté");
        }

        // Resets the input
        textFieldNewCountry.setText("");
    }
}