package com.gestion_paiements.controllers.additional_windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ModifyPaymentFromPlatformController {

    @FXML
    private TextArea areaComment;

    @FXML
    private ComboBox<?> boxAccount;

    @FXML
    private ComboBox<?> boxPlatform;

    @FXML
    private TextField fieldCommission;

    @FXML
    private Label labelCurrencyCommission;

    @FXML
    private Label labelError;

    @FXML
    private AnchorPane paneProducts;

    @FXML
    private DatePicker pickerDateReceived;

    @FXML
    private DatePicker pickerDateSent;

    @FXML
    void accountSelected(ActionEvent event) {

    }

    @FXML
    void validate(ActionEvent event) {

    }

}
