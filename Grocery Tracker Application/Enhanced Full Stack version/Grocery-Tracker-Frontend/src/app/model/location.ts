/**
 * Grocery tracker frontend - location model component
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This component serves as model for the location entity.
 */
export class LocationDTO {

  id: number;
  name: string;
  address: string;
  city: string;
  state: string;


  constructor(id: number, name: string, address: string, city: string, state: string) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.city = city;
    this.state = state;
  }
}
