package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.Main;
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

public class AddNewTransferController {

    @FXML
    private TextArea areaComment;

    @FXML
    private ComboBox<String> boxAccount;

    @FXML
    private ComboBox<String> boxClient;

    @FXML
    private ComboBox<String> boxCountry;

    @FXML
    private ComboBox<String> cbCurrencySent; // TODO Set this

    @FXML
    private Label labelCurrencyReceived;

    @FXML
    private DatePicker pickerDateReceived;

    @FXML
    private DatePicker pickerDateSent;

    @FXML
    private TextField fieldAmountReceived;

    @FXML
    private TextField fieldAmountSent;

    @FXML
    private AnchorPane paneProducts;

    @FXML
    private Label labelError;

    ProductsAddingController controller;

    @FXML
    private void initialize () {
        // Boxes
        boxClient.setItems(FXCollections.observableList(Data.instance.getSetClients().stream().map(Client::getName).sorted().toList()));
        boxCountry.setItems(FXCollections.observableList(Data.instance.getMapAccountsCountries().keySet().stream().toList()));

        // Adds the "add products" pane
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("additional_windows/products-adding.fxml"));
            controller = new ProductsAddingController();
            loader.setController(controller);
            paneProducts.getChildren().clear();
            paneProducts.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void countrySelected() {
        boxAccount.setItems(FXCollections.observableList(Data.instance.getMapAccountsCountries().get(boxCountry.getValue()).getAccountsAndPlatforms().keySet().stream().toList()));
    }

    @FXML
    void accountSelected () {
        if (boxAccount.getValue() == null) {
            labelCurrencyReceived.setText("");
            return;
        }

        labelCurrencyReceived.setText(Destinations.fromStringToDestination(boxAccount.getValue()).getCurrency().getName());

    }

    @FXML
    void dateSentSelected() {
        if (pickerDateReceived.getValue() == null) pickerDateReceived.setValue(pickerDateSent.getValue());
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

        // Amounts
        if (Objects.equals(fieldAmountSent.getText(), "")) {
            labelError.setText("Aucune somme envoyée indiquée.");
            return;
        }
        if (Objects.equals(fieldAmountReceived.getText(), "")) {
            labelError.setText("Aucune somme reçue indiquée.");
            return;
        }
        if (cbCurrencySent.getValue() == null) {
            labelError.setText("Aucune devise d'envoi sélectionnée.");
            return;
        }
        Amount amountSent = new Amount(
                Double.parseDouble(fieldAmountSent.getText()),
                Currencies.fromStringToCurrency(cbCurrencySent.getValue())
        );
        Amount amountReceived = new Amount(
                Double.parseDouble(fieldAmountReceived.getText()),
                Currencies.fromStringToCurrency(labelCurrencyReceived.getText())
        );

        // Comment
        String comment = areaComment.getText();

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

        PaymentFromClient payment = new PaymentFromClient(
                client,
                destination,
                dateSent,
                dateReceived,
                amountSent,
                amountReceived,
                list,
                comment
        );

        // Writing
        client.getPayments().add(payment);
        destination.getTransfers().add(payment);
        Data.instance.getSetPayments().add(payment);
        // TODO Write to DB

        // Close window
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

}
