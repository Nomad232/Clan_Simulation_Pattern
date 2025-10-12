package com.game.task2;

import com.game.task2.controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGameApp extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartGameApp.class.getResource("views/start-view.fxml"));
        loader.setController(new StartController());
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        stage.setTitle("Clan Battle Simulation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}