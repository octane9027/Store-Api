package com.topbloc.codechallenge.InventoryRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;



public class InventoryGetRoutes {
    public void configureRoutes() {

        path("/Inventory", () -> {
         
                    /**
                     *  localhost:4567/GetInventory- route gets All items in your inventory, including the item name, ID, amount in stock, and total capacity.
                     */
                get("/GetInventory", (req, res) -> {
                    System.out.println("/Inventory/GetInventory");
                    Object result = DatabaseManager.getItems();

                    if(result == null){
                        res.status(500);
                        return "{ message: Serverside error occured please try again later.}";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }
                });
                
                get("/Items", (req, res) -> {
                    System.out.println("/Inventory/Items");
                    Object result = DatabaseManager.selectAllItems();

                    if(result == null){
                        res.status(500);
                        return "{ message: Serverside error occured please try again later.}";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }
                });
                    
                /**
                 *  localhost:4567/GetOutOfStock- 
                 */
                get("/GetOutOfStock", (req, res) -> {
                    System.out.println("/Inventory/GetOutOfStock");
                    Object result = DatabaseManager.getOutOfStock();

                    if(result == null){
                        res.status(500);
                        return "{ message: Serverside error occured please try again later.}";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }
                });
                /**
                 *  localhost:4567/GetOverStock- 
                 */
                get("/GetOverStock", (req, res) -> {
                    System.out.println("/Inventory/GetOverStock");
                    Object result = DatabaseManager.GetOverStock();

                    if(result == null){
                        res.status(500);
                        return "{ message: Serverside error occured please try again later.}";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }
                });

                /**
                 *  localhost:4567/GetLowStock- 
                 */
                get("/GetLowStock", (req, res) -> {
                    System.out.println("/Inventory/GetLowStock");
                    Object result = DatabaseManager.GetLessThen35();
                    if(result == null){
                        res.status(500);
                        return "{ message: Serverside error occured please try again later.}";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;
                    }
                });
                /**
                 *  localhost:4567/GetOverStock- 
                 */
                get("/:id", (req, res) -> {
                    try {
                    String id = req.params(":id");
                    System.out.println("/Inventory/:id");

                    if (!id.matches("\\d+")) {
                        res.status(400); // Bad Request
                        return "{ message: Serverside error occured please try again later.}";
                    }
                    System.out.println("ID Vaildations complete");
                    System.out.println("Checking DB......");
                    int intValue =Integer.parseInt(id);
                    Object result = DatabaseManager.GetByID(intValue);
                    if(result == null){
                        res.status(500);
                        return "{message : Serverside error occured please try again later.}";

                    }else{
                        res.status(200);
                        System.out.println("SUCESS");
                        return result;

                    }
                    } catch (NumberFormatException e) {
                        res.status(400); // Bad Request
                        return "{message: Invalid ID format. ID must be an integer.}";
                    } catch (Exception e) {
                        res.status(500); // Internal Server Error
                        return "Internal Server Error: " + e.getMessage();
                    }

                });

    });



    }

}
