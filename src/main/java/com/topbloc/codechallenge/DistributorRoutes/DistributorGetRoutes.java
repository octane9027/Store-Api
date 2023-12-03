package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;



public class DistributorGetRoutes {
        public void configureRoutes() {
            path("/Distributor", () -> {
                /**
                 *  localhost:4567/Distributor/GetAllDistributors- gets all distributors from the database
                 */
                get("/GetAllDistributors", (req, res) -> {
                    System.out.println("/Distributor/GetAllDistributors");
                    Object result = DatabaseManager.GetAllDistributors();
                    //return 500 there must have been a problem on the database side
                    if(result == null){
                        res.status(500);
                        return "Serverside error occured please try again later.";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }
                });

                /**
                 *  localhost:4567/Distributor/Distributor/:id- gets distributor by id 
                 */
                get("/Distributor/:id", (req, res) -> {
                    try {
                        String id = req.params(":id");
                        System.out.println("/Distributor/Distributor/:id");
                          //regext to check if the id is a valid int.

                        if (!id.matches("\\d+")) {
                            res.status(400); // Bad Request
                            return "Invalid ID format. ID must be an integer.";
                        }
                        System.out.println("ID Vaildations complete");
                        System.out.println("Checking DB......");
                        //verifies that distributor exists in the database
                        int intValue =Integer.parseInt(id);
                        Object result =DatabaseManager.GetIDItems(intValue);
                        //return 500 there must have been a problem on the database side
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


                get("/items/:id", (req, res) -> {
                    try {
                        String id = req.params(":id");
                        System.out.println("/Distributor/items/:id");
                          //regext to check if the id is a valid int.

                        if (!id.matches("\\d+")) {
                            res.status(400); // Bad Request
                            return "Invalid ID format. ID must be an integer.";
                        }
                        System.out.println("ID Vaildations complete");
                        System.out.println("Checking DB......");
                        //verifies that distributor exists in the database
                        int intValue =Integer.parseInt(id);
                        Object result = DatabaseManager.GetByIDDistributor(intValue);
                        //return 500 there must have been a problem on the database side
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
