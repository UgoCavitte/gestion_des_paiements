package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Objects;

public class ProductsController {

    @FXML
    private Label commentSection;

    @FXML
    private ListView<Product> listViewProducts;

    @FXML
    private TextField textFieldNewProductSN;

    @FXML
    private TextField textFieldNewProductD;

    // A mutable ObservableList to hold the products for the ListView
    private ObservableList<Product> productsList;

    // TODO implement the initialize method

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
