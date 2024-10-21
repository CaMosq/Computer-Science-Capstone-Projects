/**
 * Main controller
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description: This class is the main controller for the application
 * It implements the UI structure of the Main screen of the application
 */
package com.carmen.algorithmtesting.FXControllers;

import com.carmen.algorithmtesting.AlgorithmsHandler;
import com.carmen.algorithmtesting.Course;
import com.carmen.algorithmtesting.DataHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

public class MainController {

    ////////////////////////////////////// Data variables
    //create data structures to hold the incoming data
    private ArrayList<Course> courseDataArrayList = new ArrayList<>();
    private LinkedList<Course> courseDataLinkedList = new LinkedList<>();
    private HashMap<String, Course> courseDataHashMap = new HashMap<>();
    private TreeMap<String, Course> courseDataTreeMap = new TreeMap<>();
    private List<Course> sortedCoursesList;

    ///////////////////////////////////// Editable instances
    //create instances of each data structure to manipulate
    private ArrayList<Course> copyDataArrayList;
    private LinkedList<Course> copyDataLinkedList = new LinkedList<>(courseDataLinkedList);
    private HashMap<String, Course> copyDataHashMap = new HashMap<>(courseDataHashMap);
    private TreeMap<String, Course> copyDataTreeMap = new TreeMap<>(courseDataTreeMap);

    //////////////////////////////////////create object of the
    // dataHandler, algorithmHandler, and globalUI classes
    private final DataHandler dataHandler;
    private final AlgorithmsHandler algorithmsHandler;
    private GlobalUI globalUI;
    private int completedAlgorithms = 0; // To track how many algorithms have finished

    //labels, textArea and buttons
    private TextArea textArea = new TextArea();
    @FXML private Text topAlertText;
    private Button buttonArrayList;
    private Button buttonLinkedList;
    private Button buttonHashMap;
    private Button buttonTreeMap;

    //create containers
    @FXML private HBox topBarContainer;
    @FXML private HBox innerTopBarContainer;
    private HBox resultTitleBox;
    private FlowPane resultPane;
    @FXML private VBox centerSection;


    //search bar field, button, and info text
    @FXML
    private TextField courseInputField;
    @FXML
    private Button searchButton;
    @FXML
    private Label infoText;
    @FXML
    private Label validCoursesTitle;

    //data structure selection buttons
    @FXML
    private Button arrayListButton;
    @FXML
    private Button linkedListButton;
    @FXML
    private Button hashMapButton;
    @FXML
    private Button treeMapButton;



    //constructor
    public MainController() {
        algorithmsHandler = new AlgorithmsHandler(this);
        dataHandler = new DataHandler();
        globalUI = new GlobalUI();
    }

    //initialization
    @FXML
    private void initialize() {

        //path to the file that contains the data
        String filePath = "computer-science-course-list.txt";

        //Load the data into the data structures
        courseDataArrayList = dataHandler.loadDataInArrayList(filePath); //arrayList
        courseDataLinkedList = dataHandler.loadDataInLinkedList(filePath);
        courseDataHashMap = dataHandler.loadDataInHashMap(filePath);
        courseDataTreeMap = dataHandler.loadDataInTreeMap(filePath);

        //initialize copies
        copyDataArrayList = new ArrayList<>(courseDataArrayList);

        //sort the list for searching algorithms
        sortedCoursesList = algorithmsHandler.quickSort(copyDataArrayList);

        //display choice box at app start
        createChoiceBox(sortedCoursesList);

        //hide alert text until trigger
        topAlertText.setVisible(false);
        topAlertText.setManaged(false);

    }


    /**
     * This method creates a choice box with the available algorithms
     * to allow user to choose which algorithm they want to test
     * return the created choice box to get its selected value
     */
    private void createChoiceBox(List<Course> list) {

        //create box
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        //temp data
        ArrayList<String> algorithms = new ArrayList<>();
        algorithms.add("Searching Algorithms");
        algorithms.add("Sorting Algorithms");

        //add data to the box
        for (String algorithm : algorithms) {
            choiceBox.setValue("Select Algorithm");
            choiceBox.getItems().add(algorithm);
        }

        //style the box
        choiceBox.setMinWidth(200);

        //implement conditions per each choice box selection
        choiceBox.setOnAction(actionEvent -> {


            //if searching algorithm, Show search bar with its elements
            if (choiceBox.getValue().equalsIgnoreCase("searching algorithms")) {

                //clean text area
                centerSection.getChildren().clear();

                //call method to implement search bar
                showSearchBox(list);



            } else if (choiceBox.getValue().equalsIgnoreCase("sorting algorithms")) {

                //clean text area
                centerSection.getChildren().clear();

                //clean search box holder container
                innerTopBarContainer.getChildren().clear();


                //call function to implement sorting
                showDataStructureButtons();

            } else {

                //clean text area
                centerSection.getChildren().clear();

                //clean search box holder container
                innerTopBarContainer.getChildren().clear();
            }
        });

        //add choice box to pane
        topBarContainer.getChildren().addAll(choiceBox);
    }

    /**
     * This function creates a search Box to allow users to
     * select a course number from the provided list
     * to test the searching algorithms
     */
    private void showSearchBox(List<Course>list) {
        //clean the holder container first
        innerTopBarContainer.getChildren().clear();

        //create the search box
        ChoiceBox<String> searchBox = new ChoiceBox<>();


        //add values by iterating the list
        for (Course course : list) {
            searchBox.setValue("Select a Course to Search");
            searchBox.getItems().add(course.getCourseNumber());
        }

        //create a search Button next to it
        Button searchButton = globalUI.createButton("Search");
        //searchButton.getStyleClass().add("search-button");


        //add click event to the button
        searchButton.setOnAction(event -> {
            //call function to search the element
            if (!searchBox.getValue().equalsIgnoreCase("select a course")) {

                searchCourseByCourseNumber(searchBox.getValue(), list);
            }
        });

        //add to pane
        innerTopBarContainer.getChildren().addAll(searchBox, searchButton);


    }


    /**
     * Search course by number.
     * This function implements the UI and the logic to search a course
     * by its number. this function calls the search algorithms
     * @param courseNumber: a string with the target course number
     * @param courseList:   a list of courses.
     */
    private void searchCourseByCourseNumber(String courseNumber, List<Course> courseList) {

        //algorithm box 1 elements
        List<Label> searchingBox1Label = new ArrayList<>();
        searchingBox1Label.addFirst(globalUI.createLabels("Searching...\n\n"));
        List<Rectangle> barsListBox1 = new ArrayList<>();
        VBox algorithmBox1 = globalUI.createAlgorithmBox("Lineal Search (Ordered list)", courseList, barsListBox1, searchingBox1Label);

        //algorithm box 2 elements
        List<Label> searchingBox2Label = new ArrayList<>();
        searchingBox2Label.addFirst(globalUI.createLabels("Searching...\n\n"));
        List<Rectangle> barsListBox2 = new ArrayList<>();
        VBox algorithmBox2 = globalUI.createAlgorithmBox("Binary Search (Ordered list)", courseList, barsListBox2, searchingBox2Label);


        //algorithm 3 elements
        List<Label> searchingBox3Label = new ArrayList<>();
        searchingBox3Label.addFirst(globalUI.createLabels("Searching...\n\n"));
        List<Rectangle> barsListBox3 = new ArrayList<>();
        VBox algorithmBox3 = globalUI.createAlgorithmBox("HashMap Search (Hash Map)", courseList, barsListBox3, searchingBox3Label);


        //algorithm 4 elements
        List<Label> searchingBox4Label = new ArrayList<>();
        searchingBox4Label.addFirst(globalUI.createLabels("Searching...\n\n"));
        List<Rectangle> barsListBox4 = new ArrayList<>();
        VBox algorithmBox4 = globalUI.createAlgorithmBox("TreeMap Search (Tree Map)", courseList, barsListBox4, searchingBox4Label);


        //create floe pane to hold the boxes
        FlowPane algorithmsHolderBox = new FlowPane(algorithmBox1, algorithmBox2, algorithmBox3, algorithmBox4);
        algorithmsHolderBox.setPrefWrapLength(1200);
        algorithmsHolderBox.setHgap(15);
        algorithmsHolderBox.setVgap(15);
        algorithmsHolderBox.setAlignment(Pos.CENTER);


        //add to main pane
        centerSection.getChildren().clear();
        centerSection.getChildren().addAll(algorithmsHolderBox);

        /*
         * call show result method: This method is called here because
         * I want to populate the runtime and memory values before calling them below.
         * The showResult() method calls the functions that set runtime and memory values, and send them to the globalUI using
         * setters and getters. Since I'm calling the getter methods for runtime and memory below, I need to call this method first.
         */
        showResults(courseNumber);

        //call search algorithm functions
        linealSearch (sortedCoursesList, courseNumber, barsListBox1, searchingBox1Label.getFirst(), globalUI, globalUI.getRuntime1().getText(), globalUI.getMemory1().getText());
        binarySearch (sortedCoursesList, courseNumber, barsListBox2, searchingBox2Label.getFirst(), globalUI, globalUI.getRuntime2().getText(), globalUI.getMemory2().getText());
        mapSearch    (courseDataHashMap, courseNumber, barsListBox3, searchingBox3Label.getFirst(), globalUI, globalUI.getRuntime3().getText(), globalUI.getMemory3().getText());
        treeMapSearch(courseDataTreeMap, courseNumber, barsListBox4, searchingBox4Label.getFirst(), globalUI, globalUI.getRuntime4().getText(), globalUI.getMemory4().getText());

        //initialize result container
        resultPane = globalUI.getResultBoxesHolder();
        resultPane.setVisible(false);
        Text title = globalUI.getTitle();
        title.getStyleClass().add("result-title");
        resultTitleBox = new HBox(title);
        resultTitleBox.setAlignment(Pos.CENTER);
        resultTitleBox.setVisible(false);

        //add result to center pane
        centerSection.getChildren().addAll(resultTitleBox, resultPane);
    }


    /**
     * This method will be called by the Algorithms class after each algorithm finishes
     * @param courseNumberToFind: the course number to find by the search algorithms
     */
    public void onSearchingAlgorithmsComplete(String courseNumberToFind) {
        completedAlgorithms++;

        // Only update and show the grid when all algorithms are done
        int totalAlgorithms = 4;
        if (completedAlgorithms >= totalAlgorithms) {

            // Make the grid visible
            resultPane.setVisible(true);
            resultTitleBox.setVisible(true);

            //call runtime algorithms and set new labels value
            /*
            * this method is commented here because I'm calling it earlier in the searchCourseByCourseNumber();
            * If i decide to remove the runtime and memory fields from the algorithm box, and
            * leave them just in the result summary, stop calling this method from searchCourseByCourseNumber()
            * and uncomment it here to call it from here.
            */
            //showResults(courseNumberToFind); //uncomment if removed it from searchCourseByCourseNumber()


        }
    }

    /**
     * Show data structure button.
     * This function creates a button for each data structure
     * then, it implements click events to buttons and call methods
     * to implement searching and sorting algorithms
     */
    private void showDataStructureButtons() {

        //create buttons
        buttonArrayList = globalUI.createButton("ArrayList");
        buttonLinkedList = globalUI.createButton("LinkedList");
        buttonHashMap = globalUI.createButton("HashMap");
        buttonTreeMap = globalUI.createButton("treeMap");

        //disable map buttons: sorting algorithms don't apply to them
        buttonHashMap.setDisable(true);
        buttonTreeMap.setDisable(true);


        //set click listeners for arrayList button
        buttonArrayList.setOnAction(event -> {
            buttonArrayList.setDisable(true);
            buttonLinkedList.setDisable(true);
            buttonHashMap.setDisable(true);
            buttonTreeMap.setDisable(true);
            resetCopiedDataList(courseDataArrayList, copyDataArrayList);
            sortingAlgorithms(buttonArrayList.getText(), copyDataArrayList);
        });
        //set click listener for linkedList button
        buttonLinkedList.setOnAction(event -> {
            buttonLinkedList.setDisable(true);
            buttonArrayList.setDisable(true);
            buttonHashMap.setDisable(true);
            buttonTreeMap.setDisable(true);
            resetCopiedDataList(courseDataLinkedList, copyDataLinkedList);
            sortingAlgorithms(buttonLinkedList.getText(), courseDataLinkedList);
        });

        //add to UI
        innerTopBarContainer.getChildren().addAll(buttonArrayList, buttonLinkedList, buttonHashMap, buttonTreeMap);

    }

    //This method calls the LinealSearch algorithm
    private void linealSearch(List<Course> courseList, String courseNumberToFind, List<Rectangle> barsShape, Label message, GlobalUI ui, String runtime, String memory) {
        algorithmsHandler.linearSearchWithAnimation(courseList, courseNumberToFind, barsShape, message, ui, runtime, memory);
    }
    private void linearSearchResults(String courseNumberToFind){

        //set result variables
        String name = "Linear Search";
        String complexity = "Complexity :  O(N)";
        String summary = "The result from the runtime and memory usage may not be 100% accurate due to the animation and UI overhead. " +
                "In a real-world scenario, these operations would run much faster without any visual elements involved.";

        String explanation = "Linear Search is the simplest search algorithm. " +
                "It sequentially checks each element in the list until the target value is found or the list is fully traversed. Its time complexity is O(n), where n is the number of elements. " +
                "In the worst case, the algorithm must examine every element, making it inefficient for large datasets. " +
                "Its space complexity is O(1), as it operates in constant space.";

        //set the result in GlobalUI
        globalUI.setAlgorithm1Results(name, complexity, summary, explanation);

    }
    private void linearSearchRuntime(String courseNumberToFind){
        //start time
        long startLinealTime = System.nanoTime();

        //call algorithm
        int temp2 = algorithmsHandler.linearSearch(sortedCoursesList, courseNumberToFind);

        //end time
        long endLinealTime = System.nanoTime();//end time

        //Measure duration (runtime)
        long duration = (endLinealTime - startLinealTime);

        String runtime = "Runtime :  " + duration + " nanoseconds";

        globalUI.getRuntime1().setText(runtime);

    }
    private void linearSearchMemoryUsage(String courseNumberToFind){

        Runtime runtimeLinear = Runtime.getRuntime();

        // Run garbage collector before measuring to get a clean slate
        runtimeLinear.gc();

        //Measure memory usage before the algorithm execution
        long usedMemoryBeforeLinear = runtimeLinear.totalMemory() - runtimeLinear.freeMemory();

        //call algorithm
        int temp2 = algorithmsHandler.linearSearch(sortedCoursesList, courseNumberToFind);

        //Measure memory usage after the algorithm execution
        long usedMemoryAfterLinear = runtimeLinear.totalMemory() - runtimeLinear.freeMemory();

        // Calculate memory usage
        long memory = (usedMemoryAfterLinear - usedMemoryBeforeLinear);

        //set string
        String memoryUsed = "Memory usage :  " + memory + " bytes";

        globalUI.getMemory1().setText(memoryUsed);

    }


    //This method calls the binarySearch algorithm
    private void binarySearch(List<Course> courseList, String courseNumberToFind, List<Rectangle> barsShape, Label message, GlobalUI ui, String runtime, String memory) {
        algorithmsHandler.binarySearchWithAnimation(courseList, courseNumberToFind, barsShape, message, ui, runtime, memory);
    }
    private void binarySearchResults(String courseNumberToFind){
        String name = "Binary Search";
        String complexity = "Complexity : O(log N)";
        String summary = "As with the other algorithms, the runtime and memory usage values might not fully " +
                "reflect the actual performance due to the UI animations. " +
                "Binary Search should generally outperform Linear Search for larger datasets, " +
                "but discrepancies can occur because of how the UI and animation are implemented.";


        String explanation =  "Binary Search is a more efficient algorithm, but it only works on sorted arrays. " +
                "It repeatedly divides the search interval in half, reducing the number of elements to search with each step. " +
                "Its time complexity is O(\\log n), making it significantly faster than Linear Search for large datasets. " +
                "However, the space complexity is still O(1), as it does not require additional memory beyond a few variables to track the search interval.";

        globalUI.setAlgorithm2Results(name, complexity, summary, explanation);

    }
    private void binarySearchRuntime(String courseNumberToFind){

        //start time
        long startBinaryTime = System.nanoTime();

        //call algorithm
        int temp = algorithmsHandler.binarySearch(sortedCoursesList, courseNumberToFind);

        //end time
        long endTime = System.nanoTime();

        //measure runtime
        long runtime = (endTime - startBinaryTime);

        //set string
        String runtimeResult = "Runtime :  " + runtime + " nanoseconds";

        globalUI.getRuntime2().setText(runtimeResult);


    }
    private void binarySearchMemoryUsage(String courseNumberToFind){

        Runtime runtimeBinary = Runtime.getRuntime();

        // Run garbage collector before measuring to get a clean slate
        runtimeBinary.gc();

        //Measure memory usage before the algorithm execution
        long usedMemoryBeforeBinary = runtimeBinary.totalMemory() - runtimeBinary.freeMemory();

        //call algorithm
        int temp2 = algorithmsHandler.binarySearch(sortedCoursesList, courseNumberToFind);

        //Measure memory usage after the algorithm execution
        long usedMemoryAfterBinary = runtimeBinary.totalMemory() - runtimeBinary.freeMemory();

        // Calculate memory usage
        long memoryUsed =  (usedMemoryAfterBinary - usedMemoryBeforeBinary);

        //set string
        String memory = "Memory usage :  " + memoryUsed + " bytes";

        //update UI
        globalUI.getMemory2().setText(memory);

    }


    //This method calls the mapSearch function
    private void mapSearch(Map<String, Course> map, String courseNumberToFind, List<Rectangle> barsShape, Label message, GlobalUI ui, String runtime, String memory ) {
        algorithmsHandler.searchMapWithAnimation(map, courseNumberToFind, barsShape, message, ui, runtime, memory);
    }
    private void hashMapSearchResults(String courseNumberToFind){

        //update variables on global ui
        String name = "Map Search";
        String complexity = "Complexity :  O(1)";
        String summary = "Like the other algorithms, the UI and animations may slightly impact the accuracy of runtime and memory results. " +
                "Hash Map-based searching is usually the fastest but can have minor inconsistencies in this context due to UI overhead.";


        String explanation =  "Hash Map Search uses a hash table to store and retrieve data in constant time, O(1), for both insertion and lookup operations under ideal circumstances. " +
                "This is why it’s considered extremely efficient. However, in the worst case (e.g., hash collisions), the time complexity can degrade to O(n). " +
                "The space complexity is generally O(n), where n is the number of elements, due to the extra storage needed for the hash table.";

        //set the result in GlobalUI
        globalUI.setAlgorithm3Results(name, complexity, summary, explanation);

    }
    private void hashMapSearchRuntime(String courseNumberToFind){

        //start time
        long startMapTime = System.nanoTime();

        //call algorithm
        Course course = algorithmsHandler.mapSearch(courseDataHashMap, courseNumberToFind);

        //end time
        long endMapTime = System.nanoTime();

        //measure runtime
        long runtime = (endMapTime - startMapTime);

        //set string
        String runtimeResult = "Runtime :  " + runtime + " nanoseconds";

        //update ui
        globalUI.getRuntime3().setText(runtimeResult);

    }
    private void hashMapSearchMemoryUsed(String courseNumberToFind){
        Runtime runtimeHashMap = Runtime.getRuntime();

        // Run garbage collector before measuring to get a clean slate
        runtimeHashMap.gc();

        //Measure memory usage before the algorithm execution
        long usedMemoryBeforeHash = runtimeHashMap.totalMemory() - runtimeHashMap.freeMemory();

        //call algorithm
        Course temp2 = algorithmsHandler.mapSearch(courseDataHashMap, courseNumberToFind);

        //Measure memory usage after the algorithm execution
        long usedMemoryAfterHash = runtimeHashMap.totalMemory() - runtimeHashMap.freeMemory();

        // Calculate memory usage
        long memory =  (usedMemoryAfterHash - usedMemoryBeforeHash);

        //set string
        String memoryResult = "Memory usage :  " + memory + " bytes";

        //add to ui
        globalUI.getMemory3().setText(memoryResult);

    }


    //this method calls the mapSearch function for a tree Map
    private void treeMapSearch(Map<String, Course> treeMap, String courseNumberToFind, List<Rectangle> barsShape, Label message, GlobalUI ui, String runtime, String memory){
        algorithmsHandler.searchMapWithAnimation(treeMap, courseNumberToFind, barsShape, message, ui, runtime, memory);
    }
    private void treeMapSearchResults(String courseNumberToFind){
        //update ui variables
        String name = "TreeMap Search";
        String complexity = "Complexity :  O(log N)";
        String summary = "The runtime for the TreeMap search algorithm may not be entirely accurate due to the overhead introduced by the animated UI. " +
                "Animations, especially in real-time, can slightly delay the measured search times.\nSimilarly, memory usage is influenced by the implementation of the UI, " +
                "so the reported values may include memory consumed by graphical elements and animation rendering.";

        String explanation = "The TreeMap search utilizes a TreeMap structure, which is a type of NavigableMap that uses a red-black tree for sorting and searching elements. " +
                "In this implementation, searching for a key involves traversing the tree structure.\n" +
                "The average time complexity of a TreeMap search operation is O(log n) due to the red-black tree’s balanced nature. " +
                "This ensures logarithmic performance for insertion, deletion, and search operations.";

        globalUI.setAlgorithm4Results(name, complexity, summary, explanation);
    }
    private void treeMapSearchRuntime(String courseNumberToFind){
        //start time
        long startTreeMapTime = System.nanoTime();

        //call algorithm
        Course course = algorithmsHandler.mapSearch(courseDataTreeMap, courseNumberToFind);

        //end time
        long endTreeMapTime = System.nanoTime();

        //measure runtime
        long runtime = (endTreeMapTime - startTreeMapTime);

        //set string
        String runtimeResult = "Runtime :  " + runtime + " nanoseconds";

        //add to ui
        globalUI.getRuntime4().setText(runtimeResult);

    }
    private void treeMapSearchMemoryUsed(String courseNumberToFind){

        Runtime runtimeTreeMap = Runtime.getRuntime();

        // Run garbage collector before measuring to get a clean slate
        runtimeTreeMap.gc();

        //Measure memory usage before the algorithm execution
        long usedMemoryBeforeTree = runtimeTreeMap.totalMemory() - runtimeTreeMap.freeMemory();

        //call algorithm
        Course temp4 = algorithmsHandler.mapSearch(courseDataTreeMap, courseNumberToFind);

        //Measure memory usage after the algorithm execution
        long usedMemoryAfterTree = runtimeTreeMap.totalMemory() - runtimeTreeMap.freeMemory();

        // Calculate memory usage
        long memory = (usedMemoryAfterTree - usedMemoryBeforeTree);

        //set string
        String memoryUsed = "Memory usage :  " + memory + " bytes";

        //add to ui
        globalUI.getMemory4().setText(memoryUsed);

    }


    /**
     * This method calls all sorting algorithms
     * and implements the UI for each algorithm box
     * @param buttonName: the name of the selected data structure button
     * @param courseList: the list of course objects to sort
     */
    private void sortingAlgorithms(String buttonName, List<Course>courseList) {

        globalUI = new GlobalUI();//re initialize global ui as clean slate

        //algorithm box 1 elements
        List<Label> sortingBox1Label = new ArrayList<>();
        List<Rectangle> barsListBox1 = new ArrayList<>();
        List<Course> unsortedCourseList1 = new ArrayList<>(courseList);
        Label message  = new Label("message");
        VBox sortBox1 = globalUI.createAlgorithmBox("Insertion Sort", unsortedCourseList1, barsListBox1, sortingBox1Label);


        //algorithm box 2 elements
        List<Label> sortingBox2Label = new ArrayList<>();
        List<Rectangle> barsListBox2 = new ArrayList<>();
        List<Course> unsortedCourseList2 = new ArrayList<>(courseList);
        VBox sortBox2 = globalUI.createAlgorithmBox("Selection Sort", unsortedCourseList2, barsListBox2, sortingBox2Label);


        //algorithm 3 elements
        List<Label> sortingBox3Label = new ArrayList<>();
        List<Rectangle> barsListBox3 = new ArrayList<>();
        List<Course> unsortedCourseList3 = new ArrayList<>(courseList);
        VBox sortBox3 = globalUI.createAlgorithmBox("Quick Sort", unsortedCourseList3, barsListBox3, sortingBox3Label);


        //algorithm 4 elements
//        List<Label> sortingBox4Label = new ArrayList<>();
//        List<Rectangle> barsListBox4 = new ArrayList<>();
//        List<Course> unsortedCourseList4 = new ArrayList<>(courseList);
//        VBox sortBox4 = globalUI.createAlgorithmBox("Merge Sort", unsortedCourseList4, barsListBox4, sortingBox4Label);


        //create flow pane to hold the boxes
        FlowPane sortAlgorithmsHolderBox = new FlowPane(sortBox1, sortBox2, sortBox3/*, sortBox4*/);
        sortAlgorithmsHolderBox.setHgap(15);
        sortAlgorithmsHolderBox.setVgap(15);
        sortAlgorithmsHolderBox.setAlignment(Pos.CENTER);


        //add to main pane
        centerSection.getChildren().clear();
        centerSection.getChildren().addAll(sortAlgorithmsHolderBox);


        //call algorithms
        //If button pressed is arrayList, sort arrayList Data structure
        if (buttonName.equalsIgnoreCase("arrayList")) {

            //sort list
            insertionSort(unsortedCourseList1, barsListBox1, sortingBox1Label);
            selectionSort(unsortedCourseList2, barsListBox2, sortingBox2Label);
            quickSort    (unsortedCourseList3, barsListBox3, sortingBox3Label);
            //mergeSort    (unsortedCourseList4, barsListBox4, sortingBox4Label);


            ////////////// set results UI
            //set result variables
            String name1 = "Insertion Sort";
            String name2 = "Selection Sort";
            String name3 = "Quick Sort";

            String complexity1 = "Complexity :  O(N)";
            String complexity2 = "Complexity :  O(N)";
            String complexity3 = "Complexity :  O(N)";

            String memory1 = "soon1";
            String memory2 = "soon2";
            String memory3 = "soon3";

            //call globalUI setters to set the result boxes values
            setRuntimeForSorting(courseList, globalUI.getRuntime1()); //set runtime1
            setRuntimeForSorting(courseList, globalUI.getRuntime2()); //set runtime2
            setRuntimeForSorting(courseList, globalUI.getRuntime3()); //set runtime3
            globalUI.getMemory1().setText(memory1); //set memory 1
            globalUI.getMemory2().setText(memory2); //set memory 2
            globalUI.getMemory3().setText(memory3); //set memory 3
            //set the rest
            globalUI.setAlgorithm1Results(name1, complexity1, "summary here", "Explanation here");
            globalUI.setAlgorithm2Results(name2, complexity2, "summary here", "Explanation here");
            globalUI.setAlgorithm3Results(name3, complexity3, "summary here", "Explanation here");

        }
        else if(buttonName.equalsIgnoreCase("linkedList")) {

            //sort linkedList
            insertionSort(unsortedCourseList1, barsListBox1, sortingBox1Label);
            selectionSort(unsortedCourseList2, barsListBox2, sortingBox2Label);
            quickSort    (unsortedCourseList3, barsListBox3, sortingBox3Label);
            //mergeSort    (unsortedCourseList4, barsListBox4, sortingBox4Label);
        }

        //initialize result container
        resultPane = globalUI.getResultBoxesHolder();
        resultPane.setVisible(false);
        Text title = globalUI.getTitle();
        title.getStyleClass().add("result-title");
        resultTitleBox = new HBox(title);
        resultTitleBox.setAlignment(Pos.CENTER);
        resultTitleBox.setVisible(false);

        //add result to center pane
        centerSection.getChildren().addAll(resultTitleBox, resultPane);

    }

    //Method to notify UI when an algorithm has finished its execution
    public void onSortingAlgorithmsComplete(){

        completedAlgorithms++;

        if(completedAlgorithms >= 3){

            //re-activate all buttons
            System.out.println("onSorting received complete status");
            buttonArrayList.setDisable(false);
            buttonLinkedList.setDisable(false);

            //show results
            resultTitleBox.setVisible(true);
            resultPane.setVisible(true);
        }
    }

    //Method to reset list state after sort completed.
    private void resetCopiedDataList(List<Course> originalList, List<Course> copyList){
            copyList.clear();
            copyList.addAll(originalList);
    }

    //This method calls the insertionSort function
    private void insertionSort(List<Course>list, List<Rectangle>shapeList, List<Label>labels) {
        algorithmsHandler.insertionSortWithAnimation(list, shapeList,labels);
    }

    //This method calls the selectionSort function
    private void selectionSort(List<Course>list, List<Rectangle>shapeList, List<Label>labels ) {
        algorithmsHandler.selectionSortWithAnimation(list, shapeList, labels );
    }

    //This method calls the quickSort function
    private void quickSort(List<Course>list, List<Rectangle>shapeList, List<Label>labels) {
        algorithmsHandler.getQuickSortWithAnimation(list, shapeList, labels);
    }

     //TODO This method calls the mergeSort function
    private void mergeSort() {}

    private long setRuntimeForSorting(List<Course>sortedList, Label label){
        //start time
        long startQuickTime = System.nanoTime();

        //call algorithm
        List<Course> tempList = algorithmsHandler.quickSort(sortedList);

        //end time
        long endQuickTime = System.nanoTime();//end time

        //Measure duration (runtime)
        long duration = (endQuickTime - startQuickTime);

        String runtime = "Runtime :  " + duration + " nanoseconds";

        label.setText(runtime);

        return duration;
    }

    //This method shows the result section in the pane when called it.
    private void showResults(String courseNumberToFind) {

        //linear
        linearSearchRuntime(courseNumberToFind);
        linearSearchMemoryUsage(courseNumberToFind);
        linearSearchResults(courseNumberToFind);
        //binary
        binarySearchRuntime(courseNumberToFind);
        binarySearchMemoryUsage(courseNumberToFind);
        binarySearchResults(courseNumberToFind);
        //hashMap
        hashMapSearchRuntime(courseNumberToFind);
        hashMapSearchMemoryUsed(courseNumberToFind);
        hashMapSearchResults(courseNumberToFind);
        //treeMap
        treeMapSearchRuntime(courseNumberToFind);
        treeMapSearchMemoryUsed(courseNumberToFind);
        treeMapSearchResults(courseNumberToFind);

    }



    //==========================================================================================
    //=============================================== center ===================================

    //Method to add colors to algorithms bars
    public void addColorToBars(List<Rectangle> list, boolean visited) {
        for (Node node : list) {
            if (visited) {
                Platform.runLater(() -> node.getStyleClass().add("visited"));
            } else {
                Platform.runLater(() -> node.getStyleClass().clear());//reset style
                Platform.runLater(() -> node.getStyleClass().addAll("rectangle", "sorted"));
            }
        }
    }


    //helper method to style multiple nodes at the same time
    public void setNodeStyle(double min, double high, String color, Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof TextField) {
                node.setStyle("-fx-control-inner-background: " + color);
                ((TextField) node).setMinWidth(min);
                ((TextField) node).setPrefWidth(((TextField) node).getText().length() * 7);
                ((TextField) node).setPrefHeight(high);
            }

            if (node instanceof Label) {
                node.setStyle("-fx-font-size:36");
            }
        }
    }




    //////////////////////////// getters and setters for data structures /////////////////////////////
    public LinkedList<Course> getCopyDataLinkedList() {
        return copyDataLinkedList;
    }

    public void setCopyDataLinkedList(LinkedList<Course> copyDataLinkedList) {
        this.copyDataLinkedList = copyDataLinkedList;
    }

    public HashMap<String, Course> getCopyDataHashMap() {
        return copyDataHashMap;
    }

    public void setCopyDataHashMap(HashMap<String, Course> copyDataHashMap) {
        this.copyDataHashMap = copyDataHashMap;
    }

    public TreeMap<String, Course> getCopyDataTreeMap() {
        return copyDataTreeMap;
    }

    public void setCopyDataTreeMap(TreeMap<String, Course> copyDataTreeMap) {
        this.copyDataTreeMap = copyDataTreeMap;
    }
}

