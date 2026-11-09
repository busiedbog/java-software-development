package com.busiedbog.exercise_16_17;

import javafx.application.Application;
import javafx.scene.text.TextAlignment;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        
        Label title = new Label("Show Colors");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextFill(Color.GRAY);
        vbox.getChildren().add(title);
        String[] properties = {"Red", "Green", "Blue", "Opacity"};
        
        for (String propertyName: properties) {
            AnchorPane settingPane = new AnchorPane();
            Label propLabel = new Label(propertyName);
            propLabel.setTextAlignment(TextAlignment.RIGHT);
            ScrollBar propScrollBar = new ScrollBar();
            propScrollBar.setMin(0);
            propScrollBar.setMax(1);
            propScrollBar.setValue(.5);
            propScrollBar.setId(propertyName);
            
            propScrollBar.valueProperty().addListener((observable, oldValue, newValue) ->
            {
                    Color titleColor = (Color) title.getTextFill();
                    Color newColor;
                    Double newNumber = newValue.doubleValue();
                    switch (propScrollBar.getId()) {
                        case "Red":
                            newColor = new Color(
                                    newNumber,
                                    titleColor.getGreen(), 
                                    titleColor.getBlue(),
                                    titleColor.getOpacity()
                            );
                            title.setTextFill(newColor);
                            break;
                        case "Green": 
                            newColor = new Color(
                                    titleColor.getRed(),
                                    newNumber, 
                                    titleColor.getBlue(),
                                    titleColor.getOpacity()
                            );
                            title.setTextFill(newColor);
                            break;
                        case "Blue":
                            newColor = new Color(
                                    titleColor.getRed(),
                                    titleColor.getGreen(), 
                                    newNumber,
                                    titleColor.getOpacity()
                            );
                            title.setTextFill(newColor);
                            break;
                        case "Opacity":
                            newColor = new Color(
                                    titleColor.getRed(),
                                    titleColor.getGreen(), 
                                    titleColor.getBlue(),
                                    newNumber
                            );
                            title.setTextFill(newColor);
                            break;
                        
                }
            }
            );
            
            AnchorPane.setLeftAnchor(propLabel, 15.0);
            AnchorPane.setRightAnchor(propScrollBar, 15.0);
            settingPane.getChildren().addAll(propLabel, propScrollBar);

            vbox.getChildren().add(settingPane);
        }
        
        var scene = new Scene(vbox, 250, 125);
        stage.setScene(scene);
        stage.setTitle("Exercise 16.17");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}