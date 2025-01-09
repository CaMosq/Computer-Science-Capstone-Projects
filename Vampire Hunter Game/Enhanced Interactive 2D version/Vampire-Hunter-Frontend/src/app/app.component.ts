import {Component} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {GameComponent} from "./game/game.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, GameComponent, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Vampire Hunter Frontend';

}
