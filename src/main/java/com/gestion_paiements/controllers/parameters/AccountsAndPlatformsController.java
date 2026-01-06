package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountsAndPlatformsController {

    @FXML
    private ComboBox<WorkingCountry> comboBoxCountry;

    @FXML
    private ComboBox<Currency> comboBoxCurrency;

    @FXML
    private ComboBox<DestinationType> comboBoxType;

    @FXML
    private Label commentSection;

    @FXML
    private ListView<Destination> listViewAccountsForSelectedCountry;

    @FXML
    private TextField textFieldName;

    @FXML
    private void initialize () {
        comboBoxCurrency.setItems(FXCollections.observableArrayList(Data.instance.getSetCurrencies()));
        comboBoxCurrency.setConverter(new StringConverter<>() {
            @Override
            public String toString(Currency currency) {
                return (currency == null) ? "" : currency.getName();
            }

            @Override
            public Currency fromString(String s) {
                return null;
            }
        });

        comboBoxCountry.setItems(FXCollections.observableArrayList(Data.instance.getMapAccountsCountries().values()));
        comboBoxCountry.setConverter(new StringConverter<>() {
            @Override
            public String toString(WorkingCountry workingCountry) {
                return (workingCountry == null) ? "" : workingCountry.getName();
            }

            @Override
            public WorkingCountry fromString(String s) {
                return null;
            }
        });

        comboBoxType.setItems(FXCollections.observableArrayList(DestinationType.values()));
        comboBoxType.setConverter(new StringConverter<>() {
            @Override
            public String toString(DestinationType destinationType) {
                return (destinationType == null) ? "" : Destination.destinationTypeLabels.get(destinationType);
            }

            @Override
            public DestinationType fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    void countrySelected() {
        WorkingCountry country = comboBoxCountry.getValue();

        listViewAccountsForSelectedCountry.setItems(FXCollections.observableArrayList(country.getAccountsAndPlatforms().values()));

        listViewAccountsForSelectedCountry.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Destination item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    @FXML
    void validateCountry() {

        // Retrieve values
        Currency currency = comboBoxCurrency.getValue();
        WorkingCountry country = comboBoxCountry.getValue();
        DestinationType type = comboBoxType.getValue();
        String name = textFieldName.getText();

        // Check for null
        if (currency == null) {
            commentSection.setText("Veuillez sélectionner une devise.");
            return;
        }

        if (country == null) {
            commentSection.setText("Veuillez sélectionner un pays.");
            return;
        }

        if (type == null) {
            commentSection.setText("Veuillez sélectionner un type.");
            return;
        }

        if (Objects.equals(textFieldName.getText(), "")) {
            commentSection.setText("Veuillez indiquer un nom.");
            return;
        }

        // Check for possible incompatibilities
        Set<String> existingNames = Data.instance.getSetDestinations().stream().map(Destination::getName).collect(Collectors.toSet());
        if (existingNames.contains(name)) {
            commentSection.setText("Ce nom est déjà pris.");
        }

        // Validate
        commentSection.setText("");

        Destination newDestination = new Destination(type, country, currency, name);
        country.getAccountsAndPlatforms().put(newDestination.getName(), newDestination);
        Data.instance.getSetDestinations().add(newDestination);
        // TODO Memory

        RefreshableData.refreshTables();

        countrySelected();
        textFieldName.setText("");

    }

}
