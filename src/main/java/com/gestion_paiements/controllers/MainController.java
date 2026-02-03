package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.data.Memory;
import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.data.SampleData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/*
 * This controller controls the main pane.
 * This includes the top menu and the tab container.
 * Since there can be any number of tabs, these are controlled in another controller file.
 * There also is a pane on the right-hand side to show the list of clients. This list can be sorted.
 */

public class MainController {

    @FXML
    private StackPane mainTabPane;

    @FXML
    private StackPane paneClient;

    // This method must :
    // - set TabPanes inside the pane ;
    // - set the TableView content.
    @FXML
    private void initialize() {

        // PANE WITH COUNTRIES
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-tab-view.fxml"));

            MainTabPaneController controller = new MainTabPaneController();
            RefreshableData.getToRefresh().add(controller);
            fxmlLoader.setController(controller);

            Parent parent = fxmlLoader.load();
            mainTabPane.getChildren().clear();
            mainTabPane.getChildren().add(parent);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // PANE WITH CLIENTS
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("table-clients.fxml"));

            TableClientsController controller = new TableClientsController();
            loader.setController(controller);

            paneClient.getChildren().clear();
            paneClient.getChildren().add(loader.load());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void openParameters() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("parameters-view.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Paramètres");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent, 1200, 800);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    private void rechargerDonnees() {
        // TODO
        // Memory.generalRead();
    }

    @FXML
    private void loadSampleData() {
        SampleData.init();
    }

    @FXML
    private void openFileExplorer () {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(Memory.dataPath.toFile());
                } catch (IOException e) {
                    System.err.println("Cannot open directory. " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Directory not existing : " + Memory.dataPath.toString());
                }
            }).start();
        }

        else {
            System.err.println("It is impossible to open File Explorer or equivalent on this system.");
        }
    }

    @FXML
    private void fermerFenetre() {
        // TODO
    }

    @FXML
    private void generalSave () {
        Memory.generalSave();
    }
}
