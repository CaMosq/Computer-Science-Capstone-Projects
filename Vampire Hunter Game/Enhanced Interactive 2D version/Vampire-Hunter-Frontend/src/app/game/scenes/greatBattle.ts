/**
 * Implementation of the Game scene
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: Phaser3 scene for the final battle. This scene creates the vampire and implements
 * the player and vampire moves during the fight.
 * */

import Phaser from "phaser";
import {GameStateService, PopUpType} from "../service/game-state.service";
import {SharedUI} from "./sharedUI";

export class GreatBattle extends Phaser.Scene {
  private vampire: Phaser.Physics.Arcade.Sprite | undefined;
  private player: Phaser.Physics.Arcade.Sprite | undefined;
  private cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
  private speed: number = 200; // Movement speed
  private gameState: GameStateService | undefined;
  private vampireHealth: number = 100;
  private inventory: any[] | undefined;
  playerDirection: string = '';
  swordHitCount: number = 0;
  bombText: Phaser.GameObjects.Text | undefined;

  constructor() {
    super('greatBattle');
  }

  //////////////------------ init method
  init(data: any) {
    this.gameState = data.gameState;

  }


  create() {

    /////////////------hide or show UI elements in this scene
    const sceneUI = this.scene.get('sharedUI') as SharedUI;
    sceneUI.showUIForGameScene();


    // Create the player and vampire sprites
    this.player = this.physics.add.sprite(400, 300, 'samurai');
    // @ts-ignore
    this.vampire = this.physics.add.sprite(600, 300, 'vampire-attack1').setScale(2);

    //set player direction for later use
    this.playerDirection = 'idle';

    //adjust player bounding box size (width, height)
    this.player.body?.setSize(80, 100);
    this.vampire.body?.setSize(80, 100);

    // Adjust the offset (x, y) to reposition the bounding box
    this.player.body?.setOffset(20, 8);
    // this.vampire.body?.setOffset(20, 80);

    // Enable physics on player and vampire
    this.player.setCollideWorldBounds(true);
    this.vampire.setCollideWorldBounds(true);

    // Add keyboard controls
    this.cursors = this.input.keyboard?.createCursorKeys();

    // Enable collision between player and vampire
    // @ts-ignore
    this.physics.add.collider(this.player, this.vampire, () => this.startBattle());//if collision, fight

    // Make vampire follow player after collision
    this.vampireFollowPlayer();
    this.vampire?.setVelocityX(-50);


  }

  // @ts-ignore
  update() {

    // Player movement logic ( animations)
    this.movePlayer();


    // Make vampire keep following the player
    if (this.vampire?.active && this.player?.active) {
      this.vampireFollowPlayer(); // Keep vampire following player
    }
  }

  ///////////--------method to start battle
  startBattle() {
    // @ts-ignore
    this.vampireFollowPlayer();
    this.throwStuffAtVampire();

  }


  ////////------method to keep the vampire following player
  vampireFollowPlayer() {
    // @ts-ignore
    this.physics.moveToObject(this.vampire, this.player, 100); // Vampire follows player

    // attack player
    // @ts-ignore
    if (this.vampireHealth > 0) {
      //if moving left
      if (this.playerDirection === 'left') {
        this.vampire?.anims.play('run-vampire', true);
        this.vampire?.setFlipX(true);
      }
      //if moving right
      else if (this.playerDirection === 'right') {
        this.vampire?.anims.play('run-vampire', true);
        this.vampire?.setFlipX(false);

      }
      //if moving up or down
      else if (this.playerDirection === 'down' || this.playerDirection === 'up') {
        this.vampire?.anims.play('attack1-vampire', true);
        this.gameState?.getInventory().subscribe(item => {
          this.inventory = item;
          // @ts-ignore
          if (this.inventory.length < 5) { //if not enough items
            // @ts-ignore
            this.throwBomb(this.player); //kill player
          }
        });
      }
      //if not moving
      else {
        this.vampire?.anims.play('attack2-vampire', true);
      }
    }
  }

  /////////-----------method to control the player moves with keyboard
  movePlayer() {
    // Stop any previous movement
    this.player?.setVelocity(0);

    // Move player based on keyboard input (cursors)
    //run to the left
    if (this.cursors?.left.isDown) { //if left cursor
      this.player?.setVelocityX(-this.speed);
      this.player?.anims.play('run-samurai', true); //run animation
      this.player?.setFlipX(true);//flip the sprite to face left
      this.playerDirection = 'left';

    }
    //run to the right
    else if (this.cursors?.right.isDown) { //if right cursor
      this.player?.setVelocityX(this.speed);
      this.player?.anims.play('run-samurai', true);
      this.player?.setFlipX(false);//deactivate left flip, ensures the sprite faces right
      this.playerDirection = 'right';
    }
    //jump
    else if (this.cursors?.up.isDown) { //cursor up
      this.player?.setVelocityY(-this.speed);
      this.player?.anims.play('run-samurai-upward', true);
      this.playerDirection = 'up';

    }
    //attack
    else if (this.cursors?.down.isDown) { //cursor down
      this.player?.setVelocityY(this.speed);
      this.player?.anims.play('attack-samurai', true);
      this.playerDirection = 'down';
      this.swordHitCount++;

    }
    //idle
    else {
      this.player?.anims.play('idle-samurai', true);
      this.playerDirection = 'idle';
    }
  }


  throwStuffAtVampire() {

    /////--------throw bomb
    //Press "B" on the keyboard to throw a bomb
    const bKey = this.input.keyboard?.addKey(Phaser.Input.Keyboard.KeyCodes.B);
    this.gameState?.getInventory().subscribe(item => {
      this.inventory = item;
      if (bKey?.isDown) {
        // @ts-ignore
        if (this.inventory.length > 5) { //if enough items
          // @ts-ignore
          this.throwBomb(this.vampire); //throw bomb to vampire
        // } else {
        //   // @ts-ignore
        //   this.bombText = this.add.text(130, 70, 'Bomb unavailable! You did not collect all item.', {
        //     fontFamily: 'Arial', fontSize:16, color: 'white'
        //   });

        }

      }
      // @ts-ignore
      if (this.swordHitCount >= 50 && this.inventory.length > 5) {
        // @ts-ignore
        this.throwBomb(this.vampire);
      }

    });


  }

  ///////-----------method to throw bombs to the vampire
  throwBomb(sprite: Phaser.GameObjects.Sprite) {
    // Create a bomb at the vampire's current position
    // @ts-ignore
    const bomb = this.physics.add.sprite(sprite.x, sprite.y, 'bomb');

    // Set bomb velocity towards the vampire
    // @ts-ignore
    this.physics.moveToObject(bomb, sprite, 300); // Speed is 300

    // Set up collision between the bomb and the vampire
    // @ts-ignore
    this.physics.add.collider(bomb, sprite, () => this.bombHitsTarget(bomb, sprite), null, this);

  }


  ///////------------- method to handle the effects after the bomb hits the vampire
  bombHitsTarget(bomb: Phaser.GameObjects.Sprite, target: Phaser.GameObjects.Sprite) {

    // Destroy the bomb after impact
    bomb.destroy();

    // Trigger hit effect
    target.setTint(0xff0000); // Vampire or player turns red when hit
    if(target === this.vampire) {
      target.anims.play('hit-vampire', true);
    }

    // Reset the tint after a delay (to simulate hit recovery)
    this.time.delayedCall(200, () => {
      target.clearTint();
    }, [], this);

    // Reduce vampire's health
    if (target === this.vampire) {
      this.vampireHealth -= 10;
      console.log('vampireHealth', this.vampireHealth);
    } else {
      this.killPlayer();
    }

    // Check if vampire is dead
    if (this.vampireHealth <= 0) {
      this.killVampire(true);

    }
  }

  playRandomVampireAnimation() {
    const animations = ['attack1-vampire', 'run-vampire', 'attack2-vampire',]; // Array of animation keys
    const randomAnim = Phaser.Math.RND.pick(animations); // Pick a random animation
    this.vampire?.play(randomAnim); // Play the random animation
  }


  ///////---------------method that implements  animation for killing the vampire
  killVampire(success: boolean) {
    if (success) {
      // Vampire dies animation
      this.vampire?.anims.play('death-vampire', true);
      this.time.delayedCall(2000, () => {
        this.vampire?.destroy();  // Vampire is dead
        // console.log('Vampire defeated!');
        //show credits scene
      });
    } else {
      this.killPlayer();
    }
  }

  ////////---------------method that implements the animation for killing the player
  killPlayer() {
    // Player dies animation or game over screen
    this.player?.setTint(0xff0000); // Red tint to indicate death
    this.player?.anims.stop();  // Stop all animations
    // console.log('Player is dead!');
    this.time.delayedCall(3000, () => {
      this.gameState?.resetGameState();
      this.gameState?.clearInventory();
      this.scene.stop('greatBattle')
      this.scene.start('gameOver');
    })

  }

}
