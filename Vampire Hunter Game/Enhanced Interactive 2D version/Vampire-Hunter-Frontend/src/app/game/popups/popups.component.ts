/**
 * Angular component to implement a popup system
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Angular component Implements a popup system to provide real-time
 * feedback notifications to the player.
 * */

import {Component, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {GameStateService, PopUpType} from "../service/game-state.service";

@Component({
  selector: 'app-popups',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './popups.component.html',
  styleUrl: './popups.component.css'
})
export class PopupsComponent implements OnInit {
   collectedItem: any = null; // The item passed when collected
   itemDetails: any = null;   // The item passed for showing details
   houseDetails: any = null;
   visiblePopUp: PopUpType | null = null;

  constructor(private gameState: GameStateService) {}

  ngOnInit(): void {

    // Subscribe to the pop-up type to know which one to display
    this.gameState.popUpType$.subscribe(popUpType => {
      this.visiblePopUp = popUpType;
    });

    //get selected item
    this.gameState.selectedItem$.subscribe(selectedItem => {
      this.itemDetails = selectedItem;
    })

    //get collected item
    this.gameState.getCollectedItem().subscribe(collectedItem => {
      this.collectedItem = collectedItem;
    });



  }


  ////////////////-------------------- Great battle popup
  // method to check if the fight/collect pop-up is visible
  isFightOrCollectVisible(): boolean {
    return this.visiblePopUp === PopUpType.FightOrCollect;
  }
  //button 1
  continueBattle() {
    // @ts-ignore
    this.gameState.hidePopUp();
    this.gameState.switchToBattleScene()

  }
  //button 2
  scene:Phaser.Scene | undefined;
  collectItems() {
    this.gameState.hidePopUp();
  }


  ///////////////-------------------- Item details popup
  // method to check if the item details pop-up is visible
  isItemDetailsVisible(): boolean {
    return this.visiblePopUp === PopUpType.ItemDetails;
  }

  //Method to close the popup
  closePopup() {
    this.gameState.hidePopUp();
  }


  //////////////-------------------- Item collected popup
  //method to check if the inventory collected pop-up is visible
  isInventoryCollectedVisible(): boolean {
    return this.visiblePopUp === PopUpType.InventoryCollected;
  }


  //////////////-------------------- House detail popup
  //method to check if the inventory collected pop-up is visible
  isHouseDetailsVisible(): boolean {
    return this.visiblePopUp === PopUpType.HouseDetail;
  }

}
