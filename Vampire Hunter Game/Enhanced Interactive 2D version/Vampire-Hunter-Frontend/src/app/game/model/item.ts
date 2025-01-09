/**
 * Angular component to create the Item class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Angular component Creates the Item model class.
 * */

export class Item {
  name: string;
  description: string;
  image_key: string;
  isCollected: boolean;

  constructor(name: string, description: string, image_key: string, isCollected: boolean) {
    this.name = name;
    this.description = description;
    this.image_key = image_key;
    this.isCollected = isCollected;
  }
}
