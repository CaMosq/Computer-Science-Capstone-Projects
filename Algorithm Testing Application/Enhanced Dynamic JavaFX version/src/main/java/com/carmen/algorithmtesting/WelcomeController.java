/**
 * Algorithms testing application
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description: JavaFX application that implements searching,
 * sorting, inserting algorithms to test the performance
 * with different data structures.
 * */
package com.carmen.algorithmtesting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
   @FXML private VBox welcomeBox;
    @FXML private Text welcomeText;
   @FXML private Label welcomeMessage;
   @FXML private Button startButton;



   @FXML private void initialize (){

       //style welcome headline text
       welcomeText.setStyle("-fx-font-size: 42px; -fx-font-family: 'Arial Black'");

       //set welcome message
       welcomeMessage.setText("This application allows you to explore and compare different algorithms in real-time. " +
               "You can test various sorting and searching algorithms on custom datasets, " +
               "visualize how they work step by step, and analyze their performance in" +
               " terms of runtime and memory usage.\n" +
               "\n" +
               "Key Features:\n" +
               "\n" +
               "\t•\tVisualize how algorithms process data dynamically.\n" +
               "\t•\tCompare sorting algorithms like Insertion Sort, Selection Sort, and Quick Sort.\n" +
               "\t•\tMeasure the performance of different searching techniques such as Binary Search and TreeMap Search.\n" +
               "\t•\tView detailed results, including execution time, memory usage, and complexity analysis.\n" +
               "\n" +
               "Whether you’re a computer science student or a seasoned developer, this tool will help you understand algorithm behavior in a clear and interactive way.\n" +
               "\n" +
               "Start testing now and enhance your understanding of data structures and algorithms!");

       welcomeMessage.setStyle("-fx-font-size: 18px");
       welcomeMessage.setWrapText(true);

   }

   //method to navigate to main screen
    @FXML private void goToMainScreen(ActionEvent event) throws IOException {

        int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();

        FXMLLoader fxmlLoader = new FXMLLoader (MainApplication.class.getResource ("main-view.fxml"));
        Scene scene = new Scene (fxmlLoader.load (), width, height);
        Stage stage = (Stage) ((Node)event.getSource ()).getScene ().getWindow ();
        stage.setScene (scene);
        stage.setMaximized (true);
        stage.show();

    }













}
