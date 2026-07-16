package com.alvi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * MainApp.java
 * -----------------------------
 * This is the entry point of the Personal Task Manager (Lite) application.
 * It loads the FXML layout (main_view.fxml) and displays the main window.
 *
 * Responsibilities:
 * - Initialize the JavaFX application.
 * - Load and show the main user interface.
 */

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Personal Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
