/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the game locations. This scene displays the locations in the village.
 * */
import {Scene} from "phaser";
import {SharedUI} from "./sharedUI";
import {GameStateService, PopUpType} from "../service/game-state.service";

export class Locations extends Scene {
  private gameState: GameStateService | undefined;

  constructor() {
    super('locations');
  }

  //////////////------------ init method
  init(data: any){
    this.gameState = data.gameState;
  }

  create(){

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForChildScene();

    document.getElementById('resume-btn')?.addEventListener('click', ()=>{
      this.scene.stop('locations');
      this.scene.wake('game', { playerPosition: this.gameState?.getPlayerPosition() });
      this.gameState?.setCurrentScene('game');

    });
    document.getElementById('exit-btn')?.addEventListener('click', ()=>{
      //clear inventory and reset game state
      this.gameState?.clearInventory();
      this.gameState?.resetGameState();
      this.scene.stop('locations');
      this.scene.start('mainMenu', {gameState: this.gameState});
    });

    /////////////------scene content
    //create background
    const background = this.add.graphics();
    background.fillStyle(0xD4D4D4, 1);
    background.fillRect(0, 0, 800, 600);


    //create title
    const title = this.add.text(300, 30, 'Locations', {
      fontFamily: 'Arial Black', fontSize: 42, color: 'black'
    });

    //create subtitle
    const subTitle = this.add.text(90, 90, 'Click on each location to see more details', {
      fontSize: 20, color: 'black'
    })


    //create grid with location names dynamically
    this.gameState?.getHouses().subscribe( houses => {
      this.populateGrid(100, 150, houses, 200, 80);

    })

  }

  //method to create a grid and populated it with objects from an array (items, locations etc..)
  populateGrid(xP: number, yP: number, objectList: any[], cellWidth: number, cellHeight: number) {
    const initialX = xP;  // Save the initial x position
    const colCount = 3;   // Number of columns


    for (let i = 0; i < objectList.length; i++) {
      //if the list has images
      if (objectList[i].image_key) {
        // populate grid with the image for the current object
        // @ts-ignore
       let img = this.add.image(xP, yP, objectList[i].image_key);

        // Set scroll factor and depth
        img.setScrollFactor(0, 0);
        img.setDepth(10);
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
        // Make the image interactive (clickable)
        img.setInteractive({useHandCursor: true});

        // Add a click event listener for each image
        img.on('pointerdown', () => {
          // console.log(`Item clicked: ${inventory[i].name}`);

          // Pass the item details to the GameStateService
          this.gameState?.setSelectedItem(objectList[i]);
          this.gameState?.showPopUp(PopUpType.HouseDetail);
        });
      }
      else {

        //add text instead of the image
        // @ts-ignore
        let name = this.add.text(xP, yP, objectList[i].name, {
          fontFamily: 'Arial',
          fontSize: 18,
          color: 'black',
          align: 'center',
          padding: {top: 5, right: 5, bottom: 5, left: 5},

        });

        //rectangle for the shadow
        let graphic2 = new Array(objectList.length);
        graphic2[i] = this.add.graphics();
        graphic2[i].fillStyle(0xcccccc, 1);
        graphic2[i].fillRoundedRect(xP-21, yP-21, cellWidth+10, cellHeight+10, 5);

        //rectangle for the item background
        let graphic = new Array(objectList.length);
        graphic[i] = this.add.graphics();
        graphic[i].fillStyle(0xffffff, 1);
        graphic[i].fillRoundedRect(xP-20, yP-20, cellWidth, cellHeight, 5);

        // Set scroll factor and depth
        name.setScrollFactor(0, 0);
        name.setDepth(10);

        //add hover effects
        name.on('pointerover', () => {
          // Scale up the text when hovered
          this.tweens.add({
            targets: name,
            scaleX: 1.1,
            scaleY: 1.1,
            duration: 200,
            ease: 'Power1'
          });

        });
        name.on('pointerout', () => {
          // Scale back to original size when pointer leaves
          this.tweens.add({
            targets: name,
            scaleX: 1,
            scaleY: 1,
            duration: 200,
            ease: 'Power1'
          });
        });
        // Make the text interactive (clickable)
        name.setInteractive({useHandCursor: true});

        // Add a click event listener for each cell
        name.on('pointerdown', () => {
          // console.log(`Item clicked: ${objectList[i].name}`);

          // Pass the item details to the GameStateService
          this.gameState?.setSelectedItem(objectList[i]);
          this.gameState?.showPopUp(PopUpType.HouseDetail);
        });
      }


      // Move to the next column
      xP += (cellWidth + 20 );

      // If we've placed the last item in a row, reset xP and move to the next row
      if ((i + 1) % colCount === 0) {
        xP = initialX;  // Reset x position
        yP += (cellHeight + 20);  // Move down to the next row
      }
    }
  }

}
