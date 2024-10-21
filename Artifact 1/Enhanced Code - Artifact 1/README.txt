Carmen Mosquera
CS 499 Computer Science Capstone
Category : One - Software Engineering and Design
Artifact: Vampire Hunter Game.


-------- Enhancements Summary.

For this category, I implemented the following enhancements to my text-based game:

- I integrated a front-end interface using Angular to move the game beyond simple text commands to an interactive, graphical user experience.

- I also incorporated Phaser 3 to create a dynamic village map, allowing players to visually navigate locations, interact with objects, and collect items.

- I added a Flask backend to manage game state and logic, including the handling of collected items and player actions. The communication between the front end and back end is managed through Angular services, to allow players to get real-time updates of their inventory and state.

- I also enhanced the game's item collection mechanism . I created a pop-up system where player get live notifications after they collect items, when they click an item to see its details, when they click a village location to see its history and information, and also when they arrive to the vampire mansion without the required items to win the battle. 

- I enhanced the structure  of the game with detailed location descriptions and additional decision-making scenarios, such as allowing players to choose between continuing the fight with the vampire or returning to collect more items.



------ How to Test the game

	1. Download the project files and extract the contents to a desired folder on your computer. The  enhanced code directory contains 2 folders  : Vampire-hunter-Backend and Vampire-Hunter-Frontend.

2. Install the dependencies for the back-end : 
	•	Create virtual environment:
			macOs/Linux: 
				$ cd Vampire-Hunter-Backen  
				$ python3 -m venv .venv

			Windows:       
				cd Vampire-Hunter-Backend
				py -3 -m venv .venv

	•	 Activate virtual environment:
			macOs/Linux:     . .venv/bin/activate
			Windows:         .venv\Scripts\activate

	•	Install Flask :  pip install Flask

	•	Install Cors:     pip install -U flask-core



3. run the back-end server : flask run


4. Install dependencies for the front-end:
	•	Install Angular CLI : npm install -g @angular/cli
	•	Install required dependencies: npm install

5. Run the back-end server : ng serve
	- open your browser and go to: http://localhost:4200.
	- The game interface should load with the main menu.
	- Start playing the game by moving the character through the village map and interacting with various locations to collect items.
	- Make sure that:
		- The player can navigate the village map using the arrow keys.
		- Items can be collected, and a pop-up appears with item details.
		- The collected items are properly updated in the player’s inventory.
		- When the player reaches the final scene, a decision to continue fighting the vampire or return to collect more items is presented.


