package com.topbloc.codechallenge.InventoryRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;

public class InventoryDeleteRoutes {
    public void configureRoutes() {
        path("/Item", () -> {

            delete("/DeleteItem/:id", (req, res) -> {
                try {
                    String id = req.params(":id");
                    System.out.println("/Item/DeleteItem/:id");
    
                    if (!id.matches("\\d+")) {
                        res.status(400); // Bad Request
                        return "{messgae: Invalid ID format. ID must be an integer.}";
                    }
                    System.out.println("ID Vaildations complete");
                    System.out.println("Checking DB......");
                    int intValue =Integer.parseInt(id);
                    if(DatabaseManager.checkIfItemExists(intValue).size() == 0 ){
                        halt(400, "{ message:  Bad Request - Item does not exist. } ");
                    }
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
