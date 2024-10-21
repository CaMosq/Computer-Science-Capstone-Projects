/**
* Created by Carmen Mosquera
* SNHU : CS 300 - Project Two
* Date: 12/ 6/ 2023
* Description: working with files(open, read, load, and print) Using a tree data structure
* 
* INPUT: Implemented in main() function.
* MENU : Implemented in main() function.
* LOAD DATA: Include functions buildTree(), parseData(), ValidateFields(), validatePrerequisites(), and loadCourseData(). 
* COURSELIST : Implemented in printAlphanumericOrderedCourses() function.
* COURSE INFORMATION : include functions findACourse(), and printCourseInfo().
* 
*/



#include <iostream>
#include <sstream>
#include <fstream>
#include <vector>
#include <fstream>
#include <cwctype>
#include <algorithm>

//class to implement a binary search tree.
class BinaryTree
{
public:
	//create struct to hold course information
	struct Course {

		std::string courseNumber;
		std::string courseTitle;
		std::vector<std::string> prerequisites;

		//default constructor
		Course(){}

		//constructor with all parameters
		Course(std::string number, std::string title, std::vector<std::string> pre): Course() {
			this->courseNumber = number;
			this->courseTitle = title;
			this->prerequisites = pre;
		}

	};

	//create a struct to hold TreeNode information
	struct TreeNode {
		Course course;
		TreeNode* left;
		TreeNode* right;
		int count;

		//defaul constructor
		TreeNode(){
			left = nullptr;
			right = nullptr;
			count = 0;
		}
		//constructor with a course
		TreeNode(Course aCourse): TreeNode() {
			course = aCourse;

		};

	};
    //==========
	
	BinaryTree();
	~BinaryTree();
	void buildTree(TreeNode* &node, const Course &co);
	Course parseData(std::string line, int l);
	bool validateFields(std::string str, int lineNumber);
	bool validatePrerequisites(std::string str, std::vector<std::string> list, int lineNumber);
	TreeNode* loadCourseData(std::string filePath, std::ifstream& myFile);
	TreeNode* findACourse(TreeNode*& node, std::string courseNumber);
	void printCourseInfo(TreeNode* &node);
	void printAlphanumericOrderedCourses(TreeNode*& node, const Course& co);
	
	


private:

	TreeNode* root;
};


//default class constructor
BinaryTree::BinaryTree()
{
	root = nullptr;
}


//class destructor
BinaryTree::~BinaryTree()
{
	delete root;
}



/**
 * This function builds a tree of courses from the input data
 * @param node 
 * @param courseData
 */
void BinaryTree::buildTree(TreeNode* &node, const Course &co)
{
	//if root node is empty, insert new node there.
	if (node == nullptr) {
		node = new TreeNode(co);
	}

	// else if not empty, compare course numbers to decide if traverse left or right side
	else if (co.courseNumber < node->course.courseNumber) {
		
		//recurse the left side
		buildTree(node->left, co);
	}
	else if (co.courseNumber > node->course.courseNumber) {
		
		//recurse the right side
		buildTree(node->right, co);
	}
}



/**
 * This function splits each line into field
 * and check for errors in the data format
 * @param line a line in course file
 * @param line number in file
 * @return course object
 */
BinaryTree::Course BinaryTree::parseData(std::string line, int lineNumber)
{
	//create course object
	Course co;

	//create delimiter for line split
	char delimiter = ',';

	//create vector to hold line fields
	std::vector<std::string> lineFields;

	//create stream
	std::stringstream ss(line);


	//create field;
	std::string field;

	//split line
	while (std::getline(ss, field, delimiter)) {

		//add each field to the vector of fields
		lineFields.push_back(field);
	}

	//VALIDATION: each line should have at leats two parameters 
	if (lineFields.size() < 2) {
		std::cout << "\nError: invalid course data format on line # " << lineNumber << std::endl;

		//throw exception
		throw std::invalid_argument("Update the data on your file and try again!\n");
		
	}

	//assign fields to course data
	co.courseNumber = lineFields[0];
	co.courseTitle = lineFields[1];

	//VALIDATION: validate fields format.
	if (!validateFields(co.courseNumber, lineNumber)) {
		throw std::invalid_argument("Update the data on your file and try again!\n");
		
	}


	//create vector to hold prerequisitesh 
	std::vector < std::string> prereList;
	for (int i = 2; i < lineFields.size(); i++) {
		co.prerequisites.push_back(lineFields[i]);
	}


	return co; //return course object
}



/**
 * This function validates course fields format
 * @param string field
 * @param int line counter
 * @return bool true or false
 */
bool BinaryTree::validateFields(std::string field, int lineNumber )
{
	//VALIDATE COURSE NUMBER FORMAT:
	
	//1. course number can't have more than 7 characteres
	int size = field.size();
	if (size != 7) {
		
		std::cout << "\nError: invalid format!" << std::endl;
		std::cout << "For course number (" << field << ") on line " << lineNumber << std::endl;
		std::cout << "** Character count exceeded. (7 maximum)." << std::endl;
		std::cout << std::endl;
	
		return false;
	}
	//2. course number cant have special characters,  must be alphanumeric.
	for (int i = 0; i < size; i++) {
		if (!iswalnum(field[i])) {
			std::cout << "\nError: invalid format!" << std::endl;
			std::cout << "For course number (" << field << ") on line " << lineNumber << std::endl;
			std::cout << "** Must be alphanumic. no special characters allowed" << std::endl;
			std::cout << std::endl;
			
			return false;
		}
	}

	//3. course number last 3 characters must be numeric
	for (int i = size - 3; i < size; i++) {
		if (!isdigit(field[i])) {
			std::cout << "\nError: invalid format!" << std::endl;
			std::cout << "For course number (" << field << ") on line " << lineNumber << std::endl;
			std::cout << "** Last 3 characters must be numeric." << std::endl;
			std::cout << std::endl;
			
			return false;
		}
	}
	return true;
}



/**
 * This function validates each prerequisite
 * each prerequisite must be a valid course
 * @param string prerequisite
 * @param vector string of course numbers
 * @param int line counter
 * @return true or false
 */
bool BinaryTree::validatePrerequisites(std::string pre, std::vector<std::string> list, int lineNumber) 
{
	//VALIDATE PREREQUISITES: 

	//1. prerequisite must be a valid course.
	if (std::find(list.begin(), list.end(), pre) == list.end()) {
		std::cout << "\nError: invalid prerequisite ( " << pre << " ) on line # " << lineNumber << std::endl;
		std::cout << "** prerequisite must be a valid course." << std::endl;
		std::cout << std::endl;
		
		return false;
	}
}



/**
 * This function loads the course data from file into a tree data structure.
 * @param path of the file as string
 * @param ifstream file
 */
 BinaryTree:: TreeNode* BinaryTree::loadCourseData(std::string filePath, std::ifstream &myFile)
{

	//declare line counter
	int lineCounter = 0;

	//declare string variable to read from file
	std::string fileLine;

	//declare a tracking variable
	bool dataLoaded = true;

	//declare a vector to hold course numbers
	std::vector<std::string>numList;
	
	

	//verify if file is open
	std::cout << "\nOpening file... " << std::endl;
	if (myFile.is_open()) {

		std::cout << "File ( " << filePath << " ) opened successfully." << "\n\nLoading data..." << std::endl;

		//while file is open
		while (myFile.good()) {

			//read each line
			std::getline(myFile, fileLine);

			//start the counter
			lineCounter++;

			//load file data into the tree
			Course co = parseData(fileLine, lineCounter);
			buildTree(root, co);

			//populate vector
			numList.push_back(parseData(fileLine, lineCounter).courseNumber);
			
			//VALIDATION: validate prerequisites 
			for (std::string pre : co.prerequisites) {
				if (!validatePrerequisites(pre, numList, lineCounter)) {

					//set data loaded to false.
					dataLoaded = false;
					
					//throw exception
					throw std::invalid_argument("update the data on your file and try again!\n");

				}
			}
			
			
		}

		//close file
		myFile.close();

	}
	else {
		throw std::exception("Unable to open file, check file path and try again!\n");
	}
		
	//notify user about data loading status
	if (dataLoaded) {
		std::cout << "Your file data has been loaded into a tree data structure." << std::endl;
	}

	return root;
}



 /**
  * This function searches for a single course in the tree data structure
  * by course number
  * @param node
  * @param string course number
  */
 BinaryTree::TreeNode* BinaryTree::findACourse(TreeNode*& node, std::string courseNumber)
 {

	 //make string case insensitive for user input
	 transform(courseNumber.begin(), courseNumber.end(), courseNumber.begin(), ::toupper);
	


	 //if the tree has data,
	 if (node != nullptr) {

		 //if target course number is equal to node course number. course found
		 if (courseNumber == node->course.courseNumber) {

			 //print course info
			 printCourseInfo(node);

			 //return course.
			 return node;
		 }

		 //else if target course number is smaller than node course number
		 else if (courseNumber < node->course.courseNumber) {

			 //recurse to the left
			 return findACourse(node->left, courseNumber);
		 }

		 //else if target course is greater than node course number
		 else if (courseNumber > node->course.courseNumber) {

			 //recurse to the right
			 return findACourse(node->right, courseNumber);
		 }
	 }
	 //if not found, notify user
	 else {
		 std::cout << "\nCourse " << courseNumber << " not found." << std::endl;
		 std::cout << "Check your input and try again!" << std::endl;
		 return node;
	 }
 }



 /**
 * This function prints the course node data
 * @param node
 */
void BinaryTree:: printCourseInfo(TreeNode* &node) {

	
	//print  node data
	std::cout << std::endl;
	std::cout  << node->course.courseNumber << ", ";
	std::cout  << node->course.courseTitle << std::endl;
	if (node->course.prerequisites.size() > 0) {
		std::cout << "Prerequisites: ";
		for (std::string pre : node->course.prerequisites) {
			std::cout << pre << ", ";
		}
		std::cout << "\n";
	}

}



/**
 * This function prints all courses in alphanumeric order
 * by traversing the tree in in-order.
 * @param node
 * @param courseData
 */
void BinaryTree::printAlphanumericOrderedCourses(TreeNode*& node, const Course& co)
{

	// if root node is not empty
	if (node != nullptr) {

		//recurse to the left : in-order traversal
		printAlphanumericOrderedCourses(node->left, co);

		//print tree data
		std::cout << node->course.courseNumber << ", ";
		std::cout << node->course.courseTitle << std::endl;


		//recurse to the right
		printAlphanumericOrderedCourses(node->right, co);
	}

}







int main() {

	//create BinaryTree class object
	BinaryTree bts;

	//create Course object
	BinaryTree::Course course;
	
	//create TreeNode Object
	BinaryTree::TreeNode* root = nullptr;
	
	// variable to hold file path
	std::string filePath;

	//declare ifstream to open file
	std::ifstream myFile;

	//declare choice input
	int choice = 0;
	
	//start menu
	std::cout << "\nWelcome to the course planner." << std::endl;
	while (choice != 4) {
		
		std::cout << "\n  1. Load Data Structure" << std::endl;
		std::cout << "  2. Print Course List" << std::endl;
		std::cout << "  3. Print Course" << std::endl;
		std::cout << "  4. Exit" << std::endl;

		std::cout << "\nWhat would you like to do?  ";
		std::cin >> choice;
		

		//switch statment to process user input
		switch (choice) {


		case 1:

			//ask user to enter file to read
			std::cout << "\nEnter the file path  ";
			std::cin >> filePath;

			//catch exceptions from data validation
			try {
				//stream-open file
				myFile.open(filePath.c_str());


				//load data into the tree data structure
				root = bts.loadCourseData(filePath, myFile);
			}
			catch (std::invalid_argument& e) {
				std::cerr << e.what() << std::endl;
				return 0;
			}
			catch (std::exception& b) {
				std::cerr << b.what() << std::endl;
			}

			break;

		

		case 2:

			//if the data has been loaded to the tree, print the tree data
			if (root != nullptr) {

				//print header message
				std::cout << "Here is a sample schedule: \n" << std::endl;

				//while file is reading
				while (myFile) {

					//print tree data in alphanumirc order.
					bts.printAlphanumericOrderedCourses(root, course);

					//increment node count
					root->count++;

					break;
				}
			}
			//else: if data hasn't been loaded to the tree yet. display error.
			else {
				std::cout << "\nError: Data structure is empty. Load data first." << std::endl;
			}

			break;



		case 3:
			
			//if data hasn't been loaded to the tree yet. display error.
			if (root == nullptr) {
				std::cout << "\nError: Data structure is empty. Load data first." << std::endl;
			}
			//else: ask user to enter course number to search
			else {
				std::string cNumber;
				std::cout << "\nWhat course would you like to know about?" << std::endl;
				std::cout << "Enter the course number: ";
				std::cin >> cNumber;

				//search course
				bts.findACourse(root, cNumber);	
			}
			break;



		case 4:
			//exit program
			std::cout << "\nThank you for using the course planner ! " << std::endl;
			break;

		

		default:
			std::cout << "\nInvalid input. Please enter a valid option." << std::endl;
			break;
		}

	}
	std::cout << "Good bye." << std::endl;

	

	return 0;
}
