package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.PurchasedProduct;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
    private DatePicker dateReceived;

    @FXML
    private DatePicker dateSent;

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
    void dateSentSelected() {
        if (dateReceived.getValue() == null) dateReceived.setValue(dateSent.getValue());
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

    }

}
