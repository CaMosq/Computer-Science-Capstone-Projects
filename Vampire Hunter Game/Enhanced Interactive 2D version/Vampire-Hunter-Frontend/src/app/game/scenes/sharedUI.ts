/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Phaser Scene implements UI elements such as buttons styles, texts styles, shapes,
 * than can be use globally and define global logic for shared elements
 * */

import {Scene} from "phaser";
import {GameStateService} from "../service/game-state.service";
export class SharedUI extends Scene {

  constructor(private readonly gameStateService: GameStateService){
    super('sharedUI');
  }


  create(){

  }

  ////////////////////////// Method to create a grid ////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////
  createGrid(scene: Phaser.Scene, xP: number, yP: number, width: number, height: number,
             length: number, glow:boolean = false, cellColor: number, glowColor:number = 0) {
    let graphics = new Array(length);
    for (let i = 0; i < length; i++) {
      graphics[i] = scene.add.graphics();
      graphics[i].fillStyle(cellColor, 1);
      graphics[i].fillRoundedRect(xP, yP, width, height, 5);
      graphics[i].setScrollFactor(0, 0);
      graphics[i].setDepth(10);
      xP += (width + 20); //gap between every column

      // add blinking glow
      if(glow) {
        const sT = graphics[i].postFX.addGlow(0xffc800, 0, 0, false, 1, 6);
        scene.tweens.add({
          targets: sT,
          outerStrength: 4,
          yoyo: true,
          loop: -1,
          ease: 'sine.inout'
        });
      }
    }
  }

  ////////////////////////// Method to create a button-styled blinking rectangle /////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  buttonBackground(scene: Phaser.Scene, xP: number, yP: number) {
    let graphic = scene.add.graphics();
    graphic.fillStyle(0xffffff, 1);
    graphic.fillRoundedRect(xP, yP, 200, 50, 20);
    graphic.setDepth(10);
    const sT = graphic.postFX.addGlow(0x00ff00, 0, 0, false, 1);
    scene.tweens.add({
      targets: sT,
      outerStrength: 4,
      yoyo: true,
      loop: -1,
      ease: 'sine.inout'
    });
  }

  ///////////////////////////// Method to create text for blinking buttons with callback //////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  buttonText(scene: Phaser.Scene, xP: number, yP: number, text: string, clickAction: () => void) {
    const title = scene.add.text(xP, yP, text, {
      fontFamily: 'Arial Black', fontSize: 25, color: '#ffffff',
      stroke: '#000000', strokeThickness: 8,
      align: 'center'
    }).setOrigin(0.5).setDepth(100);

    //set menu item click
    title.setInteractive({ useHandCursor: true });
    title.on('pointerdown', (event: PointerEvent) => {clickAction()})

    //add visual effect to text
    // Add hover effects
    title.on('pointerover', () => {
      //change text color on hover
      title.setStyle({ color: '#08f838' }); // Change color to white on hover
      // Scale up the text when hovered
      scene.tweens.add({
        targets: title,
        scaleX: 1.1,
        scaleY: 1.1,
        duration: 200,
        ease: 'Power1'
      });
    });
    title.on('pointerout', () => {
      //change text color back  original color
      title.setStyle({ color: '#d4d4d4' });
      // Scale back to original size when pointer leaves
      scene.tweens.add({
        targets: title,
        scaleX: 1,
        scaleY: 1,
        duration: 200,
        ease: 'Power1'
      });
    });

  }

  ///////////////////////////// Method to create text for menus with comic sans font //////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  addMenuItem(scene: Phaser.Scene, xP:number, yP:number, itemName: string, clickAction: () => void) {
    const menuItem = scene.add.text(xP, yP, itemName, {
      fontFamily: 'Comic Sans MS',
      color: '#f8f6f6',
      fontSize: 30,
      // fontStyle: 'bold',
      stroke: '#000000', strokeThickness: 8,
      align: 'center',
    }).setOrigin(0.5).setDepth(20);

    //set menu item click
    menuItem.setInteractive({ useHandCursor: true });
    menuItem.on('pointerdown', (event: PointerEvent) => {
      clickAction()
    })

    //add visual effect to text
    // Add hover effects
    menuItem.on('pointerover', () => {
      //change text color on hover
      menuItem.setStyle({color: '#FFFFFF'}); // Change color to white on hover
      // Scale up the text when hovered
      scene.tweens.add({
        targets: menuItem,
        scaleX: 1.1,
        scaleY: 1.1,
        duration: 200,
        ease: 'Power1'
      });
    });

    menuItem.on('pointerout', () => {
      //change text color back  original color
      menuItem.setStyle({color: '#FDFFB5'});
      // Scale back to original size when pointer leaves
      scene.tweens.add({
        targets: menuItem,
        scaleX: 1,
        scaleY: 1,
        duration: 200,
        ease: 'Power1'
      });
    });
  }

  //////////////////////////// Methods to control UI on different scenes ///////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  showUIForGameScene(){
    // @ts-ignore
    document.getElementById('resume-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('exit-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('town-map-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('locations-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('inventory-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('instructions-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('top-panel').style.display = 'block';
    // @ts-ignore
    document.getElementById('left-panel').style.display = 'block';
    // @ts-ignore
    document.getElementById('right-panel').style.display = 'block';
  }

  showUIForIntroScenes(){
    // @ts-ignore
    document.getElementById('top-panel').style.display = 'none';
    // @ts-ignore
    document.getElementById('left-panel').style.display = 'none';
    // @ts-ignore
    document.getElementById('right-panel').style.display = 'none';
    // @ts-ignore
    document.getElementById('resume-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('exit-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('town-map-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('locations-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('inventory-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('instructions-btn').style.display = 'none';
  }

  showUIForChildScene(){
    // @ts-ignore
    document.getElementById('resume-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('exit-btn').style.display = 'block';
    // @ts-ignore
    document.getElementById('town-map-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('locations-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('inventory-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('instructions-btn').style.display = 'none';
    // @ts-ignore
    document.getElementById('left-panel').style.display = 'block';
  }

}
