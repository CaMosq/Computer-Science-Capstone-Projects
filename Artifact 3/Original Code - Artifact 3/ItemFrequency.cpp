
#include "ItemFrequency.h"
#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <map>
using namespace std;




//constructor
ItemFrequency::ItemFrequency(std::string filename, std::map <std::string, int>& mMap ) : fileToRead(filename), itemMap(mMap) {
	
	//open file to read
	ReadInputFile(fileToRead);
	
	//create backup file in the beginning of the program
	SaveBackupFile();
}


//function to read input file
int ItemFrequency::ReadInputFile(string& fileToRead) const {
	
	//variables
	ifstream inFileStream;
	string productName;

	//open file to read
	inFileStream.open(fileToRead);

	
	//verify if file opened
	if (!inFileStream.is_open()) {
		cout << "File to read couldn't be opened." << endl;
		return 1;
	}
	else {
		cout << "input file opened successfully." << endl;
	}

	//read file untill the end of it and
	// increment count for item frequency in map
	while (!inFileStream.eof()) {
		inFileStream >> productName;
		++itemMap[productName];
	}

	//close file to read
	inFileStream.close();

	return 0;
}


//function to write to a output file
int ItemFrequency::WriteToFile(string& fileToWrite) const {
	
	//variables
	ofstream outFileStream;
	string productName;

	//open file for writing
	outFileStream.open(fileToWrite);

	//verify if writing file is open
	if (!outFileStream.is_open()) {
		cout << "Back up file could not be opened" << endl;
		return 1;
	}

	//write to the file from the map
	for (const auto& pair : itemMap) {
		outFileStream << pair.first << ":" << pair.second << endl;
	}
	if (itemMap.empty()) {
		cout << "couldn't write to file" << endl;
	}
	else {
		cout << "Backup file created successfully" << endl;

	}

	//close output file
	outFileStream.close();

	return 0;
}


//function to search item frequency
void ItemFrequency::SearchProduct(string& productName) {
	
	//variables
	map <string, int >::iterator it; //map iterator

	it = itemMap.find(productName);
	if (it != itemMap.end()) {
		cout << endl << "Found: " << productName << endl;
		cout << "Frequency: " << it->second << endl;
	}
	else {
		cout << "Product not found." << endl;
		cout << "search again using plural/singular form for better search" << endl;
	}
	cout << "------  --------" << endl;

}


//function to print list with all items and frequency
void ItemFrequency::PrintAllItemsAndFrequency(char special) {
	
	cout << endl << "Item    Frequency" << endl << endl;
	for (const auto& pair : itemMap) {
		string symbol(pair.second, special);
		if (special == ' ') {
			cout << left << setw(12) << pair.first << "   " << pair.second << endl;
		}
		else {
			cout << left << setw(12) << pair.first << "   " << symbol << endl;
		}
	}
}


//function to print histogram of frequency
void ItemFrequency::PrintHistogram() {

	if (!itemMap.empty()) {
		cout << "************* - H I S T O G R A M - *************" << endl << endl;

		for (const auto& item : itemMap) {

			cout << left << setw(15) << item.first << " | ";

			for (int i = 0; i < item.second; i++) {
				cout << "=";
			}
			cout << ">     ";
			cout << endl;
		}
	}
	else {
		cout << " nothing to display" << endl;
	}
	cout << endl << "********** - E N D    H I S T O G R A M - **********" << endl << endl;
}


//function to save data to a backup file (file.dat)
void ItemFrequency::SaveBackupFile() {
	string filename = "Frequency.dat";
	WriteToFile(filename);

}


