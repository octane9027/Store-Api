
## Getting Started
1. Open the project in your IDE
2. Run the project via the `Main` class or with the run button in your IDE
3. Hit the API with your method of choice (Postman, cURL, etc.) at `localhost:4567/version` to ensure the project is running correctly.

## Description
At the start, this project has two classes: `Main` and `DatabaseManager`. `Main` is the location you will write any routes you
need to add, and `DatabaseManager` contains the code for connecting and interacting with the database.

The scenario of this application is as follows: You are a software engineer at a company that sells candy. Your team has been tasked with building a backend
for the companies new ECP (Enterprise Candy Planning) software. The scaffolding of the project and the database have already been built, and the frontend team
has the following feature requirements to complete their work:
- GET routes
  - Inventory routes that return the following:
    - All items in your inventory, including the item name, ID, amount in stock, and total capacity
    - All items in your inventory that are currently out of stock, including the item name, ID, amount in stock, and total capacity
    - All items in your inventory that are currently overstocked, including the item name, ID, amount in stock, and total capacity
    - All items in your inventory that are currently low on stock (<35%), including the item name, ID, amount in stock, and total capacity
    - A dynamic route that, when given an ID, returns the item name, ID, amount in stock, and total capacity of that item
  -  Distributor routes that return the following:
      - All distributors, including the id and name
      - A dynamic route that, given a distributors ID, returns the items distributed by a given distributor, including the item name, ID, and cost
      - A dynamic route that, given an item ID, returns all offerings from all distributors for that item, including the distributor name, ID, and cost
- POST/PUT/DELETE routes
  - Routes that allow you to:
    - Add a new item to the database
    - Add a new item to your inventory
    - Modify an existing item in your inventory
    - Add a distributor
    - Add items to a distributor's catalog (including the cost)
    - Modify the price of an item in a distributor's catalog
    - Get the cheapest price for restocking an item at a given quantity from all distributors
    - Delete an existing item from your inventory
    - Delete an existing distributor given their ID
  

