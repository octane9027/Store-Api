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
            return  DatabaseManager.getItems();
        });
            
        /**
         *  localhost:4567/GetOutOfStock- 
         */
        get("/GetOutOfStock", (req, res) -> {
            return  DatabaseManager.getOutOfStock();
        });
        /**
         *  localhost:4567/GetOverStock- 
         */
        get("/GetOverStock", (req, res) -> {
            return  DatabaseManager.GetOverStock();
        });

        /**
         *  localhost:4567/GetLowStock- 
         */
        get("/GetLowStock", (req, res) -> {
            return  DatabaseManager.GetLessThen35();
        });

        get("/:id", (req, res) -> {
            try {
            String id = req.params(":id");
            if (!id.matches("\\d+")) {
                res.status(400); // Bad Request
                return "Invalid ID format. ID must be an integer.";
            }
            int intValue =Integer.parseInt(id);
            Object result = DatabaseManager.GetByID(intValue);
            res.status(200);
            return result;
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
