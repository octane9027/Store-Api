package com.topbloc.codechallenge.DistributorRoutes;
import static spark.Spark.*;

import com.topbloc.codechallenge.db.DatabaseManager;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DistributorDeleteRoutes {
    public void configureRoutes() {
        path("/Distributor", () -> {

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
                if(DatabaseManager.checkIfItemExists(intValue).size() == 0 ){
                    halt(400, "Bad Request - Distributor does not exist.");
                }
                DatabaseManager.DeleteDistributor(intValue);
                res.status(200);
                System.out.println("SUCESS");
                return "{ResponseFromServer: OK}";


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
