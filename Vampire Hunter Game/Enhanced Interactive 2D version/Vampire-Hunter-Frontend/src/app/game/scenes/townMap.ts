/**
 * Implementation of the Game scenes
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the village map. This scene display the map of the villa
 * with all the key locations.
 * */

import {Scene} from "phaser";
import {GameStateService} from "../service/game-state.service";
import {SharedUI} from "./sharedUI";

export class TownMap extends Scene {
  private gameState: GameStateService | undefined;

  constructor() {
    super('townMap');
  }

  create(data: any) {

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForChildScene();
    this.gameState = data.gameState;
    document.getElementById('resume-btn')?.addEventListener('click', ()=>{
      this.scene.stop('townMap');
      this.scene.wake('game', { playerPosition: this.gameState?.getPlayerPosition() });
      this.gameState?.setCurrentScene('game');

    });
    document.getElementById('exit-btn')?.addEventListener('click', ()=>{
      //clear inventory and reset game state
      this.gameState?.clearInventory();
      this.gameState?.resetGameState();
      this.scene.stop('townMap');
      this.scene.start('mainMenu', {gameState: this.gameState});
    });

    //create background
    const graphic = this.add.graphics();
    graphic.fillStyle(0x40b080, 1);
    graphic.fillRect(0, 0, 800, 600);

    //create map
    this.add.image(400, 280, 'villageMap').setScale(0.9);
  }

}
