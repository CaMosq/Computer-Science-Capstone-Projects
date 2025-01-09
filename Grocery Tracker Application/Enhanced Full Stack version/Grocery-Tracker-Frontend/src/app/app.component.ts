/**
 * Grocery tracker frontend - product model class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 */

import {Component, Renderer2, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {RouterModule, RouterLink, RouterOutlet} from '@angular/router';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'Grocer Tracker';
  $cast = this.castTo<HTMLButtonElement>()

  // Correctly reference all buttons using @ViewChildren and collect them as ElementRef
  @ViewChildren('sidebarButton') buttons!: QueryList<ElementRef>;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {}

  // Function to handle button click
  clickedButton(selectedButton: HTMLButtonElement): void {
    // Remove 'active' class from all buttons and reset their background
    this.buttons.forEach((button) => {
      this.renderer.removeClass(button.nativeElement, 'sidebar-btn-active');
      this.renderer.addClass(button.nativeElement, 'sidebar-btn-bgr');
    });

    // Add 'active' class to the clicked button
    this.renderer.addClass(selectedButton, 'sidebar-btn-active');
    this.renderer.removeClass(selectedButton, 'sidebar-btn-bgr');
  }

  // @ts-ignore
  castTo<T>(): (row) => T {
    return (row) => row as T;
  }


}
