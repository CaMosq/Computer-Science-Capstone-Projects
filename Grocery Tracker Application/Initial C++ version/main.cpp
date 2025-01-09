/*
* Name: Carmen Mosquera
* Class: CS-210-H7884
* Date: April/14/2023
* program: App to track frequency of items purchased in grocey store.
*/

#include "ItemFrequency.h"
#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <map>
#include <cctype>
using namespace std;



//function to display menu to user
void displayMenu() {
	cout << endl;
	cout << "******* M A I N   M E N U *******" << endl;
	cout << "1 - Search item" << endl << endl;;
	cout << "2 - Print list with frequency of purchased items" << endl << endl;;
	cout << "3 - Print histogram with frequency of purchased items " << endl << endl;
	cout << "4 - Exit program" << endl << endl;
	cout << endl;
	cout << "Enter selection now: " << endl;
}


int main() {

	
	//variables
	int menuSelection;
	map<string, int > productMap;
	string fileToRead = "CS210_Project_Three_Input_File.txt";
	

	// create class object
	ItemFrequency itemFrequency(fileToRead, productMap);
	
	
	while (true) {

		// call function to Display menu to user
		displayMenu();

		// user input to select from menu
		cin >> menuSelection;
	
		// validate user input and take actions
		if (menuSelection == 1) {
			
			// ask user to input item name
			cout << "Enter a product name to search" << endl;
			string itemName;
			cin >> itemName;
			
			// capitalize first letter
			itemName[0] = toupper(itemName[0]);
	
			//search for product and output frequency
			itemFrequency.SearchProduct(itemName);
		}
		else if (menuSelection == 2) {
			
			// print list with all items purchased and frequency
			itemFrequency.PrintAllItemsAndFrequency(' ');
		}
		else if (menuSelection == 3) {
			
			// print histogram of the frequency of purchase per item
			itemFrequency.PrintHistogram();

			// print items name followed by asterisks representing frequency 
			itemFrequency.PrintAllItemsAndFrequency('*');
		}
		else if (menuSelection == 4) {

			// exit the program
			cout << "Program finished" << endl;
			exit(3);
		}
		else {
			
			//print invalid input message and prompt user to try again
			wcout << "Invalid input. Try again\n" << endl;
			cin.clear(); //clear error
			cin.ignore(123, '\n'); //ignore line.
		}
	}

	return 0;
}