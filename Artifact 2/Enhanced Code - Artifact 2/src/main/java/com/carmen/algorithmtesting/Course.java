/**
 * Course Model class
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description: This is the Model class for the course object.
 * */
package com.carmen.algorithmtesting;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseNumber;
    private String courseName;
    private List<String> prerequisites;

    //constructor
    public Course() {}

    //constructor
    public Course(String courseNumber, String courseName, List<String> prerequisites) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.prerequisites = prerequisites;
    }

    //Method to check if the course data is empty
    public boolean isEmpty() {
        return courseName == null && courseNumber == null && prerequisites == null;
    }


    ////////////// getters and setters

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getPrerequisites() {
        if(prerequisites == null){
            return new ArrayList<>(0);
        }
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Override
    public String toString() {
        if(prerequisites != null && prerequisites.isEmpty()){
            return "\nCourse Number = " + courseNumber + "\n" +
                    "Course Name = " + courseName + "\n" +
                    "Prerequisites = None" ;
        }
        return  "\nCourse Number = " + courseNumber + "\n" +
                "Course Name = " + courseName + "\n" +
                "Prerequisites = " + prerequisites ;

    }
}
