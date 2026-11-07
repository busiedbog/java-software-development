package com.busiedbog.exercise_15_6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
        Circle displayedCircle = new Circle(100, Color.WHITE);
        StackPane pane = new StackPane(displayedCircle);
        BackgroundFill backgroundFill = new BackgroundFill(Color.GRAY, new CornerRadii(20), new Insets(20));
        pane.setBackground(new Background(backgroundFill));
        pane.setPadding(new Insets(40));
        pane.setOnMousePressed(
                (evt) -> {
                   displayedCircle.setFill(Color.BLACK);
                }
        );
        pane.setOnMouseReleased(
                (evt) -> {
                   displayedCircle.setFill(Color.WHITE);
                }
        );
        
        var scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Exercise 15.7 (Change color using a mouse)");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}