package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.WorkingCountry;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class AccountsAndPlatformsController {

    @FXML
    private ComboBox<WorkingCountry> comboBoxCountry;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private Label commentSection;

    @FXML
    private ListView<Destination> listViewAccountsForSelectedCountry;

    @FXML
    private TextField textFieldName;

    @FXML
    private void initialize () {
        comboBoxCountry.setItems(FXCollections.observableArrayList(Data.instance.getMapAccountsCountries().values()));
        comboBoxCountry.setConverter(new StringConverter<WorkingCountry>() {
            @Override
            public String toString(WorkingCountry workingCountry) {
                return (workingCountry == null) ? "" : workingCountry.getName();
            }

            @Override
            public WorkingCountry fromString(String s) {
                return null;
            }
        });

        comboBoxType.setItems(FXCollections.observableArrayList(Destination.destinationTypeLabels));
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

    }

}
