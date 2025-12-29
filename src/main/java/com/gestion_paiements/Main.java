package com.gestion_paiements;

import com.gestion_paiements.data.Memory;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Splash
        Stage loadingStage = new Stage();
        loadingStage.initStyle(StageStyle.UNDECORATED);
        ProgressBar progressBar = new ProgressBar(0);
        Label statusLabel = new Label("Initialisation...");

        Label loadingLabel = new Label("Chargement des données...");
        VBox loadingLayout = new VBox(loadingLabel, statusLabel, progressBar);
        loadingLayout.setAlignment(Pos.CENTER);
        loadingLayout.setPrefSize(300, 200);

        loadingStage.setScene(new Scene(loadingLayout));
        loadingStage.show();

        // Loader
        Task<Void> loadTask = getVoidTask(stage, loadingStage, progressBar, statusLabel);

        new Thread(loadTask).start();
    }

    private @NotNull Task<Void> getVoidTask(Stage stage, Stage loadingStage, ProgressBar progressBar, Label statusLabel) {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Chargement de la configuration...");
                Memory.generalRead((progress, message) -> {
                    updateProgress(progress, 1.0);
                    updateMessage(message);
                });
                return null;
            }
        };

        progressBar.progressProperty().bind(loadTask.progressProperty());
        statusLabel.textProperty().bind(loadTask.messageProperty());

        loadTask.setOnSucceeded(e -> {
            loadingStage.close();

            try {
                showMainWindow(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return loadTask;
    }

    private void showMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestion des paiements");
        stage.setScene(scene);
        stage.show();

        PauseTransition delay = new PauseTransition(Duration.millis(100));
        delay.setOnFinished(event -> {
            stage.setMaximized(true);
        });
        delay.play();
    }
}
