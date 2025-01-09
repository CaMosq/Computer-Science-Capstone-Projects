/**
 * Global UI
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class implements all the UI designs and components that are shared across
 * the application. such as buttons, algorithm boxes, labels, popups...
 */
package com.carmen.algorithmtesting.FXControllers;

import com.carmen.algorithmtesting.Course;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GlobalUI {

    //create label for title
    private Text title;

    //create labels for the column headers
    private Label algorithmName;
    private Label runtimeName;
    private Label memoryUsage;
    private Label complexity;
    private Label explanation;

    // create labels for the first column
    private Label algorithm1;
    private Label algorithm2;
    private Label algorithm3;
    private Label algorithm4;

    //create labels for the second column (runtime)
    private Label runtime1;
    private Label runtime2;
    private Label runtime3;
    private Label runtime4;

    //create labels for the third column ( memory usage)
    private Label memory1;
    private Label memory2;
    private Label memory3;
    private Label memory4;

    //create labels for the fourth column ( complexity)
    private Label complexity1;
    private Label complexity2;
    private Label complexity3;
    private Label complexity4;

    //create labels for the fifth column ( explanation)
    private Label summary1;
    private Label summary2;
    private Label summary3;
    private Label summary4;

    private Label explanationR1;
    private Label explanationR2;
    private Label explanationR3;
    private Label explanationR4;

    @FXML private GridPane gridPane;
    private FlowPane resultBoxesHolder;


    public GlobalUI() {
        //create label for title
        title = new Text("Results Summary");
        title.setStyle("-fx-font-size: 42px; -fx-font-family: 'Arial Black'");


        // create labels for the names
        algorithm1 = new Label();
        algorithm2 = new Label();
        algorithm3 = new Label();
        algorithm4 = new Label();

        //create labels for the second column (runtime)
        runtime1 = new Label();
        runtime2 = new Label();
        runtime3 = new Label();
        runtime4 = new Label();

        //create labels for the third column ( memory usage)
        memory1 = new Label();
        memory2 = new Label();
        memory3 = new Label();
        memory4 = new Label();

        //create labels for the fourth column ( complexity)
        complexity1 = new Label();
        complexity2 = new Label();
        complexity3 = new Label();
        complexity4 = new Label();

        summary1 = new Label();
        summary2 = new Label();
        summary3 = new Label();
        summary4 = new Label();
        explanationR1 = new Label();
        explanationR2 = new Label();
        explanationR3 = new Label();
        explanationR4 = new Label();

        //create images
        ImageView image1 = createImage("/assets/linear.png");
        ImageView image2 = createImage("/assets/binary.png");
        ImageView image3 = createImage("/assets/hash.png");
        ImageView image4 = createImage("/assets/tree.png");

        //create stackPanes for the images
        StackPane imagePane1 = new StackPane(image1);
        StackPane imagePane2 = new StackPane(image2);
        StackPane imagePane3 = new StackPane(image3);
        StackPane imagePane4 = new StackPane(image4);

        //create result boxes
        VBox box1 = createResultsBox(imagePane1, algorithm1, runtime1, memory1, complexity1, new Label("Observation"), summary1, new Label("Explanation"), explanationR1);
        VBox box2 = createResultsBox(imagePane2, algorithm2, runtime2, memory2, complexity2, new Label("Observation"), summary2, new Label("Explanation"), explanationR2);
        VBox box3 = createResultsBox(imagePane3, algorithm3, runtime3, memory3, complexity3, new Label("Observation"), summary3, new Label("Explanation"), explanationR3);
        VBox box4 = createResultsBox(imagePane4, algorithm4, runtime4, memory4, complexity4, new Label("Observation"), summary4, new Label("Explanation"), explanationR4);
        //VBox box5 = createResultsBox(imagePane5, algorithm5, runtime5, memory5, complexity5, summary5);
        //VBox box6 = createResultsBox(imagePane6, algorithm6, runtime6, memory6, complexity6, summary6);
        //VBox box7 = createResultsBox(imagePane7, algorithm7, runtime7, memory7, complexity7, summary7);
        //VBox box8 = createResultsBox(imagePane8, algorithm8, runtime8, memory8, complexity8, summary8);

        //flow pane to hold the boxes
        resultBoxesHolder = new FlowPane(box1, box2, box3, box4);
        resultBoxesHolder.setHgap(15);
        resultBoxesHolder.setVgap(15);
        resultBoxesHolder.setAlignment(Pos.CENTER);

        //remove 4th box if not merge sort algorithm. delete once implemented.
        if(getAlgorithm4().getText().isEmpty()){
            resultBoxesHolder.getChildren().remove(box4);
        }

    }

   //create labels
    public Label createLabels(String labelName){
        return new Label(labelName);
    }

    //create Button
    public Button createButton(String buttonName){
        final String currentStyle = "-fx-background-color:#ffffff;" + "-fx-pref-width: 100px;";
        Button button = new Button(buttonName);
        button.hoverProperty ().addListener (createHoverListener (button, currentStyle));

        return button;
    }

    //create algorithm box
    public VBox createAlgorithmBox(String name, List<Course> list, List<Rectangle> rectangleBars, List<Label> labelList){

        //create the main box of the algorithm
        VBox algorithmBox = new VBox();
        algorithmBox.setSpacing(15);
        algorithmBox.getStyleClass().add("algorithmBox");

        //create title
        Label title = createLabels(name);

        //create the list to hold course names
        List<String> names = new ArrayList<>();

        //create a list of numbers
        List<Integer> number = new ArrayList<>();

        //populate the list of shapes dynamically
        for (Course course : list) {

            //rectangles ( bars)
            rectangleBars.add(new Rectangle(0, 0, 20, 60));

            //labels
            labelList.add(new Label(""));

            //names
            names.add(course.getCourseName());

            //numbers


        }

        //create box to hold the bars
        HBox barsBox = new HBox();
        barsBox.setSpacing(10);
        barsBox.getStyleClass().add("barsBox");

//        //temporary algorithm box
//        Pane rectsPane = new Pane();
//        rectsPane.getStyleClass().add("barsBox");
//
//        for(Rectangle rec: rectangleBars) {
//            rectsPane.getChildren().add(rec);
//        }


        //add shapes to pane
        barsBox.getChildren().addAll(rectangleBars);


        //box to hold the course names vertically
        VBox namesBox = new VBox();
        namesBox.getStyleClass().add("namesBox");
        namesBox.getChildren().addAll(labelList);

        //box to hold bars and the names box
        VBox barsAndNamesBox = new VBox();
        barsAndNamesBox.setSpacing(0);
        barsAndNamesBox.getChildren().addAll(barsBox, namesBox);

        //add everything to main box (algorithm )
        algorithmBox.getChildren().addAll(title, barsAndNamesBox);

        return  algorithmBox;

    }


    // Methods to update algorithm 1 results
    public void setAlgorithm1Results(String name, String complexity, String summary, String explanation) {
        algorithm1.setText(name);
        complexity1.setText(complexity);
        summary1.setText(summary);
        explanationR1.setText(explanation);

    }
    // Methods to update algorithm 2 results
    public void setAlgorithm2Results(String name, String complexity, String summary, String explanation) {
        algorithm2.setText(name);
        complexity2.setText(complexity);
        summary2.setText(summary);
        explanationR2.setText(explanation);
    }
    // Methods to update algorithm 3 results
    public void setAlgorithm3Results(String name, String complexity, String summary, String explanation) {
        algorithm3.setText(name);
        complexity3.setText(complexity);
        summary3.setText(summary);
        explanationR3.setText(explanation);
    }
    // Methods to update algorithm 4 results
    public void setAlgorithm4Results(String name, String complexity, String summary, String explanation) {
        algorithm4.setText(name);
        complexity4.setText(complexity);
        summary4.setText(summary);
        explanationR4.setText(explanation);
    }

    // This method will be called to refresh the grid after all algorithms finish
    public void updateGridPane() {
        //no need  it for now
    }

    //method to create the algorithms result box
    public VBox createResultsBox(StackPane imagePane, Label nameR, Label runtimeR, Label memoryR, Label complexityR, Label summaryTitle, Label summaryR, Label explanationTitle, Label explanation){

        //create result box
        VBox resultBox = new VBox();
        resultBox.setSpacing(30);
        resultBox.setPrefWidth(300);
        resultBox.getStyleClass().add("result-box");

        //box for the centered algorithm name
        HBox nameBox = new HBox(nameR);
        nameBox.setAlignment(Pos.CENTER);

        //observation box
        VBox vBox4 = new VBox(summaryTitle, summaryR);
        vBox4.setSpacing(5);

        //explanation box
        VBox vBox5 = new VBox(explanationTitle, explanation);
        vBox5.setSpacing(5);

        nameR.getStyleClass().add("subtitle-text");
        summaryTitle.getStyleClass().add("bold-text");
        explanationTitle.getStyleClass().add("bold-text");

        summaryR.setWrapText(true);
        explanation.setWrapText(true);

        //add everything to box
        resultBox.getChildren().addAll(imagePane, nameBox, runtimeR, memoryR, complexityR, vBox4,  vBox5);


        return resultBox;
    }

    //Method to create an image view
    private ImageView createImage(String imagePath){
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    //method to create a lister for hovering butttons
    public ChangeListener<Boolean> createHoverListener(final Button button, String oldStyle){
        return ((observableValue, wasHovered, isHovered) -> {
            final String style = "-fx-background-color:#ff9d00;" + "-fx-pref-width: 110px;";
            if(isHovered){
                button.setStyle (style);
            }
            else {
                button.setStyle(oldStyle);
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////// GETTERS /////////////////////////////////////


    public FlowPane getResultBoxesHolder() {
        return resultBoxesHolder;
    }

    public GridPane getGridPane() {
        return gridPane;
    }


    public Text getTitle() {
        return title;
    }


    public Label getAlgorithm1() {
        return algorithm1;
    }

    public Label getAlgorithm2() {
        return algorithm2;
    }

    public Label getAlgorithm3() {
        return algorithm3;
    }

    public Label getAlgorithm4() {
        return algorithm4;
    }

    public Label getRuntime1() {
        return runtime1;
    }

    public Label getRuntime2() {
        return runtime2;
    }

    public Label getRuntime3() {
        return runtime3;
    }

    public Label getRuntime4() {
        return runtime4;
    }

    public Label getMemory1() {
        return memory1;
    }

    public Label getMemory2() {
        return memory2;
    }

    public Label getMemory3() {
        return memory3;
    }

    public Label getMemory4() {
        return memory4;
    }

    public Label getComplexity1() {
        return complexity1;
    }

    public Label getComplexity2() {
        return complexity2;
    }

    public Label getComplexity3() {
        return complexity3;
    }

    public Label getComplexity4() {
        return complexity4;
    }

    public Label getSummary1() {
        return summary1;
    }

    public Label getSummary2() {
        return summary2;
    }

    public Label getSummary3() {
        return summary3;
    }

    public Label getSummary4() {
        return summary4;
    }

    public Label getExplanation() {
        return explanation;
    }

    public Label getExplanationR1() {
        return explanationR1;
    }

    public Label getExplanationR2() {
        return explanationR2;
    }

    public Label getExplanationR3() {
        return explanationR3;
    }

    public Label getExplanationR4() {
        return explanationR4;
    }


    //
//    //////////////////////////////////////////////////////////////////////////////////////
//    /////////////////////////////////////// SETTERS /////////////////////////////////////


    public void setTitle(Text title) {
        this.title = title;
    }

    public void setExplanation(Label explanation) {
        this.explanation = explanation;
    }

    public void setAlgorithm1(Label algorithm1) {
        this.algorithm1 = algorithm1;
    }

    public void setAlgorithm2(Label algorithm2) {
        this.algorithm2 = algorithm2;
    }

    public void setAlgorithm3(Label algorithm3) {
        this.algorithm3 = algorithm3;
    }

    public void setAlgorithm4(Label algorithm4) {
        this.algorithm4 = algorithm4;
    }

    public void setRuntime1(Label runtime1) {
        this.runtime1 = runtime1;
    }

    public void setRuntime2(Label runtime2) {
        this.runtime2 = runtime2;
    }

    public void setRuntime3(Label runtime3) {
        this.runtime3 = runtime3;
    }

    public void setRuntime4(Label runtime4) {
        this.runtime4 = runtime4;
    }

    public void setMemory1(Label memory1) {
        this.memory1 = memory1;
    }

    public void setMemory2(Label memory2) {
        this.memory2 = memory2;
    }

    public void setMemory3(Label memory3) {
        this.memory3 = memory3;
    }

    public void setMemory4(Label memory4) {
        this.memory4 = memory4;
    }

    public void setComplexity1(Label complexity1) {
        this.complexity1 = complexity1;
    }

    public void setComplexity2(Label complexity2) {
        this.complexity2 = complexity2;
    }

    public void setComplexity3(Label complexity3) {
        this.complexity3 = complexity3;
    }

    public void setComplexity4(Label complexity4) {
        this.complexity4 = complexity4;
    }

    public void setSummary1(Label summary1) {
        this.summary1 = summary1;
    }

    public void setSummary2(Label summary2) {
        this.summary2 = summary2;
    }

    public void setSummary3(Label summary3) {
        this.summary3 = summary3;
    }

    public void setSummary4(Label summary4) {
        this.summary4 = summary4;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void setResultBoxesHolder(FlowPane resultBoxesHolder) {
        this.resultBoxesHolder = resultBoxesHolder;
    }

    public void setExplanationR1(Label explanationR1) {
        this.explanationR1 = explanationR1;
    }

    public void setExplanationR2(Label explanationR2) {
        this.explanationR2 = explanationR2;
    }

    public void setExplanationR3(Label explanationR3) {
        this.explanationR3 = explanationR3;
    }

    public void setExplanationR4(Label explanationR4) {
        this.explanationR4 = explanationR4;
    }

}
