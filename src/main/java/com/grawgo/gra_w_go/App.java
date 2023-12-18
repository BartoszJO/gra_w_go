package com.grawgo.gra_w_go;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.scene.SceneManager;

public class App extends Application {
    public static void main (String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showMenuScene();
    }
}
