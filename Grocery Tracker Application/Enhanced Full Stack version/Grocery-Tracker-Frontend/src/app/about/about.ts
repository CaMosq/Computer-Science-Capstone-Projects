/**
 * Grocery tracker frontend - aboutComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component implements the UI for the about screen of the application.
 */
import {Component} from '@angular/core';


@Component({
  selector: 'app-about',
  template: `
  <div class="main-wrapper">
    <div class="about-container">
      <section class="about-header">
        <h1>About Grocery Tracker</h1>
        <p>Welcome to Grocery Tracker, your go-to solution for tracking and managing product sales data effectively.</p>
        <p><strong>Version:</strong> 1.0</p>
        <p><strong>Developer:</strong> Carmen Mosquera</p>
      </section>

      <section class="about-purpose">
        <h2>Purpose and Features</h2>
        <ul>
          <li>Track Sales Trends: Keep an eye on daily sales, monitor the frequency of product purchases, and identify your top-selling products.</li>
          <li>Visualize Data: Interactive charts help visualize sales data over time, allowing you to spot trends and patterns with ease.</li>
          <li>Manage Product Information: View detailed product information such as categories, locations, and total sales.</li>
          <li>Analyze Performance: Get insights into the best-performing products, track inventory movement, and predict future demand based on past trends.</li>
        </ul>
      </section>

      <section class="about-technologies">
        <h2>Technologies Used</h2>
        <ul>
          <li><strong>Frontend:</strong> Angular for a responsive and intuitive user interface.</li>
          <li><strong>Backend:</strong> Java Spring Boot for reliable and scalable data processing.</li>
          <li><strong>Database:</strong> MySQL to securely store and manage product and sales information.</li>
          <li><strong>Data Visualization:</strong> Chart.js for dynamic, interactive sales charts.</li>
        </ul>
      </section>

      <section class="about-benefits">
        <h2>Why Choose Grocery Tracker?</h2>
        <ul>
          <li><strong>Increase Efficiency:</strong> Spend less time manually tracking sales and more time making strategic decisions.</li>
          <li><strong>Make Data-Driven Decisions:</strong> Understand which products drive your business and optimize stock levels.</li>
          <li><strong>Boost Profitability:</strong> Align your inventory with customer demand, reduce waste, and maximize profitability.</li>
        </ul>
      </section>

      <section class="about-contact">
        <h2>Get in Touch</h2>
        <p>We are always looking to improve Grocery Tracker and welcome your feedback. If you have any questions, suggestions, or need support, feel free to reach out to us.</p>
        <p>Thank you for choosing Grocery Tracker, where sales insights meet simplicity.</p>
      </section>
    </div>
  </div>
  `,
  styles: ['.about-container {\n' +
  '  max-width: 1200px;\n' +
  '  margin: 0 auto;\n' +
  '  padding: 20px;\n' +
  '  font-family: Arial, sans-serif;\n' +
  '}\n' +
  '.about-header h1 {\n' +
  '  text-align: center;\n' +
  '  font-size: 2.5rem;\n' +
  '  margin-bottom: 10px;\n' +
  '}' +
  '.about-header p {\n' +
  '  text-align: center;\n' +
  '  font-size: 1.2rem;\n' +
  '  margin-bottom: 30px;\n' +
  '}' +
  'h2 {\n' +
  '  font-size: 2rem;\n' +
  '  margin-bottom: 15px;\n' +
  '}\n' +
  '\n' +
  'p, li {\n' +
  '  font-size: 1.1rem;\n' +
  '  line-height: 1.5;\n' +
  '  margin-bottom: 10px;\n' +
  '}\n' +
  'ul {\n' +
  '  list-style-type: disc;\n' +
  '  margin-left: 20px;\n' +
  '}\n' +
  '\n' +
  '.about-contact {\n' +
  '  margin-top: 40px;\n' +
  '  text-align: center;}'
  ],
  standalone: true
})

export class AboutComponent {


}
