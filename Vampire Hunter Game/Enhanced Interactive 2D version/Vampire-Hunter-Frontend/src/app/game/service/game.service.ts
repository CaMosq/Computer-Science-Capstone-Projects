/**
 * Angular component to communicate with backend
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Angular component Communicates with the Flask(Python) backend
 * through http client/server API requests.
 * */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Item} from "../model/item";
import {House} from "../model/house";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private baseUrl = 'http://127.0.0.1:5000'; // Flask API URL

  constructor(private http: HttpClient) { }


  //method to display the current location and player inventory
  getStatus(): Observable<any> {
    return this.http.get(`${this.baseUrl}/status`);
  }


  //method to get a list of all items
  getAllItems(): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.baseUrl}/items`);
  }

  // Method to get an item by name
  getItemByName(itemName: string): Observable<Item> {
    return this.http.get<Item>(`${this.baseUrl}/item/${itemName}`);
  }

  //method to get all houses
  getHouses(): Observable<House[]> {
    return this.http.get<House[]>(`${this.baseUrl}/houses`);
  }

  //Method to collect item, add it to inventory and remove it from house
  collectItem(houseName: string): Observable<Item> {
    return this.http.post<Item>(`${this.baseUrl}/collect-item/${houseName}`, {});
  }
}
