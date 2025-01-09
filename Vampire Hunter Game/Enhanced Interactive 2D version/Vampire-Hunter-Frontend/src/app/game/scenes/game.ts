/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the main game, this scene creates the village, the player and
 * the player's inventory. It also calls functions to verify player location, collect items, and
 * add them to the inventory.
 * */

import {Scene} from 'phaser';
import {GameStateService, PopUpType} from "../service/game-state.service";
import {Item} from "../model/item";
import {House} from "../model/house";
import {GameComponent} from "../game.component";
import {SharedUI} from "./sharedUI";

export class Game extends Scene {

  ///////////----------class variables
  camera: Phaser.Cameras.Scene2D.Camera | undefined;
  background: Phaser.GameObjects.Image | undefined;
  player: Phaser.Physics.Arcade.Sprite | undefined;
  cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
  aboveLayer: Phaser.Tilemaps.TilemapLayer | undefined;
  worldLayer: Phaser.Tilemaps.TilemapLayer | undefined;
  belowLayer: Phaser.Tilemaps.TilemapLayer | undefined;
  objectLayer: Phaser.Tilemaps.ObjectLayer | undefined;


  /////--------API variables
  locations: any[] = [];
  houses: House[] = [];
  currentLocation: string = '';
  inventory: Item[] = [];


  //////--------------items variables if not need it remove these variables.
  ancientBook: Phaser.GameObjects.Image | undefined;
  crucifix: Phaser.GameObjects.Image | undefined;
  garlic: Phaser.GameObjects.Image | undefined;
  healthPotion: Phaser.GameObjects.Image | undefined;
  holyWater: Phaser.GameObjects.Image | undefined;
  money: Phaser.GameObjects.Image | undefined;
  silverSword: Phaser.GameObjects.Image | undefined;
  townMapItem: Phaser.GameObjects.Image | undefined;
  uvLight: Phaser.GameObjects.Image | undefined;
  woodStack: Phaser.GameObjects.Image | undefined;


  ////---------------constructor
  constructor(private readonly gameStateService: GameStateService, private readonly gameComponent: GameComponent) {
    super('game');
    this.gameStateService = gameStateService;  // Store the service instance
    this.gameComponent = gameComponent;
  }



  //////////////////------------create method
  // @ts-ignore
  create(data) {

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForGameScene();

    //pass scene info to game state service
    this.gameStateService.setBattleScene(this);

    ///////////////--------------create the village
    const townMap = this.make.tilemap({key: 'tileMap'});//create town map

    //////---------create the tiles set for the village
    const townTileSet = townMap.addTilesetImage('town-village-tileset-32px', 'tilesetTown');

    ///////--------create the layers of the village
    // @ts-ignore
    this.belowLayer = townMap.createLayer('Below Player', townTileSet, 0, 0);//creating below payer layer
    // @ts-ignore
    this.worldLayer = townMap.createLayer('World', townTileSet, 0, 0);//creating world layer
    // @ts-ignore
    this.aboveLayer = townMap.createLayer('Above Player', townTileSet, 0, 0);//creating above payer layer


    ////////--------get starting location (village hall)
    const startingLocation = townMap.findObject('Objects', (obj) => obj.name.toLowerCase() === 'village hall');



    ////////////////------------create the player
    // @ts-ignore
    this.player = this.physics.add.sprite(startingLocation.x, startingLocation.y, 'samurai').setScale(0.7);
    // set player position after returning from another scene(to last position before switch)
    if (data.playerPosition) {
      this.player.setPosition(data.playerPosition.x, data.playerPosition.y);
    }
    //adjust player bounding box size (width, height)
    this.player.body?.setSize(80, 100);

    // Adjust the offset (x, y) to reposition the bounding box if needed
    this.player.body?.setOffset(20, 8);

    ////////////-----------define collision elements
    this.worldLayer?.setCollisionByProperty({collides: true});
    this.aboveLayer?.setDepth(10);

    //////////----------- Enable collision between player and the world layers
    // @ts-ignore
    this.physics.add.collider(this.player, this.worldLayer);


    ////////------------- request houses info to API through game state service.
    this.gameStateService.getHouses().subscribe((data: House[]) => {
      if (data.length > 0) {
        this.houses = data;
      }
    });

    ///////------------Get spawn pints from town tileMap
    // @ts-ignore
    this.objectLayer = townMap.createFromObjects('Objects');
    // @ts-ignore
    this.objectLayer.forEach(obj => {
      this.physics.world.enable(obj);
      this.addMenuOption(obj.body?.position.x, obj.body?.position.y, obj.name);

    });

    //////////--------------- collect items
    //subscribe to inventory to compare items later on
    this.gameStateService.getInventory().subscribe(currentInventory => {
      this.inventory = currentInventory;
    });


    ////////---------get the name of the house the player collided with.
    // @ts-ignore
    this.physics.add.collider(this.player, this.objectLayer, (player, object2) => {
      // @ts-ignore
      this.currentLocation = object2.name; //current location = spawn point name
      this.gameStateService.setCurrentLocation(this.currentLocation);

      //////--------check if that house is a valid house (has an item)
      //this checks if any house in the houses list has the same name as the spawn point colliding with the player.
      for (let i = 0; i < this.houses.length; i++) { //traverse houses list
        if (this.houses[i].name === this.currentLocation) { //if player collided with a valid house

          /////---- if you are not at the vampire mansion.
          if (this.houses[i].name.toLowerCase() != 'vampire mansion') {

            // @ts-ignore //disable body
            this.objectLayer.forEach(obj => { //get the body of all spawn point objects
              if (obj.name === this.currentLocation) { //get the body of the spawn point at the valid house
                this.physics.world.disable(obj); //disable body after collision
              }
            });
            //if item not in inventory, collect it---------
            if(!this.inventory.some(item => this.areItemsEqual(item, this.houses[i].item))) {
              this.gameComponent.collectItem(this.houses[i].name);// collect the item
              break; //the condition is met, break the loop.--------
            }
            break; //the condition is met, break outer loop
          }
          // if the house is the vampire mansion, don't collect item, kill vampire
          else {
            //great battle
            this.checkPlayerConditionToFight();



            //@ts-ignore //disable body
            this.objectLayer.forEach(obj => { //get the body of all spawn point objects
              if (obj.name === this.currentLocation) { //get the body of the spawn point at the valid house
                this.physics.world.disable(obj); //disable body after collision
              }
            });
            break;
          }
        }
      }
    });


    ///////-------- Add cursor keys input
    // @ts-ignore
    this.cursors = this.input.keyboard.createCursorKeys();

    //////----------create camera
    this.camera = this.cameras.main;
    this.camera.startFollow(this.player);
    this.camera.setBounds(0, 0, townMap.widthInPixels, townMap.heightInPixels);
    // this.camera.zoom = 1.3;


    ////////////----------- Add listener for UI buttons
    this.navigateToScene('town-map-btn', 'townMap', {gameState: this.gameStateService});
    this.navigateToScene('locations-btn', 'locations', {gameState: this.gameStateService});
    this.navigateToScene('inventory-btn', 'inventory', {gameState: this.gameStateService});
    this.navigateToScene('instructions-btn', 'instructions', {gameState: this.gameStateService});
    document.getElementById('exit-btn')?.addEventListener('click', () => {
      //clear inventory and reset game state
      this.gameStateService.clearInventory();
      this.gameStateService.resetGameState();
      this.scene.stop('game');
      this.scene.start('mainMenu', {gameState: this.gameStateService});
    });

  }

  /////////////////////////////-------------- update method
  // @ts-ignore
  update() {

    const speed = 160;
    // @ts-ignore

    // Stop any previous movement from the last frame
    // @ts-ignore
    this.player.body.setVelocity(0);

    // Moving right
    if (this.cursors?.right.isDown) {
      // @ts-ignore
      this.player.body.setVelocityX(speed);
      this.player?.anims.play('run-samurai', true);
      this.player?.setFlipX(false); // Ensure the sprite faces right
    }
    // Moving left
    else if (this.cursors?.left.isDown) {
      // @ts-ignore
      this.player.body.setVelocityX(-speed);
      this.player?.anims.play('run-samurai', true);
      this.player?.setFlipX(true); // Flip the sprite to face left
    }
    //moving upward
    else if (this.cursors?.up.isDown) {
      // @ts-ignore
      this.player.body.setVelocityY(-speed);
      this.player?.anims.play('run-samurai-upward', true);
    }
    //moving downward
    else if (this.cursors?.down.isDown) {
      // @ts-ignore
      this.player.body.setVelocityY(speed);
      this.player?.anims.play('idle-samurai', true);

    } else {
      this.player?.anims.play('idle-samurai', true);
    }

    // Make sure player stays on the ground and gravity affects it
    // @ts-ignore
    this.player.body.setVelocityY(this.player.body.velocity.y); // Preserve Y velocity (for jumping, falling, etc.)
  }


  ///////////////////////////-----------------navigate to other scenes method
  //function to navigate to other scene using right panel buttons
  navigateToScene(btnName: string, sceneName: string, p: any = null) {
      document.getElementById(btnName)?.addEventListener('click', () => {
        const playerPosition = { x: this.player?.x, y: this.player?.y };
        this.gameStateService.savePlayerPosition(playerPosition);  // Save position in service

        if (this.scene.isActive('game')) {
          this.scene.switch(sceneName, p);
        }
        this.gameStateService.setCurrentScene(sceneName);
      });

  }

  /////////////////////////------------------- add menu option method
  // Helper method to add text objects to houses
  addMenuOption(cX: number | undefined, cY: number | undefined, text: string) {
    if (cX != null) {
      if (cY != null) {
        this.add.text(cX, cY, text, {
          fontFamily: 'Comic Sans MS',
          color: '#FDFFB5',
          fontSize: 14,
          fontStyle: 'bold',
          stroke: '#000000', strokeThickness: 6,
          align: 'center'
        });
      }
    }
  }


  /////////////////////////--------------------compare two object for equity method
  // Function to compare two items based on their properties
  areItemsEqual(item1: Item, item2: Item): boolean {
    return (
      item1.name === item2.name &&
      item1.description === item2.description &&
      item1.image_key === item2.image_key
    );
  }
  checkPlayerConditionToFight(){
    // @ts-ignore -- Check if the player has all the required items
    const hasAllItems = this.checkInventoryForItems(this.inventory);

    // @ts-ignore. -- Check if the player has the silver sword
    // const hasSilverSword = this.inventory.some(item => item.name === 'Silver Sword');

    //if the player has all the items, start the battle
    if (hasAllItems) {
      this.scene.switch('greatBattle', {gameState: this.gameStateService});
    }
      //if he does not have all items
    // offer option to return to the villa and collect more items, or to go to battle
    else {
      //show dialog
      this.gameStateService.showPopUp(PopUpType.FightOrCollect);
    }


  }
  ////////-------------method to check if inventory has all the required items
  checkInventoryForItems(inventory: any[]): boolean {
    const requiredItems = ['Holy Water', 'Crucifix', 'Ancient Book', 'Garlic', 'Wooden Stake', 'Silver Sword', 'UV Light'];
    return requiredItems.every(item => inventory.some(invItem => invItem.name === item));
  }

}
