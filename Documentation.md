
## Authentication and Considerations for the future
- currently there is no authentication but this security feature must be considered in the event that this API is exposed to the public. 
- logging agent must be considered currently to minimize the dependencies a logger lik Log4j was not installed but upon deployment to AWS, and productionizing this api this must be added.
- Alerting also must be added in the event of true failures or service downing adding an alerting service like pager duty should be considered.
- The optimal infrasturcture on AWS should be considered based off the average TPS. We have a few options:
      - We could deploy this as an EC2 with ECS tasks with an alb in front and r53. Downside to this would be cost
      - We could use a lambda and ALB in the event we have a lower TPS and are not concerned with latency on cold starts. downside is scalability.
      - Finally we have the next serverless solution that maybe the most optimal is a fargate api with alb infront. This is a happy medium with the cost and cold start latency.



## Error Handling
All error handling is handled serverside and input validations are conducted each route details the proper inputs in the documentation.

## Endpoints

### 1. Endpoint Name
#### `GET /Inventory/GetInventory`

**Description:**
this route gets All items in your inventory, including the item name, ID, amount in stock, and total capacity. This joins two tables in the database on the item id.


**Possible Responses:**
500,200

**Input:**
None

**Response:**
[
  {
    "name": "Good & Plenty",
    "id": 2,
    "stock": 4,
    "capacity": 20
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
localhost:4567/Inventory/GetInventory


### 2. Endpoint Name
#### `GET /Inventory/Items`

**Description:**
this route gets All items in your inventory table route created for testing


**Possible Responses:**
500,200

**Input:**
None

**Response:**
[
  {
    "name": "Good & Plenty",
    "id": 2
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567/Inventory/Items

### 3. Endpoint Name
#### `GET /Inventory/GetOutOfStock`

**Description:**
this route gets All All items in your inventory that are currently out of stock, including the item name, ID, amount in stock, and total capacity
- will query the table and check for if your inventory does not have this item 
**Possible Responses:**
500,200

**Input:**
None

**Response:**
[
  {
    "name": "Michalki",
    "id": 18,
    "stock": 0,
    "capacity": 200
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567/Inventory/GetOverStock


### 4. Endpoint Name
#### `GET /Inventory/GetOverStock`

**Description:**
All items in your inventory that are currently overstocked, including the item name, ID, amount in stock, and total capacity
- this route will return from the database when stock is greater (not greater then or equal to) the capcity.
**Possible Responses:**
500,200

**Input:**
None

**Response:**
[
  {
    "name": "Doritos",
    "id": 19,
    "stock": 23,
    "capacity": 20
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567/Inventory/GetOverStock




### 5. Endpoint Name
#### `GET /Inventory/GetLowStock`

**Description:**
All items in your inventory that are currently low on stock (<35%), including the item name, ID, amount in stock, and total capacity

**Possible Responses:**
500,200

**Input:**
None

**Response:**
[
  {
    "name": "Good & Plenty",
    "id": 2,
    "stock": 4,
    "capacity": 20
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567/Inventory/GetLowStock



### 6. Endpoint Name
#### `GET /Inventory/:id`

**Description:**
A dynamic route that, when given an ID, returns the item name, ID, amount in stock, and total capacity of that item

**Possible Responses:**
500,400,200

**Input:**
an integer in the api url

**Response:**
[
  {
    "name": "Good & Plenty",
    "id": 2,
    "stock": 4,
    "capacity": 20
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567/Inventory/1




### 7. Endpoint Name
#### `GET /Distributor/GetAllDistributors`

**Description:**
All distributors, including the id and name


**Possible Responses:**
500,200

**Input:**
None

**Response:**
[
  {
    "name": "The Sweet Suite",
    "id": 2
  },
  {
    "name": "Dentists Hate Us",
    "id": 3
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567/Distributor/GetAllDistributors





### 8. Endpoint Name
#### `GET /Distributor/items/:id`

**Description:**
A dynamic route that, given a distributors ID, returns the items distributed by a given distributor, including the item name, ID, and cost


**Possible Responses:**
500, 400, 200

**Input:**
an integer in the api url


**Response:**
[
  {
    "item": 2,
    "cost": 0.18,
    "name": "The Sweet Suite"
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567//Distributor/items/2



### 9. Endpoint Name
#### `GET /Distributor/Distributor/:id`

**Description:**
A dynamic route that, given an item ID, returns all offerings from all distributors for that item, including the distributor name, ID, and cost


**Possible Responses:**
500, 400, 200

**Input:**
an integer in the api url


**Response:**
[
  {
    "item": 16,
    "cost": 0.42000000000000004,
    "name": "Gummy Bears"
  }
]
route returns an array of json objects sorted by ID. A valid response is an empty array which will return 200

**Request:**
http GET localhost:4567//Distributor/items/2


### 9. Endpoint Name
#### `POST /Inventory/addItem`

**Description:**
Add a new item to your inventory, If item does not exist in the items db it will not be inserted since it is not a valid item.


**Possible Responses:**
500, 400, 200

**Input:**
{
  item: Integer,
  stock: Integer,
  capacity : Integer
}

**Response:**
Route Will return an OK that the item was successfully inserted to the database

**Request:**
http POST localhost:4567/Inventory/addItem



### 9. Endpoint Name
#### `POST /Inventory/addItem`

**Description:**
Add a new item to your Database, id will be assigned by on the backend to avoid duplicate id insertion.


**Possible Responses:**
500, 400, 200

**Input:**
{
  name : String
}

**Response:**
Route Will return an OK that the item was successfully inserted to the database

**Request:**
http POST localhost:4567/item/add



### 10. Endpoint Name
#### `POST /Inventory/addItem`

**Description:**
Add a new item to your Database, id will be assigned by on the backend to avoid duplicate id insertion.


**Possible Responses:**
500, 400, 200

**Input:**
{
  name : String
}

**Response:**
Route Will return an OK that the item was successfully inserted to the database

**Request:**
http POST localhost:4567/item/add





### 11. Endpoint Name
#### `PUT /Inventory/UpdateItem`

**Description:**
Add a new item to your Database, id will be assigned by on the backend to avoid duplicate id insertion.


**Possible Responses:**
500, 400, 200

**Input:**
{
  item: Integer,
  stock: Integer,
  capacity : Integer
}

**Response:**
Route Will return an OK that the item was successfully update in the database

**Request:**
http PUT localhost:4567/Inventory/UpdateItem



### 12. Endpoint Name
#### `POST /Distributor/addDistributor`

**Description:**
Adds a distributor the database

**Possible Responses:**
500, 400, 200

**Input:**


**Response:**
Route Will return an OK that the item was successfully update in the database

**Request:**
http POST localhost:4567/Distributor/addDistributor




### 13. Endpoint Name
#### `POST /Distributor/addToCatalouge`

**Description:**
Adds a distributor the database

**Possible Responses:**
500, 400, 200

**Input:**
{
 name: String
}

**Response:**
Route Will return an OK that the item was successfully update in the database

**Request:**
http POST localhost:4567/Distributor/addToCatalouge


### 14. Endpoint Name
#### `PUT /Distributor/UpdatePrice`

**Description:**
Updates price in distributors catalogue given 
**Possible Responses:**
500, 400, 200

**Input:**
{
 distributor: Integer,
 item: Integer,
 cost: Integer
}

**Response:**
Route Will return an OK that the item was successfully update in the database

**Request:**
http PUT localhost:4567/Distributor/UpdatePrice

### 15. Endpoint Name
#### `DELETE /Distributor/DeleteDistributor/:id`

**Description:**
deletes distributor and all of its entries in the catalogue.

**Possible Responses:**
500, 400, 200

**Input:**
Distributor id

**Response:**
Route Will return an OK that the item was successfully update in the database

**Request:**
http PUT localhost:4567/Distributor/DeleteDistributor/:id


### 16. Endpoint Name
#### `DELETE /Item/DeleteItem/:id`

**Description:**
deletes item and all of its entries in the catalogue.

**Possible Responses:**
500, 400, 200

**Input:**
Distributor id

**Response:**
Route Will return an OK that the item was successfully update in the database

**Request:**
http PUT localhost:4567/Distributor/DeleteDistributor/:id