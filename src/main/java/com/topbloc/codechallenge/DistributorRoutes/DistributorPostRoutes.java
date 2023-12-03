package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;

import com.topbloc.codechallenge.db.DatabaseManager;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class DistributorPostRoutes {
    public void configureRoutes() {
        path("/Distributor", () -> {
        /**
        *  localhost:4567/Distributor/addToCatalouge
        */
            post("/addToCatalouge", (req, res) -> {
                try{
                    JSONParser parser = new JSONParser();
                    JSONObject jsonBody = (JSONObject) parser.parse(req.body());
                    //verifies that body has 3 fields otherwise its a bad reuqest
                    if (jsonBody.size() != 3) {
                        halt(400, "Bad Request - JSON object must have exactly 3 fields");
                    }
                    try {
                        //verifies that the field distributor exists and it is an integer 
                        if (!jsonBody.containsKey("distributor") ||   !jsonBody.get("distributor").toString().matches("\\d+")) {
                            halt(400, "Bad Request - JSON object must have a field named 'distributor' as a Integer, please update distributor to integer");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "Bad Request - JSON object must have a field named 'distributor', distributor may not exist in the object, also check your type of the value it must be integer");

                    } 
                     try {
                        //verifies that the field item exists and it is an integer 
                        if (!jsonBody.containsKey("item") ||   !jsonBody.get("item").toString().matches("\\d+")) {
                            halt(400, "Bad Request - JSON object must have a field named 'item' as a Integer, please update item to integer");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "Bad Request - JSON object must have a field named 'item', item may not exist in the object,  also check your type of the value it must be integer");

                    } 

                    try {
                        //verifies that the field cost exists and it is double regex validation used 
                        if (!jsonBody.containsKey("cost") ||   !jsonBody.get("cost").toString().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
                            halt(400, "Bad Request - JSON object must have a field named 'cost' as a Integer, please update cost to double");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "Bad Request - JSON object must have a field named 'cost', cost may not exist in the object, also check your type of the value it must be double");

                    } 
                   int distributorId = ((Long) jsonBody.get("distributor")).intValue();
                   JSONArray array =  DatabaseManager.checkIfDistributorExists(distributorId);
                   //verify that this exists in the database
                   if(array.size() == 0){
                         halt(400, "Bad Request - distributor doesn't exist in distributor database");
                   }

                    int cost =  ((Long) jsonBody.get("cost")).intValue();
                    int item =  ((Long) jsonBody.get("item")).intValue();
                    //query db
                    DatabaseManager.InsertDistributorCatalogue( distributorId, item, cost );
                    return "OK";
                }
                 catch (ParseException e) {
                    halt(500, "Server Error");
                }
                return "OK" ;  
              
                });
        });
         /**
        *  localhost:4567/Distributor/addDistributor
        */
        path("/Distributor", () -> {
            post("/addDistributor", (req, res) -> {
                try{
                    JSONParser parser = new JSONParser();
                    JSONObject jsonBody = (JSONObject) parser.parse(req.body());
                    //verifies that body has 1 field otherwise its a bad reuqest

                    if (jsonBody.size() != 1) {
                        halt(400, "Bad Request - JSON object must have exactly one field");
                    }
                    String fieldName = jsonBody.keySet().iterator().next().toString();
    
                    Object fieldValue = jsonBody.get(fieldName);
                    //verifies that body has 1 field that is a string and is named name

                    if (!"name".equals(fieldName) || !(fieldValue instanceof String)) {
                        halt(400, "Bad Request - JSON object must have a field named 'name' as a string");

                    }
                        JSONObject  largestId =  (JSONObject) DatabaseManager.getLargestIdDistributor().get(0);
                        int maxId = (int) largestId.get("max_id") + 1;
                        String name= (String) jsonBody.get("name");
                        DatabaseManager.InsertDistributor(maxId, name);
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
