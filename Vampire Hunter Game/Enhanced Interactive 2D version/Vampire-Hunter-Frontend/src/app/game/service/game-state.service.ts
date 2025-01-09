/**
 * Angular component to manage the game states
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Angular component manages the game states and
 * serves as a bridge between Angular components and the Phaser scenes
 * */
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {Item} from "../model/item";

export enum PopUpType {
  FightOrCollect = 'fightOrCollect',
  ItemDetails = 'itemDetails',
  InventoryCollected = 'inventoryCollected',
  HouseDetail = 'HouseDetail'
}


@Injectable({
  providedIn: 'root',
})


export class GameStateService {
  //private
  private locationsSubject = new BehaviorSubject<any[]>([]); // Initialize with an empty array
  private housesSubject = new BehaviorSubject<any[]>([]); // Initialize with an empty array
  private itemSubject = new BehaviorSubject<string | null>(null); // Initialize item
  private inventorySubject = new BehaviorSubject<any[]>([]);
  private currentLocationSubject = new BehaviorSubject<string>('');
  private selectedItemSource = new BehaviorSubject<any>(null);
  private currentSceneSubject = new BehaviorSubject<string>('game');
  private playerPositionSubject = new BehaviorSubject<any>(null);
  private popUpVisible = new BehaviorSubject<boolean>(false);
  private popUpType = new BehaviorSubject<PopUpType | null>(null);
  private battleScene: Phaser.Scene | null = null;

  //public observable to track elements visibility
  public houses$: Observable<any[]> = this.housesSubject.asObservable();
  public item$ = this.itemSubject.asObservable();
  public inventory$:  Observable<any[]> = this.inventorySubject.asObservable();
  public currentLocation$ = this.currentLocationSubject.asObservable();
  public selectedItem$ = this.selectedItemSource.asObservable();
  public currentScene$ = this.currentSceneSubject.asObservable();
  public playerPosition$ = this.playerPositionSubject.asObservable();
  public popUpVisible$ = this.popUpVisible.asObservable();
  public popUpType$ = this.popUpType.asObservable();

  colItem: any = null;
  constructor() {}


  //////////////////=================== collected item
  setCollectedItem(collectedItem: any) {
    // @ts-ignore
    this.itemSubject.next(collectedItem);
    // console.log('item from state service: ', collectedItem);

  }
  getCollectedItem(){
    // console.log('item from get method:', this.item$);
    return this.item$;
  }

  // Method to update the selected item for the popup
  setSelectedItem(item: any) {
    this.selectedItemSource.next(item);
  }

  getSelectedItem(){
    return this.selectedItem$;

  }

  //////////////////========================= houses
  setHouses(houses: any[]) : void {
    this.housesSubject.next(houses);
  }
  getHouses(): Observable<any[]> {
    return this.houses$;
  }


  ////////////////////======================= inventory
  setInventory(inventory: Item[]) {
    this.inventorySubject.next(inventory);
    // console.log('inventory length: ', inventory.length);
  }
  getInventory(){
    return this.inventory$;
  }
  clearInventory() {
    let inventory: Item[] = []; // Reset inventory array
    this.inventorySubject.next(inventory); // Notify subscribers that the inventory is cleared
  }

  ////////////////////======================current house/location
  setCurrentLocation(location: string){
    this.currentLocationSubject.next(location);
  }
  getCurrentLocation(){
    return this.currentLocation$;
  }

  ////////////////////====================== game state
  resetGameState(){
    this.currentLocationSubject.next('');  // Reset current location
    this.inventorySubject.next([]);        // Clear the inventory
    this.itemSubject.next(null);           // Reset item
    this.playerPositionSubject.next(null); // Reset player position
    this.currentSceneSubject.next('');     // Clear current scene
    this.selectedItemSource.next(null);    // Clear selected item for pop-ups
    this.popUpType.next(null);             // Hide any active pop-up
  }

  ////////////////////======================  scene
  setCurrentScene(scene: string) {
    this.currentSceneSubject.next(scene);
  }
  getCurrentScene(): string {
    return this.currentSceneSubject.value;
  }
  // Method to set the main game scene (called once from the Phaser scene when the game starts)
  setBattleScene(scene: Phaser.Scene) {
    this.battleScene = scene;
  }
  switchToBattleScene() {
    if (this.battleScene) {
      this.battleScene.scene.stop('game')
      this.battleScene.scene.start('greatBattle', {gameState: this}); // Switch to main game scene
    }
  }


  ///////////////////========================= player
  savePlayerPosition(playerPosition: any) {
    this.playerPositionSubject.next(playerPosition);
    // console.log('playerPosition: ', playerPosition);
  }
  getPlayerPosition(): any {
    return this.playerPositionSubject.value;
  }
  resetPlayerPosition(): any {
    this.playerPositionSubject.next(null);
  }

  //////////////////========================Popups
  showPopUp(type: PopUpType) {
    this.popUpType.next(type);

  }

  hidePopUp() {
    this.popUpType.next(null);
  }

  getPopupType() {
    return this.popUpType$;
  }


}
