/**
 * Angular component to create the house / location class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This Angular component Communicates creates the house model class
 * */
import {Item} from "./item";

export class House{
    name: string;
    description: string;
    item: Item;

    constructor(name: string, description: string, item: Item) {
        this.name = name;
        this.description = description;
        this.item = item;

    }
}
