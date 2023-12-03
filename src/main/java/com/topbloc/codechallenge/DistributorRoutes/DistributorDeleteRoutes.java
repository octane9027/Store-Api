package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;

import com.topbloc.codechallenge.db.DatabaseManager;

public class DistributorDeleteRoutes {
    public void configureRoutes() {
        path("/Distributor", () -> {
                /**
                 *  localhost:4567/GetOverStock- 
                 */
        delete("/DeleteDistributor/:id", (req, res) -> {
            try {
                String id = req.params(":id");
                System.out.println("/Distributor/DeleteDistributor/:id");

                if (!id.matches("\\d+")) {
                    res.status(400); // Bad Request
                    return "Invalid ID format. ID must be an integer.";
                }
                System.out.println("ID Vaildations complete");
                System.out.println("Checking DB......");
                int intValue =Integer.parseInt(id);
                if(DatabaseManager.checkIfDistributorExists(intValue).size() == 0 ){
                    halt(400, "{ message: Bad Request - Distributor does not exist.} ");
                }
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
