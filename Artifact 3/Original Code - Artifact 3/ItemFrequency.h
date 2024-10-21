#pragma once

#ifndef ITEMFREQUENCY_H
#define ITEMFREQUENCY_H

#include <string>
#include <map>

class ItemFrequency
{
public:
	//constructor
	ItemFrequency(std::string filename,std::map <std::string, int>& mMap);

	//function to read input file
	int ReadInputFile(std::string& filename) const;

	//function to write to a output file (file.dat)
	int WriteToFile(std::string& filename) const;
	

	//function to search item frequency
	void SearchProduct(std::string& itemName);
	
	//function to print list with all items and frequency
	void PrintAllItemsAndFrequency(char special);
	
	//function to print histogram of frequency
	void PrintHistogram();



private:
	//variables
	std::map <std::string, int>& itemMap;
	std::string fileToRead;


	//function to save data to backup file
	void SaveBackupFile();

};

#endif

