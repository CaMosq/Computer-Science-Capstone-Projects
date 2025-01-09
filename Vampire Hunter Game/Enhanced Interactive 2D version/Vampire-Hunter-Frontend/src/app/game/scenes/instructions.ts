/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the game instructions. This scene displays the instructions for the game
 * controllers and interface.
 * */

import {Scene} from "phaser";
import {GameStateService} from "../service/game-state.service";
import {SharedUI} from "./sharedUI";

export class Instructions extends Scene {
  private gameState: GameStateService | undefined;

  constructor() {
    super('instructions');
  }


  create(data:any) {

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForChildScene();

    this.gameState = data.gameState;
    document.getElementById('resume-btn')?.addEventListener('click', ()=>{
      this.scene.stop('instructions');
      this.scene.wake('game', { playerPosition: this.gameState?.getPlayerPosition() });
      this.gameState?.setCurrentScene('game');

    });
    document.getElementById('exit-btn')?.addEventListener('click', ()=>{
      //clear inventory and reset game state
      this.gameState?.clearInventory();
      this.gameState?.resetGameState();
      this.scene.stop('instructions');
      this.scene.start('mainMenu', {gameState: this.gameState});
    });


    /////////-------------- create background
    this.add.image(300, 100, 'background');
    const graphic = this.add.graphics();
    graphic.fillStyle(0x000000, 0.5);
    graphic.fillRect(0, 0, 800, 600);

    /////////-----------------create title
    const title = this.add.text(250, 30, 'Instructions', {
      fontFamily: 'Arial Black', fontSize: 42, color: 'white'
    });

    ////////////////////////---------------- create content
    ///////----objective
    this.createParagraph(100, 120, 'Game Objective','Search the village for all the items required to kill the vampire. Collect 8 items and head to the vampire mansion for the final battle.' );

    ///////----- controls
    this.createParagraph(100, 250, 'Controls', '- Use cursor keys to control the player: Up, Down, Left, Right.\n' +
      '- Press "B" to throw a bomb during battle.\n' +
      '- Use the [Down] control key to use the silver sword if available in your inventory\n'+
      '- Use the right panel to check inventory, view the map, or exit the game.\n' +
      '- Player status (name, location, item count, time, health) is shown in the status bar.\n' +
      '- In inventory, click each item to view its details.\n' +
      '- In the locations menu, click each location to learn more.\n' +
      '- Press "Exit Game" to lose all progress and collected items.',
    );

  }

  //method to create a style paragraph text with title and text
  createParagraph(xP:number, yP:number, titleText:string, descriptionText:string) {
    this.add.text(xP, yP, titleText, {
      fontFamily: 'Arial Black', fontSize: 20, color: 'white'
    })

    this.add.text(xP, yP + 30, descriptionText, {
      fontFamily: 'Arial', fontSize: 18, color: 'white',
      wordWrap: {width: 600}, lineSpacing: 10
    })

  }



}
