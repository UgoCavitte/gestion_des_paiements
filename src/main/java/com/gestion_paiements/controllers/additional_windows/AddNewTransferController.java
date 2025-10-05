package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.Main;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.PurchasedProduct;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

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
        List<PurchasedProduct> list = controller.validate();

        // Controls that the products were set correctly
        for (PurchasedProduct p : list) {
            if (p.getProduct() == null) {
                return;
            }
            if (p.getQuantity() <= 0) {
                return;
            }
        }

    }

}
