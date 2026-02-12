package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Destination;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SynthesisController {

    @FXML
    private ComboBox<Destination> comboBoxAccount;

    @FXML
    private ComboBox<Destination> comboBoxCountry;

    @FXML
    private Label labelAverage;

    @FXML
    private Label labelCount;

    @FXML
    private Label labelTotal;

    @FXML
    private AnchorPane panePayments;

    @FXML
    private DatePicker pickerDateBeginning;

    @FXML
    private DatePicker pickerDateEnd;

    @FXML
    void accountSelected() {

    }

    @FXML
    void closeWindow() {

    }

    @FXML
    void countrySelected() {

    }

    @FXML
    void dateBeginningPicked() {

    }

    @FXML
    void dateEndPicked() {

    }

    @FXML
    void exportToPDF() {

    }


}
