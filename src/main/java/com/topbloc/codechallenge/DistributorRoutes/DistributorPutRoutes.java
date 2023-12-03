package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class DistributorPutRoutes {
    public void configureRoutes() {
        path("/Distributor", () -> {

            put("/UpdatePrice", (req, res) -> {
                try{
                    JSONParser parser = new JSONParser();
                    JSONObject jsonBody = (JSONObject) parser.parse(req.body());
                    if (jsonBody.size() != 3) {
                        halt(400, "Bad Request - JSON object must have exactly 3 fields");
                    }
                    try {
                        if (!jsonBody.containsKey("distributor") ||   !jsonBody.get("distributor").toString().matches("\\d+")) {
                            halt(400, "Bad Request - JSON object must have a field named 'distributor' as a Integer, please update distributor to integer");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "Bad Request - JSON object must have a field named 'distributor', distributor may not exist in the object, also check your type of the value it must be integer");

                    } 
                     try {
                        if (!jsonBody.containsKey("item") ||   !jsonBody.get("item").toString().matches("\\d+")) {
                            halt(400, "Bad Request - JSON object must have a field named 'item' as a Integer, please update item to integer");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "Bad Request - JSON object must have a field named 'item', item may not exist in the object,  also check your type of the value it must be integer");

                    } 

                    try {
                        if (!jsonBody.containsKey("cost") ||   !jsonBody.get("cost").toString().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
                            halt(400, "Bad Request - JSON object must have a field named 'cost' as a Integer, please update cost to double");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        halt(400, "Bad Request - JSON object must have a field named 'cost', cost may not exist in the object, also check your type of the value it must be double");

                    } 
                   
                   int distributorId = ((Long) jsonBody.get("distributor")).intValue();
                   JSONArray array =  DatabaseManager.checkIfDistributorExists(distributorId);
                   if(array.size() == 0){
                         halt(400, "Bad Request - distributor doesn't exist in distributor database");
                   }

                   int item = ((Long) jsonBody.get("item")).intValue();

                   JSONArray arrayItem =  DatabaseManager.checkIfItemExists(item);
                   if(arrayItem.size() == 0){
                         halt(400, "Bad Request - distributor doesn't exist in distributor database");
                   }

                   // Double cost =  ((Long) jsonBody.get("cost")).doubleValue();
                    double cost = (double) jsonBody.get("cost");
                    DatabaseManager.updateDistributorPricing( distributorId, item, cost );
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
