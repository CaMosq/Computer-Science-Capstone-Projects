
Carmen Mosquera
CS 499 Computer Science Capstone
Category : Three - Databases
Artifact: Grocery Tracker Application 



---------- Artifact Overview:

The Grocery Tracker application allows users to track and analyze store sales data, specifically focusing on product sales frequency and trends. This application was built using Java (Spring Boot) for the backend, MySQL for the database, and Angular for the frontend. The application provides features such as adding new products, viewing sales data, and visualizing sales frequency through charts.






--------- Prerequisites:

To run and test the Grocery Tracker application, ensure you have the following tools installed:

- Java Development Kit (JDK) 17 or higher
- Spring Boot 3.x..
- Maven (for backend dependencies)
- MySQL Server (for the database)
- Node.js (for running Angular)
- Angular CLI (globally installed via npm)
- Postman (optional, for API testing)






--------- Setup Instructions:

----Backend (Java Spring Boot):

1. Clone the repository or download the zip file:
   Locate the "Grocery-tracker-backed" folder inside the "enhanced code" folder:	
   cd grocery-tracker-backend
   

2. Set up the MySQL database:
   - Create a new MySQL database named 'grocery_tracker_db'.
   - Modify the database connection properties in 'application.properties' to match your local MySQL setup:
     	properties
     spring.datasource.url=jdbc:mysql://localhost:3306/grocery_tracker_db
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     

3. Run the backend:
   - Ensure you’re in the 'grocery-tracker-backend' directory, then run:
     mvn spring-boot:run
     
   - The Spring Boot server should now be running on 'http://localhost:8080'.

4. Create the database schema:
   - The application will automatically generate the required tables when it starts. You can verify this by checking your MySQL database for the following tables:
     - products
     - categories	
     - sales
     - locations
    	

----Frontend (Angular):

1. Navigate to the frontend directory:
   cd ../grocery-tracker-frontend
  

2. Install dependencies:
   npm install
   

3. Run the Angular application:
   ng serve
   
   - The frontend server will start on 'http://localhost:4200'.







------------- Testing the Application:

1. Add Products:
   - Navigate to the frontend on 'http://localhost:4200'.
   - Use the "Add Product" button on the products page to input new products (e.g., product name, category, and image).
   - The added products will be stored in the MySQL database.

2. Add Sales:
   - On the "Sales" page, you can add sales transactions for existing products.
   - For each sale, input the product, sale date, quantity sold, and total amount.

3. View Sales Data:
   - The application displays charts of sales trends.
   - The "Sales Dashboard (Insights)" visualizes the top 10 most purchased products and their total sales.

4. API Testing (Optional):
   You can test the backend REST API using Postman or any HTTP client. Here are some sample API endpoints:

   - Get All Sales:
     GET http://localhost:8080/api/sales/all
     

   - Add a New Sale:
     POST http://localhost:8080/api/sales
     Content-Type: application/json

     {
         "productId": 1,
         "saleDate": "2024-10-01",
         "quantitySold": 5,
         "totalAmount": 25.00
     }
    

   - Get Product Frequency Daily:
     GET http://localhost:8080/api/sales/product-frequency-daily
    







------------ Troubleshooting:

- CORS Issues: If you're getting CORS-related errors in the browser, ensure that the 'WebSecurityConfig' class in your Spring Boot application is correctly set up to allow cross-origin requests.

- Database Issues: Ensure your MySQL database is running, and your 'application.properties' file has the correct credentials.

- Chart Not Displaying: If the chart on the frontend isn’t rendering, inspect the browser console for errors. Common issues might include missing data or incorrect chart configurations.






------------ Conclusion:

Once set up, you should be able to fully test the Grocery Tracker application, including adding products, recording sales, and viewing data visualizations. For any issues or further clarifications, feel free to reach out.