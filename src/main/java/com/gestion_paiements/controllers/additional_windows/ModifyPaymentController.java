package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.Main;
import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.*;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.Destinations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ModifyPaymentController {

    private PaymentFromClient selectedPayment;

    public void setSelectedPayment(PaymentFromClient selectedPayment) {
        this.selectedPayment = selectedPayment;
    }

    @FXML
    private TextArea areaComment;

    @FXML
    private ComboBox<String> boxAccount; // TODO

    @FXML
    private ComboBox<String> boxClient;

    @FXML
    private ComboBox<String> boxCountry;

    @FXML
    private ComboBox<String> boxCurrencySent;

    @FXML
    private TextField fieldAmountReceived;

    @FXML
    private TextField fieldAmountSent;

    @FXML
    private Label labelCurrencyReceived;

    @FXML
    private Label labelError;

    @FXML
    private AnchorPane paneProducts;

    @FXML
    private DatePicker pickerDateReceived;

    @FXML
    private DatePicker pickerDateSent;

    ProductsAddingController controller;

    @FXML
    private void initialize () {

        WorkingCountry country = selectedPayment.getDestination().getCountry();

        // Boxes
        boxClient.setItems(FXCollections.observableList(Data.instance.getSetClients().stream().map(Client::getName).sorted().toList()));
        boxCountry.setItems(FXCollections.observableList(Data.instance.getMapAccountsCountries().keySet().stream().sorted().toList()));
        boxAccount.setItems(FXCollections.observableList(country.getAccountsAndPlatforms().keySet().stream().toList()));
        boxCurrencySent.setItems(FXCollections.observableList(Data.instance.getSetCurrencies().stream().map(Currency::getName).sorted().toList()));

        // Adds the "add products" pane
        // TODO send it the products from that payment
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("additional_windows/products-adding.fxml"));
            controller = new ProductsAddingController();
            controller.setInitialList(selectedPayment.getProducts());
            loader.setController(controller);
            paneProducts.getChildren().clear();
            paneProducts.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Setting everything
        areaComment.setText(selectedPayment.getComment());
        boxClient.setValue(selectedPayment.getSender().getName());
        boxCountry.setValue(country.getName());
        boxAccount.setValue(selectedPayment.getDestination().getName());
        boxCurrencySent.setValue(selectedPayment.getSentAmount().getCurrency().getName());
        fieldAmountSent.setText(String.valueOf(selectedPayment.getSentAmount().getAmount()));
        fieldAmountReceived.setText(String.valueOf(selectedPayment.getReceivedAmount().getAmount()));
        labelCurrencyReceived.setText(selectedPayment.getDestination().getCurrency().getName());
        pickerDateSent.setValue(selectedPayment.getDateSent());
        pickerDateReceived.setValue(selectedPayment.getDateReceived());

    }

    @FXML
    void accountSelected(ActionEvent event) {
        if (boxAccount.getValue() == null) {
            labelCurrencyReceived.setText("");
            return;
        }

        labelCurrencyReceived.setText(Destinations.fromStringToDestination(boxAccount.getValue()).getCurrency().getName());
    }

    @FXML
    void countrySelected(ActionEvent event) {
        boxAccount.setItems(FXCollections.observableList(Data.instance.getMapAccountsCountries().get(boxCountry.getValue()).getAccountsAndPlatforms().keySet().stream().toList()));
    }

    @FXML
    void validate(ActionEvent event) {
        labelError.setText("");

        // Client
        Client client = Data.instance.getSetClients().stream().filter(c -> Objects.equals(c.getName(), boxClient.getValue())).findFirst().orElse(null);
        if (client == null) {
            labelError.setText("Il y a un problème avec la sélection du client.");
            return;
        }
        selectedPayment.getSender().getPayments().remove(selectedPayment);
        selectedPayment.setSender(client);
        client.getPayments().add(selectedPayment);

        // Compte destinataire
        if (boxCountry.getValue() == null) {
            labelError.setText("Aucun pays sélectionné.");
            return;
        }
        if (boxAccount.getValue() == null) {
            labelError.setText("Aucun compte sélectionné.");
            return;
        }
        Destination destination = Data.instance.
                getMapAccountsCountries().get(boxCountry.getValue()).
                getAccountsAndPlatforms().get(boxAccount.getValue());
        selectedPayment.getDestination().getTransfers().remove(selectedPayment);
        selectedPayment.setDestination(destination);
        destination.getTransfers().add(selectedPayment);

        // Dates
        LocalDate dateSent = pickerDateSent.getValue();
        LocalDate dateReceived = pickerDateReceived.getValue();
        if (dateSent == null) {
            labelError.setText("Aucune date d'envoi sélectionnée.");
            return;
        }
        if (dateReceived == null) {
            labelError.setText("Aucune date de réception sélectionnée.");
            return;
        }
        selectedPayment.setDateSent(dateSent);
        selectedPayment.setDateReceived(dateReceived);

        // Amounts
        if (Objects.equals(fieldAmountSent.getText(), "")) {
            labelError.setText("Aucune somme envoyée indiquée.");
            return;
        }
        if (Objects.equals(fieldAmountReceived.getText(), "")) {
            labelError.setText("Aucune somme reçue indiquée.");
            return;
        }
        if (boxCurrencySent.getValue() == null) {
            labelError.setText("Aucune devise d'envoi sélectionnée.");
            return;
        }
        Amount amountSent = new Amount(
                Double.parseDouble(fieldAmountSent.getText()),
                Currencies.fromStringToCurrency(boxCurrencySent.getValue())
        );
        Amount amountReceived = new Amount(
                Double.parseDouble(fieldAmountReceived.getText()),
                Currencies.fromStringToCurrency(labelCurrencyReceived.getText())
        );
        selectedPayment.setSentAmount(amountSent);
        selectedPayment.setReceivedAmount(amountReceived);

        // Comment
        String comment = areaComment.getText();
        selectedPayment.setComment(comment);

        // Products
        List<PurchasedProduct> list = controller.validate();
        // Controls that the products were set correctly
        for (PurchasedProduct p : list) {
            if (p.getProduct() == null) {
                labelError.setText("Un produit a mal été sélectionné.");
                return;
            }
            if (p.getQuantity() <= 0) {
                labelError.setText("La quantité d'un produit a mal été entrée.");
                return;
            }
        }
        selectedPayment.setProducts(list);

        // Writing
        // TODO Write to DB

        RefreshableData.refreshTables();

        // Close window
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}
