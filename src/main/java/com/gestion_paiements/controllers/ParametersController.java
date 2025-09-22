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
        parametersList.getItems().addAll("Pays", "Pays de travail", "Devises", "Produits", "Colonnes des tableaux");

        try {
            views.put("Pays", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("parameters/clients-countries-view.fxml"))));
            views.put("Pays de travail", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("parameters/accounts-countries-view.fxml"))));
            views.put("Devises", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("parameters/currencies-view.fxml"))));
            views.put("Produits", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("parameters/products-view.fxml"))));
            views.put("Colonnes des tableaux", FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("parameters/columns-to-show.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
