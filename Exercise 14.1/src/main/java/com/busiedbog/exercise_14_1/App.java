/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.busiedbog.exercise_14_1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.geometry.Insets;
/**
 *
 * @author corti
 */
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Image gFlag = new Image(getClass().getResourceAsStream("/images/flag1.gif"), 
                400.0d, 400.0d, true, true);
        Image ukFlag = new Image(getClass().getResourceAsStream("/images/flag2.gif"), 
                400.0d, 400.0d, true, true);
        Image fFlag = new Image(getClass().getResourceAsStream("/images/flag6.gif"), 
                400.0d, 400.0d, true, true);
        Image cFlag = new Image(getClass().getResourceAsStream("/images/flag7.gif"), 
                400.0d, 400.0d, true, true);
        
        ImageView gFlagView = new ImageView(gFlag);
        ImageView ukFlagView = new ImageView(ukFlag);
        ImageView fFlagView = new ImageView(fFlag);
        ImageView cFlagView = new ImageView(cFlag);
        
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(8);
        root.setVgap(8);
        root.add(gFlagView, 0, 0);
        root.add(ukFlagView, 1, 0);
        root.add(fFlagView, 0, 1);
        root.add(cFlagView, 1, 1);
        
        Scene scene = new Scene(root, 825, 500);
        
        primaryStage.setTitle("Exercise 14.1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
