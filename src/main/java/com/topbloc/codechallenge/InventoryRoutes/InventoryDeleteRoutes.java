package com.topbloc.codechallenge.InventoryRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;

public class InventoryDeleteRoutes {
    public void configureRoutes() {
        path("/Item", () -> {
        /**
        *  localhost:4567/Item/DeleteItem/:id
        */
            delete("/DeleteItem/:id", (req, res) -> {
                try {
                    String id = req.params(":id");
                    System.out.println("/Item/DeleteItem/:id");
                    //verifies that this is an integer 
                    if (!id.matches("\\d+")) {
                        res.status(400); // Bad Request
                        return "{messgae: Invalid ID format. ID must be an integer.}";
                    }
                    System.out.println("ID Vaildations complete");
                    System.out.println("Checking DB......");
                    int intValue =Integer.parseInt(id);
                    //check if the item exists in the database
                    if(DatabaseManager.checkIfItemExists(intValue).size() == 0 ){
                        halt(400, "{ message:  Bad Request - Item does not exist. } ");
                    }
                    //deletes item
                    DatabaseManager.DeleteItem(intValue);
                    res.status(200);
                    System.out.println("SUCESS");
                    return "{messgae: OK}";
    
    
                    } catch (NumberFormatException e) {
                        res.status(400); // Bad Request
                        return "{messgae: Invalid ID format. ID must be an integer.}";
                    } catch (Exception e) {
                        res.status(500); // Internal Server Error
                        return "{messgae: Internal Server Error: " + e.getMessage();
                    }
            });
        });
    }
}
