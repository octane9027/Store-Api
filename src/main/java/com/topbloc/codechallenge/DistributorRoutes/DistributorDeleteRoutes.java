package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;

import com.topbloc.codechallenge.db.DatabaseManager;

public class DistributorDeleteRoutes {
    public void configureRoutes() {
        path("/Distributor", () -> {
                /**
                 *  localhost:4567/Distributor/DeleteDistributor/:id- deletes from db based on id 
                 */
        delete("/DeleteDistributor/:id", (req, res) -> {
            try {
                String id = req.params(":id");
                System.out.println("/Distributor/DeleteDistributor/:id");
                //regext to check if the id is a valid int.
                if (!id.matches("\\d+")) {
                    res.status(400); // Bad Request
                    return "Invalid ID format. ID must be an integer.";
                }
                System.out.println("ID Vaildations complete");
                System.out.println("Checking DB......");
                int intValue =Integer.parseInt(id);
                //verifies that distributor exists in the database
                if(DatabaseManager.checkIfDistributorExists(intValue).size() == 0 ){
                    halt(400, "{ message: Bad Request - Distributor does not exist.} ");
                }
                //executes the query
                DatabaseManager.DeleteDistributor(intValue);
                res.status(200);
                System.out.println("SUCESS");
                return "{message: OK}";


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
