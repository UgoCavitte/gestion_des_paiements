package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Product;
import com.gestion_paiements.util.Products;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProductsAddingController {

    @FXML
    private HBox hBox;

    private Button addButton;

    ArrayList<ComboBox<String>> boxes = new ArrayList<>();

    private Function<Void, Void> callBack;

    ArrayList<Product> chosenProducts = new ArrayList<>();

    public void setCallBack(Function<Void, Void> callBack) {
        this.callBack = callBack;
    }

    @FXML
    private void initialize() {
        // Sets the first ComboBox
        ComboBox<String> firstBox = new ComboBox<>(FXCollections.observableList(Data.instance.getSetProducts().stream().map(Product::getShortName).toList()));
        firstBox.setOnAction(e -> {
            ComboBox<String> source = (ComboBox<String>) e.getSource();
            chosenProducts.add(Products.fromStringToProduct((source.getValue())));
            addButton.setDisable(false);
        });
        boxes.add(firstBox);
        hBox.getChildren().add(firstBox);

        // Sets the "add a new product" button
        addButton = new Button("Ajouter");
        addButton.setOnAction(event -> addProduct());
        addButton.setDisable(true);
        hBox.getChildren().add(addButton);
    }

    private void addProduct() {

        boxes.forEach(box -> {
            if (box.getValue() != null) box.setDisable(true);
        });

        // List of the remaining available products
        List<String> remainingProducts = Data.instance.getSetProducts()
                        .stream().map(Product::getShortName).toList(); // TODO remove already used products

        callBack.apply(null);
    }
}
