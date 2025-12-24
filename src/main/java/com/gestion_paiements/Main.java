package com.gestion_paiements;

import com.gestion_paiements.data.Memory;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        loadingStage.initStyle(StageStyle.UNDECORATED); // Optional: removes borders/buttons

        Label loadingLabel = new Label("Chargement des données...");
        VBox loadingLayout = new VBox(loadingLabel);
        loadingLayout.setAlignment(Pos.CENTER);
        loadingLayout.setPrefSize(300, 200);

        loadingStage.setScene(new Scene(loadingLayout));
        loadingStage.show();

        // Loader
        Task<Void> loadTask = getVoidTask(stage, loadingStage);

        new Thread(loadTask).start();
    }

    private @NotNull Task<Void> getVoidTask(Stage stage, Stage loadingStage) {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // YOUR DATA LOADING LOGIC HERE
                Memory.generalRead();
                // Thread.sleep(2000); // Simulated delay
                return null;
            }
        };

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
