/**
 * Angular component to implements API functions
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Angular component Communicates the game services component to implements
 * the methods or functions need it in the game such as get locations, get items, collect items...
 * */

import {Component, OnDestroy, OnInit} from '@angular/core';
import Phaser, {Scene} from "phaser";
import {Boot} from "./scenes/boot";
import {Preloader} from "./scenes/preloaders";
import {MainMenu} from "./scenes/mainMenu";
import {Game} from "./scenes/game";
import {GameService} from "./service/game.service";
import {GameStateService, PopUpType} from "./service/game-state.service";
import {Inventory} from "./scenes/inventory";
import {NgForOf, NgIf} from "@angular/common";
import {Item} from "./model/item";
import {FormsModule} from "@angular/forms";
import {PopupsComponent} from "./popups/popups.component";
import {Locations} from "./scenes/locations";
import {GameOver} from "./scenes/gameOver";
import {SharedUI} from "./scenes/sharedUI";
import {GreatBattle} from "./scenes/greatBattle";
import {Instructions} from "./scenes/instructions";
import {TownMap} from "./scenes/townMap";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    PopupsComponent
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent implements OnInit, OnDestroy {

  game: Phaser.Game | undefined;
  inventory: Item[] = [];
  currentLocation: string = '';

  //popups
  collectedItem: any = null;  // This will hold the collected item details
  selectedItem: any = null; //This will hold the selected item details


  constructor(private gameService: GameService, protected gameState: GameStateService) {}

  ngOnInit(): void {
    // this.getLocations();
    this.getStatus();
    this.getHouses();

    //subscribe to inventory
    this.gameState.getInventory().subscribe((items: Item[]) => {
      this.inventory = items;
      // console.log('testing items: ', this.inventory);
    })

    //get current location
    this.gameState.getCurrentLocation().subscribe(location => {
      this.currentLocation = location;
    })

    // Subscribe to selected item for left sidebar item details popup
    this.gameState.getSelectedItem().subscribe(selectedItem => {
      // console.log('Selected item for details:', selectedItem);
      this.selectedItem = selectedItem;
    });

    //set UI for scene: make sure it refreshes when switching back from other scenes
    this.gameState.currentScene$.subscribe(scene => {
      const sharedUI = this.game?.scene.keys['sharedUI'] as SharedUI;
      if(scene === 'game'){
        sharedUI?.showUIForGameScene();
      }
      else if(scene === 'gameOver' || scene === 'mainMenu'){
        sharedUI?.showUIForIntroScenes();
      }
      else {
        sharedUI?.showUIForChildScene();
      }
    })


    const config: Phaser.Types.Core.GameConfig = {
      type: Phaser.AUTO,
      width: 800,
      height: 600,
      parent: 'game-container', // Match the ID in the template
      scene: [
        Boot,
        Preloader,
        MainMenu,
        new Game(this.gameState, this),
        Inventory,
        Locations,
        GameOver,
        SharedUI,
        GreatBattle,
        Instructions,
        TownMap
      ],
      physics: {
        default: 'arcade',
        arcade: {
          gravity: {y: 0, x:0},
          debug: false,
        },
      },
    };
    this.game = new Phaser.Game(config);
  }

  ngOnDestroy(): void {
    // Clean up the game instance when the component is destroyed
    if (this.game) {
      this.game.destroy(true);
    }
  }

  sendSelectedItem(item: Item): void {
    this.gameState.setSelectedItem(item);
    this.gameState.showPopUp(PopUpType.ItemDetails);
  }

  /**
   * Method to collect an item when player arrives to a location
   * This method call API to get the location item
   * and communicates with game state to set the collected items in the game
   * it also calls the pop-up component to display the corresponding pop-up
   */
  collectItem(houseName: string) {
    this.gameService.collectItem(houseName).subscribe(response => {
      if(response) {
        const collectedItem = response;
        this.getStatus(); // Update inventory
        // console.log("Collected item received in gameComponent.ts", collectedItem);

        //pass collected item to game state service
        this.gameState.setCollectedItem(collectedItem);

        //show popup
         this.collectedItem = collectedItem;
         this.gameState.showPopUp(PopUpType.InventoryCollected);
      }
    });
  }


  /**
   * Method to get the inventory from backend
   * This method call API to get the player inventory
   * and communicates with game state to set the inventory in the game
   */
  getStatus() {
    this.gameService.getStatus().subscribe(response => {
      if (response.inventory) {
        const inventory = response.inventory;
        this.gameState.setInventory(inventory);//pass inventory to game state services
      }
    });

  }


  /**
   * Method to get all the valid houses of the town
   * This method call API to get the locations of the villa
   * and communicates with game state to set the locations in the game
   */
  getHouses(){
    this.gameService.getHouses().subscribe(
      (response: any) => {
        const houses = response.houses; // Ensure you're assigning the 'houses' array from the response

        this.gameState.setHouses(houses);
      },
      (error) => {
        console.error('Error fetching houses:', error);
      }
    );
  }

  protected readonly PopUpType = PopUpType;
}
