/**
 * Implementation of the main menu scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the main menu. This scene creates the game main menu that
 * takes the user to the different game screens.
 * */

import {GameObjects, Scene} from 'phaser';
import {SharedUI} from "./sharedUI";
import {GameStateService} from "../service/game-state.service";

export class MainMenu extends Scene {

  //////-------class variables
  background: GameObjects.Image | undefined;
  logo: GameObjects.Image | undefined;
  title: GameObjects.Text | undefined;


  //////--------constructor
  constructor(private  gameState: GameStateService) {
    super('mainMenu');
    this.gameState = gameState;
  }


  ////-----create method
  create(data:any) {

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForIntroScenes();
    this.gameState = data.gameState;

    /////////////--------create scene logo and background
    this.background = this.add.image(400, 300, 'menu-background');
    this.logo = this.add.image(400, 150, 'logo');

    /////////////------------create title for the menu
    this.title = this.add.text(400, 300, 'Main Menu', {
      fontFamily: 'Arial Black', fontSize: 45, color: '#ffffff',
      stroke: '#000000', strokeThickness: 8,
      align: 'center'
    }).setOrigin(0.5).setDepth(100);

    ///////////-------------create menu items and
    sceneUI.buttonBackground(this, 280, 375);//background for button
    sceneUI.buttonText(this, 380, 400, 'Play', () => {
      this.scene.start('game');
    })

    //options
    sceneUI.addMenuItem(this, 380, 470, 'Options', () => {
      // this.scene.start('instructions');
    });
    //credits
    sceneUI.addMenuItem(this, 380, 530, 'Credits', () => {
      // this.scene.start('gameOver')
    });
  }
}
