/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the main game. This function preloads all the game assets (images, sprites, jsons files...)
 * */
import {Scene} from 'phaser';

export class Preloader extends Scene {

  constructor() {
    super('Preloader');
  }

  init() {
    //  We loaded this image in our Boot Scene, so we can display it here
    this.add.image(512, 384, 'background');

    //  A simple progress bar. This is the outline of the bar.
    this.add.rectangle(512, 384, 468, 32).setStrokeStyle(1, 0xffffff);

    //  This is the progress bar itself. It will increase in size from the left based on the % of progress.
    const bar = this.add.rectangle(512 - 230, 384, 4, 28, 0xffffff);

    //  Use the 'progress' event emitted by the LoaderPlugin to update the loading bar
    this.load.on('progress', (progress: number) => {

      //  Update the progress bar (our bar is 464px wide, so 100% = 464px)
      bar.width = 4 + (460 * progress);

    });
  }

  preload() {
    //load the assets for the game
    this.load.setPath('../assets');

    //logo and background
    this.load.image('logo', 'images/logo.png');
    this.load.image('menu-background', 'images/menu-background.jpg');


    //player
    this.load.atlas('samurai', 'hunter/samurai/samurai-sheet.png', 'hunter/samurai/samurai-sheet.json');

    //vampire
    this.load.spritesheet('vampire', 'vampire/vampire.png', {frameWidth: 26, frameHeight: 26});
    this.load.spritesheet('vampire-attack1', 'vampire/Attack1.png', {frameWidth: 250, frameHeight: 250});
    this.load.spritesheet('vampire-attack2', 'vampire/Attack2.png', {frameWidth: 250, frameHeight: 250});
    this.load.spritesheet('vampire-death', 'vampire/Death.png', {frameWidth: 250, frameHeight: 250});
    this.load.spritesheet('vampire-run', 'vampire/Run.png', {frameWidth: 250, frameHeight: 250});
    this.load.spritesheet('vampire-take-hit', 'vampire/Take-hit.png', {frameWidth: 250, frameHeight: 250});

    //bomb
    this.load.image('bomb', 'images/bomb.png');


    //tileMap
    this.load.tilemapTiledJSON('tileMap', 'village/villge-town.json');//town

    //tiles
    this.load.image('tilesetTown', 'tiles/village-town-32-tileset.png')//village-town tileset image

    // map of the villa
    this.load.image('villageMap', 'village/villge-town.png')

    //town-items to kill vampire 48 pixels
    this.load.image('townMapItem', 'town-items/Town-map.png');
    this.load.image('ancientBook', 'town-items/Ancient-book.png');
    this.load.image('crucifix', 'town-items/Crucifix.png');
    this.load.image('garlic', 'town-items/Garlic.png');
    this.load.image('healthPotion', 'town-items/Health-potion.png');
    this.load.image('holyWater', 'town-items/Holy-water.png');
    this.load.image('money', 'town-items/Money.png');
    this.load.image('silverSword', 'town-items/Silver-sword.png');
    this.load.image('uvLight', 'town-items/UV-light.png');
    this.load.image('woodStack', 'town-items/Wood-stack.png');

    //town-items to kill vampire 80 pixels
    this.load.image('Town-map', 'town-items/80x80px/Town-map-80.png');
    this.load.image('Ancient-book', 'town-items/80x80px/Ancient-book-80.png');
    this.load.image('Crucifix', 'town-items/80x80px/Crucifix-80.png');
    this.load.image('Garlic', 'town-items/80x80px/Garlic-80.png');
    this.load.image('Health-potion', 'town-items/80x80px/Health-potion-80.png');
    this.load.image('Holy-water', 'town-items/80x80px/Holy-water-80.png');
    this.load.image('Money', 'town-items/80x80px/Money-80.png');
    this.load.image('Silver-sword', 'town-items/80x80px/Silver-sword-80.png');
    this.load.image('UV-light', 'town-items/80x80px/UV-light-80.png');
    this.load.image('Wood-stack', 'town-items/80x80px/Wood-stack-80.png');


  }

  create() {
    //////////////////////////--------- global animations ------
    /////------player in the village
    ////////////---------- samurai animations
    //run
    this.anims.create({
      key: 'run-samurai',
      frames: this.anims.generateFrameNames('samurai', {
        start: 0,
        end: 7,
        prefix: 'RUN_',
        suffix: '.png'
      }),
      frameRate: 10,
      repeat: -1,
    });
    this.anims.create({
      key: 'run-samurai-downward',
      frames: this.anims.generateFrameNames('samurai', {
        start:0,
        end: 4,
        prefix: 'JUMP_',
        suffix: '.png'
      }),
      frameRate: 10,
      repeat: -1,
    });

    this.anims.create({
      key: 'run-samurai-upward',
      frames: this.anims.generateFrameNames('samurai', {
        start:0,
        end: 4,
        prefix: 'JUMP_',
        suffix: '.png'
      }),
      frameRate: 10,
      repeat: -1,
    });
    //attack
    this.anims.create({
      key: 'attack-samurai',
      frames: this.anims.generateFrameNames('samurai', {
        start: 0,
        end: 4,
        prefix: 'ATTACK_',
        suffix: '.png'
      }),
      frameRate: 10,
      repeat: -1,
    });
    //idle
    this.anims.create({
      key: 'idle-samurai',
      frames: this.anims.generateFrameNames('samurai', {
        start: 0,
        end: 4,
        prefix: 'IDLE_',
        suffix: '.png'
      }),
      frameRate: 10,
      repeat: -1,
    });

    ////////-------- Vampire animations
    //attack1
    this.anims.create({
      key: 'attack1-vampire',
      frames: this.anims.generateFrameNames('vampire-attack1', {
        start: 0,
        end: 7,
      }),
      frameRate: 10,
      repeat: 20,
    });
    //attack2
    this.anims.create({
      key: 'attack2-vampire',
      frames: this.anims.generateFrameNames('vampire-attack2', {
        start: 0,
        end: 7,

      }),
      frameRate: 10,
      repeat: 20,
    });
    //death
    this.anims.create({
      key: 'death-vampire',
      frames: this.anims.generateFrameNames('vampire-death', {
        start: 0,
        end: 6,
      }),
      frameRate: 10,
      repeat: 0,
    });
    //run
    this.anims.create({
      key: 'run-vampire',
      frames: this.anims.generateFrameNames('vampire-run', {
        start: 0,
        end: 7,
      }),
      frameRate: 10,
      repeat: 20,
    });
    //take hit
    this.anims.create({
      key: 'hit-vampire',
      frames: this.anims.generateFrameNames('vampire-take-hit', {
        start: 0,
        end: 2,
      }),
      frameRate: 10,
      repeat: -1,
    });


    ///// start game
    this.scene.start('mainMenu'); //change to main menu or intro screen when the game is complete
  }





}
