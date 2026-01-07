package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.DestinationType;
import com.gestion_paiements.types.payments.PaymentFromPlatform;
import com.gestion_paiements.util.Dates;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ModifyPaymentFromPlatformController {

    PaymentFromPlatform payment;

    public void setPayment(PaymentFromPlatform payment) {
        this.payment = payment;
    }

    @FXML
    private TextArea areaComment;

    @FXML
    private ComboBox<String> boxAccount;

    @FXML
    private ComboBox<String> boxPlatform;

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
    private void initialize () {

        // Set the boxes
        boxPlatform.setItems(FXCollections.observableList(
                        payment.getDestination()
                                .getCountry()
                                .getAccountsAndPlatforms()
                                .values().stream()
                                .filter(p -> p.getDestinationType() == DestinationType.platform)
                                .map(Destination::getName)
                                .toList())
        );
        boxPlatform.setValue(payment.getSender().getName());

        boxAccount.setItems(FXCollections.observableList(
                payment.getDestination()
                        .getCountry()
                        .getAccountsAndPlatforms()
                        .values().stream()
                        .filter(p -> p.getDestinationType() == DestinationType.bankAccount)
                        .map(Destination::getName)
                        .toList())
        );
        boxAccount.setValue(payment.getDestination().getName());

        // Date format
        pickerDateSent.setConverter(Dates.dateStringConverter);
        pickerDateReceived.setConverter(Dates.dateStringConverter);

        // Set the other values
        areaComment.setText(payment.getComment());
        fieldCommission.setText(String.valueOf(payment.getCommission().getAmount()));
        labelCurrencyCommission.setText(payment.getCommission().getCurrency().getName());
        pickerDateReceived.setValue(payment.getDateReceived());
        pickerDateSent.setValue(payment.getDateSent());

    }

    @FXML
    void accountSelected(ActionEvent event) {
        // TODO
    }

    @FXML
    void validate(ActionEvent event) {
        // TODO
    }

}
