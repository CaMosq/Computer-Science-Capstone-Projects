/**
 * Grocery tracker frontend - locationsComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to create the locations table and all location related UI.
 */
import {Component, OnInit} from '@angular/core';
import {LocationDTO} from '../model/location';
import {AppService} from '../service/app.service';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-locations',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './locations.component.html',
  styleUrl: './locations.component.css'
})
export class LocationsComponent implements OnInit {

  locations!: LocationDTO[];


  constructor(private appService: AppService) {
  }


  ngOnInit(): void {
    this.getLocations();
  }

  //get all locations
  getLocations(): void {
    this.appService.getAllLocations().subscribe((data: LocationDTO[]) => {
      this.locations = data;
    })
  }




}
