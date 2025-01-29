package com.example.firstfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Tasks extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("task.fxml"));
        Scene scene = new Scene(loader.load());

        // Load the CSS file
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Tasks Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


