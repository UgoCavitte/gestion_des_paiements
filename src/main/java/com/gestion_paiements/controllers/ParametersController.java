package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParametersController {

    @FXML
    private StackPane contentStackPane;

    @FXML
    private ListView<String> parametersList;

    // A map to store the loaded views to avoid reloading them
    private final Map<String, Parent> views = new HashMap<>();

    @FXML
    private void initialize() {
        parametersList.getItems().addAll("Countries", "Currencies", "Products");

        try {
            views.put("Countries", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("clients-countries-view.fxml"))));
            views.put("Currencies", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("currencies-view.fxml"))));
            views.put("Products", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("products-view.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        parametersList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Parent viewToShow = views.get(newValue);
                if (viewToShow != null) {
                    contentStackPane.getChildren().clear();
                    contentStackPane.getChildren().add(viewToShow);
                }
            }
        });

        parametersList.getSelectionModel().selectFirst();
    }

}
