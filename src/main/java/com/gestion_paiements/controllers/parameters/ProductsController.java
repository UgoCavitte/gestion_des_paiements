package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Objects;
import java.util.stream.Collectors;

public class ProductsController {

    @FXML
    private Label commentSection;

    @FXML
    private TextField textFieldNewProductSN;

    @FXML
    private TextField textFieldNewProductD;

    //////////////////////////////
    /// TABLEAU PRODUITS
    //////////////////////////////

    @FXML
    private TableView<Product> tableViewProducts;

    @FXML
    private TableColumn<Product, String> columnSN;

    @FXML
    private TableColumn<Product, String> columnD;



    // A mutable ObservableList to hold the products for the ListView
    private ObservableList<Product> productsList;

    // TODO implement the initialize method
    @FXML
    private void initialize() {

        columnSN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShortName()));
        columnD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));


        productsList = FXCollections.observableArrayList(Data.instance.getSetProducts().stream().sorted().collect(Collectors.toList()));
        tableViewProducts.setItems(productsList);
    }

    // Adds the country to the list if possible, otherwise shows a message
    @FXML
    void validateProduct() {
        String inputSN = textFieldNewProductSN.getText();
        String inputD = textFieldNewProductD.getText();

        if (Objects.equals(inputSN + inputD, "")) {
            commentSection.setText("Veuillez entrer le nom et la description d'un produit");
            return;
        }

        else if (Objects.equals(inputSN, "")) {
            commentSection.setText("Veuillez entrer le nom d'un produit");
            return;
        }

        else if (Objects.equals(inputD, "")) {
            commentSection.setText("Veuillez entrer la description du produit");
            return;
        }

        else if (Data.instance.getSetProducts().stream().map(Product::getShortName).toList().contains(inputSN)) {
            commentSection.setText("Produit déjà existant");
            return;
        }

        else {
            Product newProduct = new Product(inputSN, inputD);

            Data.instance.getSetProducts().add(newProduct);
            productsList.add(newProduct);
            FXCollections.sort(productsList);
            commentSection.setText("Produit ajouté");
        }

        // Resets the inputs
        textFieldNewProductSN.setText("");
        textFieldNewProductD.setText("");
    }


}
