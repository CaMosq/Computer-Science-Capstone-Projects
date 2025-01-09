/**
 * Implementation of the Game
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the main game. This scene boot the game,
 * and preload the background asset before starting the game.
 * */
import { Scene } from 'phaser';

export class Boot extends Scene
{
  constructor ()
  {
    super('boot');
  }

  preload () {
    //main menu background
    this.load.image('background', 'assets/images/menu-background.jpg');//intro background
    this.load.image('background2', 'assets/images/background.png');  }

  create () {
    this.scene.start('Preloader');
  }
}
