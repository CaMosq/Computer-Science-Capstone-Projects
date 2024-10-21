/**
 * Algorithms testing application
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description: JavaFX application that implements searching,
 * sorting, inserting algorithms to test the performance
 * with different data structures.
 * */

package com.carmen.algorithmtesting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Algorithm Testing");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setMaximized(true);//full width
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
