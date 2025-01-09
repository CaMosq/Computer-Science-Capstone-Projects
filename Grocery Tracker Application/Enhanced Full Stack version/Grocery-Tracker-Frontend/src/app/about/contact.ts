/**
 * Grocery tracker frontend - contactComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component implements the UI for the contact screen of the application.
 */
import {Component} from '@angular/core';

@Component({
  selector: 'app-contact',
  template: `
  <div class="main-wrapper">
    <div class="container">
      <div class="contact-container">
        <h1>Contact Us</h1>
        <p>We'd love to hear from you! Please fill out the form below and we'll get back to you shortly.</p>

        <form class="contact-form" action="" method="POST">
          <label for="name">Name</label>
          <input type="text" id="name" name="name" placeholder="Your name" required>

          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Your email" required>

          <label for="message">Message</label>
          <textarea id="message" name="message" placeholder="Your message" rows="5" required></textarea>

          <button type="submit">Send Message</button>
        </form>
      </div>
    </div>
  </div>
  `,
  styles: ['.container { display: flex;\n justify-content: center;\n margin-top: 40px;}'+
  '.contact-container {\n' +
  '  background-color: #fff;\n' +
  '  padding: 40px;\n' +
  '  border-radius: 8px;\n' +
  '  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n' +
  '  max-width: 500px;\n' +
  '  width: 100%;\n' +
  '  text-align: center;\n' +
  '}\n' +
  '\n' +
  '.contact-container h1 {\n' +
  '  margin-bottom: 20px;\n' +
  '  color: #333;\n' +
  '}\n' +
  '\n' +
  '.contact-container p {\n' +
  '  margin-bottom: 30px;\n' +
  '  color: #666;\n' +
  '}\n' +
  '\n' +
  '.contact-form {\n' +
  '  display: flex;\n' +
  '  flex-direction: column;\n' +
  '}\n' +
  '\n' +
  '.contact-form label {\n' +
  '  font-weight: bold;\n' +
  '  margin-bottom: 5px;\n' +
  '  text-align: left;\n' +
  '  color: #333;\n' +
  '}\n' +
  '\n' +
  '.contact-form input,\n' +
  '.contact-form textarea {\n' +
  '  padding: 12px;\n' +
  '  margin-bottom: 20px;\n' +
  '  border: 1px solid #ddd;\n' +
  '  border-radius: 5px;\n' +
  '  font-size: 16px;\n' +
  '  width: 100%;\n' +
  '}\n' +
  '\n' +
  '.contact-form input:focus,\n' +
  '.contact-form textarea:focus {\n' +
  '  outline: none;\n' +
  '  border-color: #333;\n' +
  '}\n' +
  '\n' +
  '.contact-form button {\n' +
  '  padding: 14px;\n' +
  '  background-color: #0f4c5c;\n' +
  '  color: #fff;\n' +
  '  border: none;\n' +
  '  border-radius: 5px;\n' +
  '  font-size: 16px;\n' +
  '  cursor: pointer;\n' +
  '  transition: background-color 0.3s ease;\n' +
  '}\n' +
  '\n' +
  '.contact-form button:hover {\n' +
  '  background-color: #3949ab;\n' +
  '}'
  ],
  standalone: true
})
export class ContactComponent {

}
