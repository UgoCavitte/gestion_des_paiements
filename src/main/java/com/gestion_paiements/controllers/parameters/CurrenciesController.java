package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Objects;
import java.util.stream.Collectors;

public class CurrenciesController {

    @FXML
    private Label commentSection;

    @FXML
    private ListView<String> listViewCurrencies;

    @FXML
    private TextField textFieldNewCurrency;

    // A mutable ObservableList to hold the countries for the ListView
    private ObservableList<String> currencyList;

    @FXML
    private void initialize() {
        currencyList = FXCollections.observableArrayList(SampleData.instance.getSetCurrencies().stream().sorted().collect(Collectors.toList()));
        listViewCurrencies.setItems(currencyList);
    }

    @FXML
    void validateCurrency() {
        String input = textFieldNewCurrency.getText();

        if (Objects.equals(input, "")) {
            commentSection.setText("Veuillez entrer le nom d'une devise");
            return;
        }

        else if (SampleData.instance.getSetCurrencies().contains(input)) {
            commentSection.setText("Devise déjà existante");
            return;
        }

        else {
            SampleData.instance.getSetCurrencies().add(input);
            currencyList.add(input);
            FXCollections.sort(currencyList);
            commentSection.setText("Devise ajoutée");
        }

        // Resets the input
        textFieldNewCurrency.setText("");
    }
}
