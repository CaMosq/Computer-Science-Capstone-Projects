/**
 * Grocery tracker frontend - SalesComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to create the charts and sales insights.
 */
import {Component, OnInit} from '@angular/core';
import {
  BarController,
  BarElement,
  PieController,
  LineController,
  LineElement,
  PointElement,
  ArcElement,
  CategoryScale,
  Chart,
  ChartType,
  Legend,
  LinearScale,
  Title,
  Tooltip
} from 'chart.js';
import {AppService} from '../service/app.service';
import {BaseChartDirective} from 'ng2-charts';
import {NgForOf} from '@angular/common';


// Register the required Chart.js components
Chart.register(CategoryScale, LinearScale, BarController, PieController, LineController, LineElement, PointElement, BarElement, ArcElement, Title, Tooltip, Legend);

@Component({
  selector: 'app-sales',
  standalone: true,
  imports: [
    BaseChartDirective,
    NgForOf
  ],
  templateUrl: './sales.component.html',
  styleUrl: './sales.component.css'
})
export class SalesComponent  implements OnInit {
  //variables for chart 1
  productNamesList!: any[];
  quantities!: any[];

  //variables for chart 2
  dates!: any[];
  quantities2!: any[];

  //variables for chart 3
  categoriesNames!: any[];
  quantities3!: any[];

  constructor(private appService: AppService) {}

  /////////////////////////////////////////// init
  ngOnInit(): void {

    /////////////////////////------ CHART #1 top-selling products chart
    this.appService.getTopSellingProducts().subscribe((data: any[]) => {

      //organize / parse data so the chart can understand
      this.productNamesList= data.map(item => item[0]);
      this.quantities = data.map(item => item[1]);

      //create chart for products
      const barChartType: ChartType = 'bar';
      this.createTopSellingChart(
        'topSellingProductsChart',
        barChartType,
        this.productNamesList,
        this.quantities,
        'Top Selling Products',
        'Product Name',
        'Quantity Sold',
        data
      );

    });

    /////////////////////////------ CHART #2 total sales by day chart
    this.appService.getProductFrequencyDaily().subscribe((data: any[]) => {

      //create chart for sales by date
      const chartType: ChartType = 'pie';
      this.createSalesByDayChart(
        'salesByDayChart',
        chartType,
        'Total Sales By Day',
        data);
    });

    ///////////////////////////////-- CHART # 3 - top selling categories
    this.appService.getTopSellingCategories().subscribe((data: any[]) => {

      //organize data for each axis
      this.categoriesNames = data.map(item => item[0]);
      this.quantities3 = data.map(item => item[1]);

      //create categories chart
      const barChartType: ChartType = 'bar';
      this.createTopSellingChart(
        'topSellingCategoriesChart',
        barChartType,
        this.categoriesNames,
        this.quantities3,
        'Top Selling Categories',
        'Category Name',
        'Quantity Sold',
        data);
    });


  }

  getAllSales() {
    this.appService.getAllSales().subscribe((data: any[]) => {
      console.log("record of sales:" + data);
    });
  }


  //method to generate random values for color generator
  generateColor(){
    return Math.floor(Math.random() * 256).toString();
  }

  //method to create the top-selling products chart
  createTopSellingChart(chartId: string, chartType: ChartType, xData: any[], yData: any[],  topLabel: string, xAxisName: string, yAxisName: string, data: any[]) {

    //generate random colors for each bar in the chart
    const backgroundColors: string[] = [];
    const borderColors: string[] = [];
    data.forEach(() => {
      const comma = ', ';
      const rColor = 'rgba('.concat(this.generateColor(), comma, this.generateColor(), comma, this.generateColor(), comma, '0.8)');
      const bColor = 'rgba('.concat(this.generateColor(), comma, this.generateColor(), comma, this.generateColor(), comma, '1)');

      backgroundColors.push(rColor);
      borderColors.push(bColor);
    });

    //create chart
    return new Chart(chartId, {
      type: chartType,
      data: {
        labels: xData,
        datasets: [{
          data: yData,
          label: topLabel,
          backgroundColor: backgroundColors,
          borderColor: borderColors,
          borderWidth: 1,
        }]
      },
      options: {
        responsive: true,
        scales: {
          x: {type: 'category', title: {display: true, text: xAxisName}}, //Set x-axis as 'category'
          y: {beginAtZero: true, title: {display: true, text: yAxisName}}
        },
        plugins: {
          legend: {display: true, position: 'top'},
        }
      }
    });

  }

  createSalesByDayChart(chartId: string, chartType: ChartType,  topLabel: string,  data: any[]) {

    //organize data so the char can understand
    //data received: [id, productName, date, quantity], ...]
    const salesByDate: {[key: string]: number } = {};

    // Grouping sales data by date
    data.forEach((sale: any[]) => {
      const saleDate = sale[2];  // Sale date
      const quantitySold = sale[3];  // Quantity sold

      if (!salesByDate[saleDate]) {
        salesByDate[saleDate] = 0;  // Initialize the date if it doesn't exist
      }
      salesByDate[saleDate] += quantitySold;  // Add to the quantity sold on that date

    });
    // Split the grouped data into labels and dataset
    const dates = Object.keys(salesByDate);
    const quantities = Object.values(salesByDate);

    //generate random colors for each bar in the chart
    const bgColors: string[] = [];
    const bdColors: string[] = [];
    data.forEach(() => {
      const deli = ', ';
      const rColor = 'rgba('.concat(this.generateColor(), deli, this.generateColor(), deli, this.generateColor(), deli, '0.8)');
      const bColor = 'rgba('.concat(this.generateColor(), deli, this.generateColor(), deli, this.generateColor(), deli, '1)');

      bgColors.push(rColor);
      bdColors.push(bColor);
    });

    //create chart
    const salesByDateChart = new Chart(chartId, {
      type: chartType,
      data: {
        labels: dates,
        datasets: [{
          data: quantities,
          label: topLabel,
          backgroundColor: bgColors,
          borderColor: bdColors,
          borderWidth: 1,
        }],
      },
      options: {
        responsive: true,
        scales: {
          x: {type: 'category', title: {display: true, text: 'Dates'}}, //Set x-axis as 'category'
          y: {beginAtZero: true, title: {display: true, text: 'Quantity Sold'}}
        },
        plugins: {
          legend: {display: true, position: 'top'},
        }
      }
    });

    //assign variables for chart 2 summary
    this.dates = dates;
    this.quantities2 = quantities;
  }

}
