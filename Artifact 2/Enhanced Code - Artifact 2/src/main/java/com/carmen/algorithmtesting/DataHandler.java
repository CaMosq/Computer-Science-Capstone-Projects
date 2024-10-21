/**
 * Data Handler
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description: This class handles the data in the text file.
 * It loads the data into a data structure, validate the data format
 * and return an array with the extracted formatted data.
 * */


package com.carmen.algorithmtesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class DataHandler {

    //constructor
    public DataHandler(){}

    //method to split each line into fields and check for errors in the data format
    Course parseData(String line, int lineNumber) throws Exception {

        //create an object of the Course class
        Course course = new Course();

        //split the data by comma from each line into individual fields
        String[] fields = line.split (",");

        //Validation: each line should have at least 2 parameters
        if(fields.length < 2){
            System.out.println("Error: invalid data format on line: " + lineNumber);
            throw new Exception("Invalid data format on line: " + lineNumber);
        }

        //insert prerequisites into list: prerequisites start after course number and name
        List<String> prerequisitesList = new ArrayList<>(Arrays.asList(fields).subList(2, fields.length));

        //set course variables with the extracted data
        course.setCourseNumber(fields[0]);
        course.setCourseName(fields[1]);
        course.setPrerequisites(prerequisitesList);

        return course;

    }

    //Method to load data into a list
    public List<Course> loadData(String filePath){

        //create arrayLists to hold course data
        List<Course> coursesList = new ArrayList<>();

        //create a buffer to read the file
        BufferedReader reader = null;

        //create a line counter
        int lineCounter = 0;

        System.out.println("Opening file...");

        try{

            //get the file path
            File file = new File(filePath);

            //pass file to the reader for reading
            reader = new BufferedReader (new FileReader(file));

            //set data for each line in file
            String line = reader.readLine ();

            //start reading
            if(reader.ready()) {
                System.out.println("File (" + filePath + " ) opened successfully.\n\n loading data...");
                //while the file has a line to read
                while (line != null) {

                    //start line counter
                    lineCounter++;

                    //read each line
                    Course course = parseData(line, lineCounter);

                    //add course to courses list
                    coursesList.add(course);


                    //read the next line
                    line = reader.readLine();

                }
            }
            else {
                throw new RuntimeException("Unable to open file, check file path and try again!\\n");
            }
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return coursesList;
    }

    /**
     * This function loads the file data into an arrayList
     * @param filePath: the relative path to the text file
     * @return an arrayList loaded with the course data
     */
    public ArrayList<Course> loadDataInArrayList(String filePath){
       return new ArrayList<>(loadData(filePath));
    }

    /**
     * This function loads the file data into a linkedList
     * @param filePath: the relative path to the text file
     * @return a linkedList loaded with the course data
     */
    public LinkedList<Course> loadDataInLinkedList(String filePath){
        return new LinkedList<>(loadData(filePath));
    }

    /**
     * This function loads the file data into a hashMap
     * @param filePath: the relative path to the text file
     * @return a hashMap loaded with the course data
     */
    public HashMap<String, Course> loadDataInHashMap(String filePath){
        //create hashMap to hold course data
        HashMap<String, Course> courseHashMap = new HashMap<>();

        //add courses
        for(Course course: loadData(filePath)){
           courseHashMap.put(course.getCourseNumber(), course );
        }
        return courseHashMap;
    }

    /**
     * This function loads the file data into a treeMap
     * @param filePath: the relative path to the text file
     * @return a treeMap loaded with the course data
     */
    public TreeMap<String, Course> loadDataInTreeMap(String filePath){
        //create treeMap to hold course data
        TreeMap<String, Course> courseTreeMap = new TreeMap<>();

        //add courses
        for(Course course: loadData(filePath)){
            courseTreeMap.put(course.getCourseNumber(), course );
        }
        return courseTreeMap;
    }


}
