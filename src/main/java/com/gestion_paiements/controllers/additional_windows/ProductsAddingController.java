package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Product;
import com.gestion_paiements.types.PurchasedProduct;
import com.gestion_paiements.util.Products;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ProductsAddingController {

    final int elementWidth = 80;

    @FXML
    private HBox hBox;

    private Button addButton;

    private final ArrayList<TextField> fields = new ArrayList<>();

    private final ArrayList<ComboBox<String>> boxes = new ArrayList<>();

    @FXML
    private void initialize() {
        // Sets the first ComboBox
        ComboBox<String> firstBox = new ComboBox<>(FXCollections.observableList(Data.instance.getSetProducts().stream().map(Product::getShortName).toList()));
        firstBox.setOnAction(e -> addButton.setDisable(false)
        );
        firstBox.setPrefWidth(elementWidth);
        boxes.add(firstBox);

        TextField firstField = new TextField("1");
        firstField.setPrefWidth(elementWidth);
        fields.add(firstField);

        VBox boxToAdd = new VBox(firstBox, firstField);
        boxToAdd.setPrefWidth(elementWidth);
        hBox.getChildren().add(boxToAdd);

        // Sets the "add a new product" button
        addButton = new Button("Ajouter");
        addButton.setPrefWidth(elementWidth);
        addButton.setOnAction(event -> addProduct());
        addButton.setDisable(true);
        hBox.getChildren().add(addButton);
    }

    private void addProduct() {

        // List of the remaining available products
        List<String> remainingProducts = Data.instance.getSetProducts()
                        .stream()
                .map(Product::getShortName)
                .filter(p -> !boxes.stream().map(ComboBoxBase::getValue).toList().contains(p))
                .toList();

        // Check if there still are products to possibly add
        if (remainingProducts.isEmpty()) {
            return;
        }

        // Returns if not all products have been set
        if (boxes.stream().map(ComboBoxBase::getValue).toList().contains(null)) return;

        boxes.forEach(box -> {
            if (box.getValue() != null) box.setDisable(true);
        });

        // Adds the next column
        ComboBox<String> cb = new ComboBox<>(FXCollections.observableList(remainingProducts));
        cb.setOnAction(e -> addButton.setDisable(false));
        cb.setPrefWidth(elementWidth);
        boxes.add(cb);

        TextField tf = new TextField("1");
        tf.setPrefWidth(elementWidth);
        fields.add(tf);

        VBox boxToAdd = new VBox(cb, tf);
        boxToAdd.setPrefWidth(elementWidth);
        hBox.getChildren().remove(addButton);
        hBox.getChildren().add(boxToAdd);
        hBox.getChildren().add(addButton);

    }

    public List<PurchasedProduct> validate () {
        ArrayList<PurchasedProduct> list = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            list.add(new PurchasedProduct(
                    Integer.parseInt(fields.get(i).getText()),
                    Products.fromStringToProduct(boxes.get(i).getValue())
            ));
        }

        return list;
    }
}
