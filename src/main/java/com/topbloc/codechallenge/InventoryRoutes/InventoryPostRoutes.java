package com.topbloc.codechallenge.InventoryRoutes;
import static spark.Spark.*;


import com.topbloc.codechallenge.db.DatabaseManager;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class InventoryPostRoutes {


    public void configureRoutes() {
        path("/Inventory", () -> {
                /**
                 *  localhost:4567/GetOverStock- 
                 */
            post("/addItem", (req, res) -> {
                try{
                    JSONParser parser = new JSONParser();
                    JSONObject jsonBody = (JSONObject) parser.parse(req.body());
                    if (jsonBody.size() != 3) {
                        halt(400, "{ message: Bad Request - JSON object must have exactly 3 fields}");
                    }
                    try {
                        if (!jsonBody.containsKey("item") ||   !jsonBody.get("item").toString().matches("\\d+")) {
                            halt(400, "{ message: Bad Request - JSON object must have a field named 'item' as a Integer, please update item to integer}");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "{ message: Bad Request - JSON object must have a field named 'item', item may not exist in the object, also check your type of the value it must be integer}");

                    } 
                     try {
                        if (!jsonBody.containsKey("stock") ||   !jsonBody.get("stock").toString().matches("\\d+")) {
                            halt(400, "{ message: Bad Request - JSON object must have a field named 'stock' as a Integer, please update stock to integer}");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "{ message: Bad Request - JSON object must have a field named 'stock', stock may not exist in the object,  also check your type of the value it must be integer}");

                    } 

                    try {
                        if (!jsonBody.containsKey("capacity") ||   !jsonBody.get("capacity").toString().matches("\\d+")) {
                            halt(400, "{ message: Bad Request - JSON object must have a field named 'capacity' as a Integer, please update capacity to integer}");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "{ message: Bad Request - JSON object must have a field named 'capacity', capacity may not exist in the object, also check your type of the value it must be integer}");

                    } 
                   
                    int itemId = ((Long) jsonBody.get("item")).intValue();
                   JSONArray array =  DatabaseManager.checkIfItemExists(itemId);
                   if(array.size() == 0){
                         halt(400, "{ message: Bad Request - item doesn't exist in items database}");
                   }

                    int capacity =  ((Long) jsonBody.get("capacity")).intValue();
                    int stock =  ((Long) jsonBody.get("stock")).intValue();
                    DatabaseManager.InsertItemInventory( itemId, capacity, stock );
                    return "OK";
                }
                 catch (ParseException e) {
                    halt(500, "{ message: Server Error}");
                }
        
    
           
                return "{ message:  OK}" ;  
              
                });
   


        });
                /**
                 *  localhost:4567/GetOverStock- 
                 */
        path("/item", () -> {
            post("/add", (req, res) -> {
                try{
                    JSONParser parser = new JSONParser();
                    JSONObject jsonBody = (JSONObject) parser.parse(req.body());
                    if (jsonBody.size() != 1) {
                        halt(400, "Bad Request - JSON object must have exactly one field");
                    }
                    String fieldName = jsonBody.keySet().iterator().next().toString();
    
                    Object fieldValue = jsonBody.get(fieldName);
        
                    if (!"name".equals(fieldName) || !(fieldValue instanceof String)) {
                        halt(400, "Bad Request - JSON object must have a field named 'name' as a string");

                    }
            
                        JSONObject  largestId =  (JSONObject) DatabaseManager.getLargestId().get(0);
                        int maxId = (int) largestId.get("max_id") + 1;
                        String name= (String) jsonBody.get("name");
                        DatabaseManager.InsertItem(maxId, name);
                    return "OK";
                }
                 catch (ParseException e) {
                    halt(500, "Server Error");
                }
        
    
           
                return "OK" ;  
              
                });
                    
        });
    }

}
