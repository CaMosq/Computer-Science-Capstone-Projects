/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the game inventory. This scene displays the player's inventory.
 * */

import {Scene} from "phaser";
import {GameStateService, PopUpType} from "../service/game-state.service";
import {SharedUI} from "./sharedUI";


export class Inventory extends Scene {

  ///////----variables
  titleText: Phaser.GameObjects.Text | undefined;
  private inventory: any[] | undefined;
  private gameState: GameStateService | undefined;

  //////--- constructor
  constructor() {
    super('inventory');

  }

  //////////////------------ init method
  init(data: any){
    this.gameState = data.gameState;
  }


  //////////////-------------create method
  create(){

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForChildScene();

    document.getElementById('resume-btn')?.addEventListener('click', ()=>{
      this.scene.stop('inventory');
      this.scene.wake('game',{ playerPosition: this.gameState?.getPlayerPosition() });
      this.gameState?.setCurrentScene('game');
    });
    document.getElementById('exit-btn')?.addEventListener('click', ()=>{
      //clear inventory and reset game state
      this.gameState?.clearInventory();
      this.gameState?.resetGameState();
      this.scene.stop('inventory');
      this.scene.start('mainMenu', {gameState: this.gameState});
    });

    //////////----------create background panel
    const graphic = this.add.graphics();
    graphic.fillStyle(0xffffff, 0.3);
    graphic.fillRoundedRect(200, 40, 400, 550, 10);


    /////////-------------create grid panel to hold the item details
    sceneUI.createGrid(this,260, 170, 80, 80, 3, true, 0xDFD4D4, 0xffc800); //row 1
    sceneUI.createGrid(this, 260, 270, 80, 80, 3, true, 0xDFD4D4, 0xffc800); // row 2
    sceneUI.createGrid(this, 260, 370, 80, 80, 3, true, 0xDFD4D4, 0xffc800); // row 3
    sceneUI.createGrid(this, 260, 470, 80, 80, 3, true, 0xDFD4D4, 0xffc800); // row 4

    ////////-----------create the title text
    this.titleText = this.add.text(280, 70, 'Inventory', {
      fontFamily: 'Arial Black',
      color: '#f8f6f6',
      fontSize: 42,
      fontStyle: 'bold',
      padding: {x: 10, y: 5},
    }).setScrollFactor(0);



    /////////////---------- Subscribe to the inventory data to populate images
    this.gameState?.getInventory().subscribe(currentInventory => {
      this.inventory = currentInventory;
      if(this.inventory.length > 0) {
        //create help text in the top-left corner
        this.titleText = this.add.text(0, 0, 'Click each item for details.', {
          color: '#f8f6f6',
          fontSize: 18,
          padding: {x: 10, y: 5},
        }).setScrollFactor(0);
      }
      else {
        //create different help text in the top-left corner
        this.titleText = this.add.text(0, 0, 'Your inventory is empty! Collected items will appear here.', {
          color: '#f8f6f6',
          fontSize: 18,
          padding: {x: 10, y: 5},
        }).setScrollFactor(0);
      }
      // console.log('data received: ', this.inventory);
      this.itemsGridImages(300, 210, this.inventory);
    });

  }


  //////////////////////////////////////// add images or titles dynamically method /////////////////////////////////
  //Method to add images to the grid dynamically
  itemsGridImages(xP: number, yP: number, inventory: any[]) {
    const initialX = xP;  // Save the initial x position
    const colCount = 3;   // Number of columns
    const rowHeight = 100;  // Vertical space between rows
    const colWidth = 100;  // Horizontal space between columns

    for (let i = 0; i < inventory.length; i++) {

      // Create the image for the current item
      let img = this.add.image(xP, yP, inventory[i].image_key);

      // Set scroll factor and depth
      img.setScrollFactor(0, 0);
      img.setDepth(10);

      // Make the image interactive (clickable)
      img.setInteractive({ useHandCursor: true });

      // Add a click event listener for each image
      img.on('pointerdown', () => {
        // console.log(`Item clicked: ${inventory[i].name}`);

        // Pass the item details to the GameStateService
        this.gameState?.setSelectedItem(inventory[i]);
        this.gameState?.showPopUp(PopUpType.ItemDetails);
      });

      //add hover effects
      img.on('pointerover', () => {
        // Scale up the text when hovered
        this.tweens.add({
          targets: img,
          scaleX: 1.1,
          scaleY: 1.1,
          duration: 200,
          ease: 'Power1'
        });

      });
      img.on('pointerout', () => {
        // Scale back to original size when pointer leaves
        this.tweens.add({
          targets: img,
          scaleX: 1,
          scaleY: 1,
          duration: 200,
          ease: 'Power1'
        });
      });

      // Move to the next column
      xP += colWidth;

      // If we've placed the last item in a row, reset xP and move to the next row
      if ((i + 1) % colCount === 0) {
        xP = initialX;  // Reset x position
        yP += rowHeight;  // Move down to the next row
      }
    }
  }

}
