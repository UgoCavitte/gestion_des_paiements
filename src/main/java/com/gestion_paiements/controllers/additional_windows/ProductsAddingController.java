package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.function.Function;

public class ProductsAddingController {

    @FXML
    private HBox hBox;

    private Button addButton;

    ArrayList<ComboBox<String>> boxes = new ArrayList<>();

    private Function<Void, Void> callBack;

    public void setCallBack(Function<Void, Void> callBack) {
        this.callBack = callBack;
    }

    @FXML
    private void initialize() {
        // Sets the first ComboBox
        ComboBox<String> firstBox = new ComboBox<>(FXCollections.observableList(Data.instance.getSetProducts().stream().map(Product::getShortName).toList()));
        hBox.getChildren().add(firstBox);

        // Sets the "add a new product" button
        addButton = new Button("Ajouter");
        addButton.setOnAction(event -> callBack.apply(null));
        hBox.getChildren().add(addButton);
    }
}
