package com.gestion_paiements.controllers.elements;

import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.PurchasedProducts;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class SelectablePaymentLineController {

    @FXML
    private CheckBox checkBox;

    public boolean isSelected () {
        return checkBox.isSelected();
    }

    @FXML
    private Label labelAmountSent;

    @FXML
    private Label labelID;

    @FXML
    private Label labelProducts;

    @FXML
    private Label labelSender;

    PaymentFromClient payment;

    public void setPayment(PaymentFromClient payment) {
        this.payment = payment;
    }

    public PaymentFromClient getPayment() {
        return payment;
    }

    @FXML
    private void initialize () {
        checkBox.setSelected(true);
        labelAmountSent.setText(Currencies.fromAmountToString(payment.getReceivedAmount()));
        labelID.setText(String.valueOf(payment.getId()));
        labelProducts.setText(PurchasedProducts.fromListToString(payment.getProducts()));
        labelSender.setText(payment.getSender().getName());
    }

}
