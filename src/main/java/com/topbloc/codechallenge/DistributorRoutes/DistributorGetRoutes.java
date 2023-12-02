package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;
import com.topbloc.codechallenge.db.DatabaseManager;



public class DistributorGetRoutes {
public void configureRoutes() {
    path("/Distributor", () -> {

        get("/GetAllDistributors", (req, res) -> {
            return  DatabaseManager.GetAllDistributors();
        });

        get("/items/:id", (req, res) -> {
            try {
                String id = req.params(":id");
    
                if (!id.matches("\\d+")) {
                    res.status(400); // Bad Request
                    return "Invalid ID format. ID must be an integer.";
                }
                int intValue =Integer.parseInt(id);
                Object result =DatabaseManager.GetIDItems(intValue);
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


        get("/:id", (req, res) -> {
            try {
                String id = req.params(":id");
    
                if (!id.matches("\\d+")) {
                    res.status(400); // Bad Request
                    return "Invalid ID format. ID must be an integer.";
                }
                int intValue =Integer.parseInt(id);
                Object result = DatabaseManager.GetByIDDistributor(intValue);
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
