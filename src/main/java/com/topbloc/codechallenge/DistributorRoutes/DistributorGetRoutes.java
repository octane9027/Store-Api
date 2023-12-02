package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;



public class DistributorGetRoutes {
        public void configureRoutes() {
            path("/Distributor", () -> {

                get("/GetAllDistributors", (req, res) -> {
                    System.out.println("/Distributor/GetAllDistributors");
                    Object result = DatabaseManager.GetAllDistributors();
                    if(result == null){
                        res.status(500);
                        return "Serverside error occured please try again later.";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }

                    
                });

                get("/items/:id", (req, res) -> {
                    try {
                        String id = req.params(":id");
                        System.out.println("/Distributor/items/:id");

                        if (!id.matches("\\d+")) {
                            res.status(400); // Bad Request
                            return "Invalid ID format. ID must be an integer.";
                        }
                        System.out.println("ID Vaildations complete");
                        System.out.println("Checking DB......");

                        int intValue =Integer.parseInt(id);
                        Object result =DatabaseManager.GetIDItems(intValue);
                        if(result == null){
                            res.status(500);
                            return "Serverside error occured please try again later.";

                        }else{
                            res.status(200);
                            System.out.println("SUCESS");
                            return result;
 
                        }

                        } catch (NumberFormatException e) {
                            res.status(400); // Bad Request
                            return "Invalid ID format. ID must be an integer.";
                        } catch (Exception e) {
                            res.status(500); // Internal Server Error
                            return "Internal Server Error: " + e.getMessage();
                        }
                });


                get("/:id", (req, res) -> {
                    try {
                        String id = req.params(":id");
                        System.out.println("/Distributor/:id");

                        if (!id.matches("\\d+")) {
                            res.status(400); // Bad Request
                            return "Invalid ID format. ID must be an integer.";
                        }
                        System.out.println("ID Vaildations complete");
                        System.out.println("Checking DB......");
                        int intValue =Integer.parseInt(id);
                        Object result = DatabaseManager.GetByIDDistributor(intValue);
                        if(result == null){
                            res.status(500);
                            return "Serverside error occured please try again later.";

                        }else{
                            res.status(200);
                            System.out.println("SUCESS");
                            return result;
 
                        }
                        } catch (NumberFormatException e) {
                            res.status(400); // Bad Request
                            return "Invalid ID format. ID must be an integer.";
                        } catch (Exception e) {
                            res.status(500); // Internal Server Error
                            return "Internal Server Error: " + e.getMessage();
                        }

                });
            });
            

        }
}
