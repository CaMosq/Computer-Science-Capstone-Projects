/**
 * Algorithms Tester
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description: This class implements sorting and searching algorithms
 * on different data structures
 */
package com.carmen.algorithmtesting;

import com.carmen.algorithmtesting.FXControllers.GlobalUI;
import com.carmen.algorithmtesting.FXControllers.MainController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AlgorithmsHandler {

    // Reference to the MainController
    private final MainController controller;

    //constructor
    public AlgorithmsHandler(MainController controller) {
        this.controller = controller;

    }


    /**
     * Binary Search Algorithm. O(log n)
     * This function implements the binary search algorithm to
     * search for a course number
     *
     * @param coursesList:        any type of Alphabetically ordered list (ArrayList, LikedList) of curse numbers
     * @param courseNumberToFind: the course number to search for
     * @param barsShape:          the list of rectangular bars that shows the visual animation
     * @param message:            the feedback message that shows if the item was found or not.
     */
    public void binarySearchWithAnimation(List<Course> coursesList, String courseNumberToFind, List<Rectangle> barsShape, Label message, GlobalUI globalUI, String runtime, String memory) {
        //variables
        Timeline binaryTimeLine = new Timeline();
        int binarySpeed = 500;
        final int[] lowIndex = {0};
        final int[] highIndex = {coursesList.size() - 1};
        AtomicBoolean binaryFound = new AtomicBoolean(false);
        AtomicInteger binaryFoundIndex = new AtomicInteger();
        AtomicReference<Course> binaryFoundCourse = new AtomicReference<>(new Course());


        // Binary search animation
        KeyFrame binaryFrame = new KeyFrame(Duration.millis(binarySpeed), event -> {
            if (lowIndex[0] <= highIndex[0] && !binaryFound.get()) {
                // Get middle index
                int middleIndex = (lowIndex[0] + highIndex[0]) / 2;
                Course midValue = coursesList.get(middleIndex);

                // Reset all bar colors to default to clear previous highlights
                for (Rectangle rectangle : barsShape) {
                    rectangle.setFill(Color.BLACK);
                }

                // Highlight the current search range
                for (int k = lowIndex[0]; k <= highIndex[0]; k++) {
                    barsShape.get(k).setFill(Color.LIGHTBLUE); // Highlight the current search range
                }

                // Highlight the middle bar separately (e.g., red for comparison)
                barsShape.get(middleIndex).setFill(Color.RED);

                // If middle value is less than the search key, move to the right
                if (midValue.getCourseNumber().compareTo(courseNumberToFind) < 0) {
                    lowIndex[0] = middleIndex + 1; // Search right side
                    //System.out.println("searching right side " + lowIndex[0]);

                    // If middle value is greater than the search key, move to the left
                } else if (midValue.getCourseNumber().compareTo(courseNumberToFind) > 0) {
                    highIndex[0] = middleIndex - 1; // Search left side
                    //System.out.println("searching left side " + highIndex[0]);

                    // If the value is found
                } else {
                    binaryFoundIndex.set(middleIndex);
                    binaryFound.set(true);
                    binaryFoundCourse.set(midValue);
                    barsShape.get(middleIndex).getStyleClass().add("visited"); // Style the found item
                    //System.out.println("Key found at index: " + middleIndex);
                }

            } else {
                // If the search is complete or item is not found
                binaryTimeLine.stop();
                setCourseFoundMessage(binaryFound, binaryFoundCourse.get(), courseNumberToFind, message, binaryFoundIndex, runtime, memory);

//                //measure runtime on the algorithm without animations
//                long startBinaryTime = System.nanoTime();
//                int temp = binarySearch(coursesList, courseNumberToFind);
//                long endTime = System.nanoTime();
//                long dur = (endTime - startBinaryTime);
//                String duration = (dur + " Nanoseconds");
//
//
//                String name = "Binary Search";
//                String memory = "coming soon";
//                String complexity = "O(log N)";
//                String summary = "As with the other algorithms, the runtime and memory usage values might not fully " +
//                        "reflect the actual performance due to the UI animations. " +
//                        "Binary Search should generally outperform Linear Search for larger datasets, " +
//                        "but discrepancies can occur because of how the UI and animation are implemented.\n\nExplanation:" +
//                        "\nBinary Search is a more efficient algorithm, but it only works on sorted arrays. " +
//                        "It repeatedly divides the search interval in half, reducing the number of elements to search with each step. " +
//                        "Its time complexity is O(\\log n), making it significantly faster than Linear Search for large datasets. " +
//                        "However, the space complexity is still O(1), as it does not require additional memory beyond a few variables to track the search interval.";
//                //System.out.println("stopped");
//
//                //set the result in GlobalUI
//                globalUI.setAlgorithm2Results(name, duration, memory, complexity, summary);

                //Notify the controller that this algorithm is complete
                controller.onSearchingAlgorithmsComplete(courseNumberToFind);
            }
        });

        // Add the keyframe to the timeline and play
        binaryTimeLine.getKeyFrames().add(binaryFrame);
        binaryTimeLine.setCycleCount(Timeline.INDEFINITE); // Repeat until the search is complete
        binaryTimeLine.play();

    }

    public int binarySearch(List<Course> dataList, String searchKey) {
        int low = 0;
        int high = dataList.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Course midValue = dataList.get(mid);

            if (midValue.getCourseNumber().compareTo(searchKey) < 0) {
                low = mid + 1;
            } else if (midValue.getCourseNumber().compareTo(searchKey) > 0) {
                high = mid - 1;
            } else {
                return mid; // Key found
            }
        }
        return -1; // Key not found
    }

    /**
     * Lineal Search Algorithm:
     * This function implements the linear search algorithm with animations
     *
     * @param courseList:         any type of list(ArrayList, LikedList) of course numbers
     * @param courseNumberToFind: the target course number
     * @param barsShape:          the list of rectangular bars that shows the visual animation
     * @param message:            the feedback message that shows if the item was found or not.
     */
    public void linearSearchWithAnimation(List<Course> courseList, String courseNumberToFind, List<Rectangle> barsShape, Label message, GlobalUI globalUI, String runtime, String memory) {
        //initialize variables
        Timeline linealTimeline = new Timeline();
        int linealSpeed = 500;
        AtomicBoolean linealFound = new AtomicBoolean(false);
        AtomicInteger linealFoundIndex = new AtomicInteger();
        AtomicReference<Course> foundCourse = new AtomicReference<>(new Course());

        //perform linear search
        for (int i = 0; i < courseList.size(); i++) {
            //start animation
            int finalI = i;
            KeyFrame linealFrame = new KeyFrame(Duration.millis(linealSpeed * (i + 1)), event -> {
                //System.out.println("Linear speed: " + speed * (finalI + 1));
                barsShape.get(finalI).setFill(Color.LIGHTBLUE);
                if (courseList.get(finalI).getCourseNumber().equals(courseNumberToFind)) { //key found

                    // Highlight the item found
                    barsShape.get(finalI).setFill(Color.GREEN);

                    //set return result
                    linealFound.set(true);
                    linealFoundIndex.set(finalI);
                    foundCourse.set(courseList.get(finalI));


                }

            });
            //add animation to the timeline
            linealTimeline.getKeyFrames().add(linealFrame);
        }

        //play the timeline
        linealTimeline.play();

//        //start time
//        long startLinealTime = System.nanoTime();
//        int temp2 = linearSearch(courseList, courseNumberToFind);
//        long endLinealTime = System.nanoTime();//end time
//
//        //duration
//        long linealDuration = (endLinealTime - startLinealTime);
//        String durationMilliseconds2 = (linealDuration + " Nanoseconds");


        linealTimeline.setOnFinished(event -> {
            //display the found course info.
            setCourseFoundMessage(linealFound, foundCourse.get(), courseNumberToFind, message, linealFoundIndex, runtime, memory);
            //System.out.println("linear found at index: " + foundIndex);

//            //set result variables
//            String name = "Linear Search";
//            String memory = "coming soon lineal";
//            String complexity = "O(N)";
//            String summary = "The result from the runtime and memory usage may not be 100% accurate due to the animation and UI overhead. " +
//                    "In a real-world scenario, these operations would run much faster without any visual elements involved.\n\nExplanation:\nLinear Search is the simplest search algorithm. " +
//                    "It sequentially checks each element in the list until the target value is found or the list is fully traversed. Its time complexity is O(n), where n is the number of elements. " +
//                    "In the worst case, the algorithm must examine every element, making it inefficient for large datasets. " +
//                    "Its space complexity is O(1), as it operates in constant space.";
//
//            //set the result in GlobalUI
//            globalUI.setAlgorithm1Results(name, durationMilliseconds2, memory, complexity, summary);

            //Notify the controller that this algorithm is complete
            controller.onSearchingAlgorithmsComplete(courseNumberToFind);

        });

    }

    public int linearSearch(List<Course> dataList, String key) {
        for (int i = 0; i < dataList.size(); ++i) {
            if (dataList.get(i).getCourseNumber().equals(key)) {
                return i;
            }
        }

        return -1; // not found
    }


    //helper method to print the found course information
    public void setCourseFoundMessage(AtomicBoolean courseFound, Course courseToFind, String searchedKey, Label message, AtomicInteger index, String runtime, String memory) {
        if (courseFound.get()) {
            message.setText("Course Found: " + courseToFind + "\n\nFound at position: " + index + "\n\n" +
                    runtime + "\n\n" + memory);
        } else {
            message.setText("Course " + searchedKey + "\nNot found.");
        }
    }

    /**
     * Search Map. hashMap O(1), treeMap O(log N)
     * This function implement searching in a map data structure
     *
     * @param map:         any map such as HashMap or TreeMap
     * @param searchedKey: the searched argument, in this case is the course number
     * @param barsShape:   the list of rectangular bars that shows the visual animation
     * @param message:     the feedback message that shows if the item was found or not.
     */
    public void searchMapWithAnimation(Map<String, Course> map, String searchedKey, List<Rectangle> barsShape, Label message, GlobalUI globalUI, String runtime, String memory) {
        //initialize variables
        Timeline timeline = new Timeline();
        int mapSpeed = 500;

        //search key in map
        if (map.containsKey(searchedKey)) {//if found
            int index = new ArrayList<>(map.keySet()).indexOf(searchedKey);// Find the index for the visual representation
            if (index != -1) {
                //animate the found key
                KeyFrame frame = new KeyFrame(Duration.millis(mapSpeed), event -> {
                    barsShape.get(index).getStyleClass().add("visited");//highlight the found key
                    message.setText("Course Found: " + map.get(searchedKey).toString() + "\n\nFound at position " + index + "\n\n" +
                            runtime + "\n\n" + memory);

                });
                timeline.getKeyFrames().add(frame);
                timeline.play();
            }
//            long startMapTime = System.nanoTime();
//            Course course = mapSearch(map, searchedKey);
//            long endMapTime = System.nanoTime();
//            long duration = (endMapTime - startMapTime);
//            String durationMilliseconds3 = (duration + " Nanoseconds");
//            String name = "Map Search";
//            String memory = "coming soon map";
//            String complexity = "O(1)";
//            String summary = "Like the other algorithms, the UI and animations may slightly impact the accuracy of runtime and memory results. " +
//                    "Hash Map-based searching is usually the fastest but can have minor inconsistencies in this context due to UI overhead.\n\nExplanation: " +
//                    "\nHash Map Search uses a hash table to store and retrieve data in constant time, O(1), for both insertion and lookup operations under ideal circumstances. " +
//                    "This is why it’s considered extremely efficient. However, in the worst case (e.g., hash collisions), the time complexity can degrade to O(n). " +
//                    "The space complexity is generally O(n), where n is the number of elements, due to the extra storage needed for the hash table.";
//
//            //set the result in GlobalUI
//            globalUI.setAlgorithm3Results(name, durationMilliseconds3, memory, complexity, summary);

            //Notify the controller that this algorithm is complete
            controller.onSearchingAlgorithmsComplete(searchedKey);


        } else {
            //if the key is not found display a message
            message.setText("Course " + searchedKey + "\nNot found");


        }
    }

    public Course mapSearch(Map<String, Course> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;//not found
    }


    /**
     * Insertion Sort O(N^2)
     * This function implements the insertion sort algorithm to
     * sort a list of objects
     *
     * @param list: a list of courses in this case
     */
    public List<Course> insertionSort(List<Course> list) {
        for (int i = 1; i < list.size(); i++) {
            Course key = list.get(i); // The element to be inserted
            int j = i - 1;

            // Shift elements of list[0...i-1] that are greater than key to one position ahead
            // of their current position
            while (j >= 0 && list.get(j).getCourseName().compareTo(key.getCourseName()) > 0) {
                list.set(j + 1, list.get(j)); // Move the larger element one position to the right
                j--;
            }
            list.set(j + 1, key); // Insert the key in its correct position
        }
        return list;
    }

    public void insertionSortWithAnimation(List<Course> list, List<Rectangle> rects, List<Label> labels) {

        Timeline timelineInsertion = new Timeline();
        int speedIn = 500;
        boolean doneIn = false;

        //start loop
        for (int i = 1; i < list.size(); i++) {
            int j = i;
            Course temp = list.get(j);//temporary variable
            Rectangle rectTemp = rects.get(j);//temporary rectangle

            //start sorting
            while (j > 0 && list.get(j - 1).getCourseName().compareTo(temp.getCourseName()) > 0) {

                // Swap in the list
                list.set(j, list.get(j - 1));

                // Move the rectangles visually while sorting
                int finalJ = j;
                KeyFrame frameIn = new KeyFrame(Duration.millis(speedIn * (i + j)), e -> {

                    //set labels
                    labels.get(finalJ).setText(list.get(finalJ).getCourseName());

                    // Create a translate transition to move the rectangle
                    Rectangle rectangle1 = rects.get(finalJ);
                    Rectangle rectangle2 = rects.get(finalJ - 1);

                    //change rectangle color while sorting
                    rectangle1.setFill(Color.GREEN); //green for right rect
                    rectangle2.setFill(Color.GREEN);//lightBlue for left

                    // Compute the distance to swap
                    double distanceIn = rectangle2.getLayoutX() - rectangle1.getLayoutX();

                    // Create transition for both rectangles
                    TranslateTransition moveIn1 = new TranslateTransition(Duration.millis(speedIn), rectangle1);
                    moveIn1.setByX(distanceIn); // Move rectangle 1 to the left

                    TranslateTransition moveIn2 = new TranslateTransition(Duration.millis(speedIn), rectangle2);
                    moveIn2.setByX(-distanceIn); // Move rectangle 2 to the right

                    // Play both animations
                    moveIn1.play();
                    moveIn2.play();

                    // After animation ends, swap the actual positions
                    moveIn1.setOnFinished(ev -> {

                        //stop animation 2
                        moveIn2.stop();

                        // Swap actual layout positions to prevent overlap
                        double tempInX = rectangle1.getLayoutX();
                        rectangle1.setLayoutX(rectangle2.getLayoutX());
                        rectangle2.setLayoutX(tempInX);

                        // Reset TranslateX after the layout change
                        rectangle1.setTranslateX(0);
                        rectangle2.setTranslateX(0);

                        // Swap in the list of rectangle
                        rects.set(finalJ, rectangle2);
                        rects.set(finalJ - 1, rectangle1);

                        //apply final color
                        rectangle1.setFill(Color.LIGHTBLUE);
                        rectangle2.setFill(Color.LIGHTBLUE);

                    });

                });

                //add frame to timeline
                timelineInsertion.getKeyFrames().add(frameIn);

                j--;
            }

            // Set the final sorted position in the list and button array
            list.set(j, temp);
            rects.set(j, rectTemp); // Update the rectangle list
//            rects.get(j + 1).setFill(Color.LIGHTBLUE);//set final color for all rectangles

            // When done sorting, update the completion message
            if (i == list.size() - 1) {
                doneIn = true;
            }
        }

        // Ensure that the last element is set correctly
        KeyFrame finalFrameB = new KeyFrame(Duration.millis(speedIn * (list.size() + 1)), e -> {
            rects.get(list.size() - 1).getStyleClass().add("sorted"); // Ensure the last element is marked sorted
        });
        timelineInsertion.getKeyFrames().add(finalFrameB);


        // Show List of names sorting
        Timeline timelineLabels1 = new Timeline();
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            KeyFrame finalFrameLabels = new KeyFrame(Duration.millis(speedIn * (i + 9)), e -> {
                labels.get(finalI).setText(list.get(finalI).getCourseName());
            });

            timelineLabels1.getKeyFrames().add(finalFrameLabels);
        }

        //play the timeline 1: names sorting
        timelineLabels1.play();

        // Play the timeline : bars sorting
        timelineInsertion.play();


        //display completion message when last timeline ends
        boolean finalDoneIn = doneIn;

        timelineLabels1.setOnFinished(e -> {
            if (finalDoneIn) {
                System.out.println("sorting complete");
                controller.onSortingAlgorithmsComplete();
                controller.addColorToBars(rects, false);

            }
        });

    }

    /**
     * Selection Sort O(N^2).
     * This function implements the selection sort algorithm to
     * sort course data
     *
     * @param list: the list of values to be sorted
     * @return the sorted list
     */
    public List<Course> selectionSort(List<Course> list) {
        int smallestIndex = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            // Find the index of the smallest element in the unsorted portion
            smallestIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getCourseName().compareTo(list.get(smallestIndex).getCourseName()) < 0) {
                    smallestIndex = j;
                }
            }
            // Swap the smallest element with the element at index i
            if (smallestIndex != i) { // Only swap if the smallest element is not already in the correct position
                Course temp = list.get(i);
                list.set(i, list.get(smallestIndex));
                list.set(smallestIndex, temp);
            }
        }
        return list;
    }

    public void selectionSortWithAnimation(List<Course> list, List<Rectangle> rects, List<Label> labels) {
        //variables
        Timeline timelineSel = new Timeline();
        int speedSel = 500; // Speed of the animation in milliseconds
        boolean doneSel = false;


        //start loop
        int smallestIndex = 0;
        for (final int[] i = {0}; i[0] < list.size() - 1; i[0]++) {

            // Find the index of the smallest element in the unsorted portion
            smallestIndex = i[0];
            for (int j = i[0] + 1; j < list.size(); j++) {
                if (list.get(j).getCourseName().compareTo(list.get(smallestIndex).getCourseName()) < 0) {
                    smallestIndex = j;
                }

            }

            // Swap the smallest element with the element at index i
            if (smallestIndex != i[0]) { // Only swap if the smallest element is not already in the correct position
                Course temp = list.get(i[0]);
                list.set(i[0], list.get(smallestIndex));
                list.set(smallestIndex, temp);

                // Swap buttons visually
                Rectangle rect1 = rects.get(i[0]);
                Rectangle rect2 = rects.get(smallestIndex);

                // Swap labels visually
                Label label1 = labels.get(i[0]);
                Label label2 = labels.get(smallestIndex);

                // Visual animation for swapping the buttons
                int finalSmallestIndex = smallestIndex;
                final int[] finalI = {i[0]};
                KeyFrame frameSel = new KeyFrame(Duration.millis(speedSel * (i[0] + 5)), e -> {

                    // Highlight the buttons being swapped
                    rect1.getStyleClass().add("visited"); // Change color to indicate the swap
                    rect2.getStyleClass().add("visited");

                    // Compute the distance to swap
                    double distanceSel = rect2.getLayoutX() - rect1.getLayoutX();
                    double labelDistanceY = label2.getLayoutY() - label1.getLayoutY();

                    // Create transitions for both buttons
                    TranslateTransition move1 = new TranslateTransition(Duration.millis(speedSel), rect1);
                    move1.setByX(distanceSel); // Move button 1 to the right

                    TranslateTransition move2 = new TranslateTransition(Duration.millis(speedSel), rect2);
                    move2.setByX(-distanceSel); // Move button 2 to the left


                    // Play both transitions
                    move1.play();
                    move2.play();

                    // After animation ends, swap the buttons' actual positions
                    move1.setOnFinished(ev -> {

                        move2.stop();

                        // Swap layout positions
                        double tempXSel = rect1.getLayoutX();
                        rect1.setLayoutX(rect2.getLayoutX());
                        rect2.setLayoutX(tempXSel);

                        // Reset TranslateX after swapping
                        rect1.setTranslateX(0);
                        rect2.setTranslateX(0);

                        // Update the list of buttons to reflect the swap
                        rects.set(finalI[0], rect2);
                        rects.set(finalSmallestIndex, rect1);

                        // Swap layout positions for labels
                        double tempLabelY = label1.getLayoutY();
                        label1.setLayoutY(label2.getLayoutY());
                        label2.setLayoutY(tempLabelY);

                        // Reset TranslateX after swapping
                        label1.setTranslateY(0);
                        label2.setTranslateY(0);

                        // Update the text in the labels based on the sorted list
                        labels.get(finalI[0]).setText(list.get(finalI[0]).getCourseName());
                        labels.get(finalSmallestIndex).setText(list.get(finalSmallestIndex).getCourseName());

                        // Apply final sorted color
                        rect1.getStyleClass().add("sorted");
                        rect2.getStyleClass().add("sorted");

                    });

                });


                // Add this animation step to the timeline
                timelineSel.getKeyFrames().add(frameSel);
            }

            // When done sorting, update the completion message
            if (i[0] == list.size() - 1) {
                doneSel = true;
            }

        }
        // Show List of names sorting
        Timeline timeline2 = new Timeline();
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            KeyFrame finalFrame2 = new KeyFrame(Duration.millis(speedSel * (i + 5)), e -> {
                labels.get(finalI).setText(list.get(finalI).getCourseName());
            });
            // When done sorting, update the completion message
            if (i == list.size() - 1) {
                doneSel = true;
                break;
            }


            timeline2.getKeyFrames().add(finalFrame2);
        }

        //play the timeline 1: names sorting
        timeline2.play();

        // Play the timeline
        timelineSel.play();


        //display completion message when last timeline ends
        boolean finalDoneSel = doneSel;
        timeline2.setOnFinished(e -> {
            if (finalDoneSel) {
                controller.addColorToBars(rects, false);
                controller.onSortingAlgorithmsComplete();
                System.out.println("sorting complete selection sort");
            }
        });
    }


    /**
     * Quick Sort algorithm O(N log N). best case O(N log N), average case O(N log N), worst case O(N^2)
     * This function implements the partition for the quickSort algorithm
     * The next six methods are part of the QuickSort algorithm implementation
     *
     * @param list: this list to be sorted
     * @param low:  the low index for the partition
     * @param high: the high index for the partition
     */
    private void quickSort(List<Course> list, int low, int high) {
        if (low < high) {
            // partition the list and get the pivot index
            int pivotIndex = partition(list, low, high);

            // recursively sort the left part of the list
            quickSort(list, low, pivotIndex - 1);

            // recursively sort the right part of the list
            quickSort(list, pivotIndex, high);
        }
    }
    private int partition(List<Course> list, int low, int high) {
        // pick the middle element as pivot point
        int middle = low + (high - low) / 2;
        Course pivot = list.get(middle);

        while (low <= high) {
            // find the first element greater than or equal to pivot from the left
            while (list.get(low).getCourseNumber().compareTo(pivot.getCourseNumber()) < 0) {
                low++;
            }
            // find the first element smaller than or equal to pivot from the right
            while (list.get(high).getCourseNumber().compareTo(pivot.getCourseNumber()) > 0) {
                high--;
            }

            // swap elements and move the boundaries
            if (low <= high) {
                Course temp = list.get(low);
                list.set(low, list.get(high));
                list.set(high, temp);
                low++;
                high--;
            }
        }
        return low;
    }
    //public quicksort with just one parameter: this is the function that I will call from FX controller
    public List<Course> quickSort(List<Course> list) {
        quickSort(list, 0, list.size() - 1);
        return list;
    }
    //private quick sort with animation
    private void quickSortWithAnimation(List<Course> list, List<Rectangle> rects, int low, int high, List<Label> labels) {

        //quicksort with animation
        Timeline quickTimeline = new Timeline();
        Timeline timeline22 = new Timeline();

        // If there are elements to sort
        if (low < high) {
            // Partition the array and get the pivot index
            int pi = partitionWithAnimation(list, rects, low, high, quickTimeline);

            // Recursively sort the left half
            quickSortWithAnimation(list, rects, low, pi - 1, labels);
            // Recursively sort the right half
            quickSortWithAnimation(list, rects, pi + 1, high, labels);
        }

        // Show List of names sorting
        for (int k = 0; k < list.size(); k++) {
            int finalK = k;
            KeyFrame finalFrame22 = new KeyFrame(Duration.millis(500 * (k + 3)), e -> {
                labels.get(finalK).setText(list.get(finalK).getCourseName());
            });
            timeline22.getKeyFrames().add(finalFrame22);
        }

        //play labels timeline
        timeline22.play();

        //play rectangles timeline
        quickTimeline.play();

        quickTimeline.setOnFinished(event -> {
            System.out.println("quick sort algorithm finished.");
            controller.addColorToBars(rects, false);

        });
        timeline22.setOnFinished(e -> {
            controller.addColorToBars(rects, false);
            controller.onSortingAlgorithmsComplete();
            System.out.println("sorting complete quick sort");

        });


    }
    //private partition with animation
    private int partitionWithAnimation(List<Course> list, List<Rectangle> rects, int low, int high, Timeline quickTimeline) {
        Course pivot = list.get(high); // Choose the last element as pivot
        int i = low - 1; // Index of the smaller element

        // Visual: Highlight the pivot element
        // Speed of the animation
        int quickSpeed = 500;
        KeyFrame pivotFrame = new KeyFrame(Duration.millis(quickSpeed), e -> {
            rects.get(high).setFill(Color.GREEN); // Highlight the pivot
        });
        quickTimeline.getKeyFrames().add(pivotFrame);

        for (int j = low; j < high; j++) {
            int finalJ = j;
            int finalI = i;

            // Visual: Compare elements with the pivot
            KeyFrame compareFrame = new KeyFrame(Duration.millis(quickSpeed * (j + 1)), e -> {
                rects.get(finalJ).setFill(Color.BLUE); // Highlight the element being compared
            });
            quickTimeline.getKeyFrames().add(compareFrame);

            if (list.get(j).getCourseName().compareTo(pivot.getCourseName()) <= 0) {
                i++;
                // Swap list[i] and list[j]
                Course temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);

                // to swap visually
                Rectangle rec1 = rects.get(i + 1);
                Rectangle rec2 = rects.get(j);

                // Visual: Animate the swap
                KeyFrame swapFrame = new KeyFrame(Duration.millis(quickSpeed * (j + 1)), e -> {
                    //-----------------------
                    //compute distance to swap
                    double disX = rec2.getLayoutX() - rec1.getLayoutX();

                    //create transition
                    TranslateTransition moveRec1 = new TranslateTransition(Duration.millis(quickSpeed), rec1);
                    moveRec1.setByX(disX);//move rectangle to the right
                    TranslateTransition moveRec2 = new TranslateTransition(Duration.millis(quickSpeed), rec2);
                    moveRec2.setByX(-disX); //move rectangle to the lef
                    //play transitions
                    moveRec1.play();
                    moveRec2.play();

                    //after animation ends, swap rectangles to actual position
                    moveRec1.setOnFinished(event -> {

                        moveRec2.stop();

                        //swap layout position for rectangles
                        double tX = rec1.getLayoutX();
                        rec1.setLayoutX(rec2.getLayoutX());
                        rec2.setLayoutX(tX);

                        //reset translateX after swapping
                        rec1.setTranslateX(0);
                        rec2.setTranslateX(0);

                        //swap in the rectangles list
                        rects.set(finalI + 1, rec2);
                        rects.set(finalJ, rec1);

                        // Reset colors after swap
                        rects.get(finalJ).setFill(Color.RED);
                        rects.get(finalI + 1).setFill(Color.LIGHTBLUE);
                    });

                    // Swap rectangles visually (swap layoutX values)
//                    double xPosI = rects.get(finalI + 1).getLayoutX();
//                    double xPosJ = rects.get(finalJ).getLayoutX();
//
//                    rects.get(finalI + 1).setLayoutX(xPosJ);
//                    rects.get(finalJ).setLayoutX(xPosI);

                    // Reset colors after swap
//                    rects.get(finalJ).setFill(Color.RED);
//                    rects.get(finalI + 1).setFill(Color.GREEN);
                });
                quickTimeline.getKeyFrames().add(swapFrame);
            }

            // Reset color of compared element
            KeyFrame resetFrame = new KeyFrame(Duration.millis(quickSpeed * (j + 1) + 100), e -> {
                rects.get(finalJ).setFill(Color.RED);
            });
            quickTimeline.getKeyFrames().add(resetFrame);
        }

        // Swap list[i + 1] and list[high] (put pivot in the right position)
        Course temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        // Visual: Animate the final pivot swap
        int finalI1 = i;
        KeyFrame finalSwapFrame = new KeyFrame(Duration.millis(quickSpeed * (high + 1)), e -> {
            double xPosPivot = rects.get(high).getLayoutX();
            double xPosI1 = rects.get(finalI1 + 1).getLayoutX();

            TranslateTransition move1 = new TranslateTransition(Duration.millis(quickSpeed), rects.get(finalI1 + 1));
            TranslateTransition move2 = new TranslateTransition(Duration.millis(quickSpeed), rects.get(high));
            move1.setByX(rects.get(high).getLayoutX() - rects.get(finalI1 + 1).getLayoutX());
            move2.setByX(-(rects.get(high).getLayoutX() - rects.get(finalI1 + 1).getLayoutX()));

            //---------
            rects.get(high).setLayoutX(xPosI1);
            rects.get(finalI1 + 1).setLayoutX(xPosPivot);

            rects.get(finalI1 + 1).setFill(Color.LIGHTBLUE); // Final sorted color for pivot
            rects.get(high).setFill(Color.RED); // Reset the original pivot color

            move1.play();//play transition 1
            move2.play();//play transition 2


        });
        quickTimeline.getKeyFrames().add(finalSwapFrame);



        return i + 1; // Return the pivot index
    }
   //public quicksort to call from FX controller
    public void getQuickSortWithAnimation(List<Course> list, List<Rectangle> rects, List<Label> labels){
        quickSortWithAnimation(list, rects, 0, list.size()-1, labels);
    }

    /**
     * Merge Sort Algorithm O(N log N). Best case O(N log N), average O(N log N), worst O(N log N) --> "fast"
     * This function implements the merge sort algorithm to sort the course data
     * The next two methods are part of the mergeSort algorithm implementation
     *
     * @param list: the list to be sorted
     * @param <T>:  the type of data structure
     * @return the sorted list
     */
    public <T extends Comparable<T>> List<T> mergeSort(List<T> list) {
        if (list.size() <= 1) {
            return list; // Base case: a list with one or zero elements is already sorted
        }

        // Find the middle index
        int middle = list.size() / 2;

        // Divide the list into two halves
        List<T> left = new ArrayList<>(list.subList(0, middle));
        List<T> right = new ArrayList<>(list.subList(middle, list.size()));

        // Recursively sort both halves
        left = mergeSort(left);
        right = mergeSort(right);

        // Merge the two sorted halves
        return merge(left, right);
    }
    // Merge two sorted lists into one sorted list
    private <T extends Comparable<T>> List<T> merge(List<T> left, List<T> right) {
        List<T> result = new ArrayList<>();

        int i = 0, j = 0;

        // Merge the two lists
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // Add remaining elements from left (if any)
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        // Add remaining elements from right (if any)
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }
}