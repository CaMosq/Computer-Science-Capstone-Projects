/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the game over. This scene creates  the game over logic to remove the player
 * inventory and data from the game and start a new game session if requested.
 * */

import {GameObjects, Scene} from "phaser";
import {SharedUI} from "./sharedUI";
import {GameStateService} from "../service/game-state.service";

export class GameOver extends Scene {

  //////-----variables
  background: GameObjects.Image | undefined;
  logo: GameObjects.Image | undefined;
  title: GameObjects.Text | undefined;

  //////-----constructor
  constructor(private  gameState: GameStateService) {
    super('gameOver');
    this.gameState = gameState;
  }


  create(){

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForGameScene();

    /////////////--------create scene logo and background
    this.background = this.add.image(400, 300, 'menu-background');
    this.logo = this.add.image(400, 150, 'logo');

    /////////////------------create title for the menu
    this.title = this.add.text(400, 350, 'Game Over', {
      fontFamily: 'Arial Black', fontSize: 70, color: '#ffffff',
      stroke: '#000000', strokeThickness: 8,
      align: 'center'
    }).setOrigin(0.5).setDepth(10);

    /////////////------------create play again button and callback
    sceneUI.buttonBackground(this, 300, 425);//call function
    sceneUI.buttonText(this, 400, 450, 'Play Again', ()=> { //call function
      this.gameState.clearInventory();
      this.gameState.resetGameState();
      this.scene.stop('gameOver');
      this.scene.start('game')
    });


    /////////////------------create exit game text and callback
    sceneUI.buttonText(this, 400, 530, 'Exit Game', () => {
      this.gameState.clearInventory();
      this.gameState.resetGameState();
      this.scene.stop('gameOver');
      this.scene.remove('gameOver');
      this.scene.start('mainMenu')
    })
  }
}
