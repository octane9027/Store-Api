package com.topbloc.codechallenge;

import com.topbloc.codechallenge.db.DatabaseManager;

import static spark.Spark.*;
import com.topbloc.codechallenge.DistributorRoutes.*;
import com.topbloc.codechallenge.InventoryRoutes.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.connect();
/**
 * This is a Javadoc comment for the InventoryItem class.
 * It provides information about the purpose of the class,
 * its fields, constructors, methods, and usage.
 */        

     options("/*",
                (req, res) -> {
                    res.header("Access-Control-Allow-Headers", "content-type");
                    res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                    return "OK";
                });



        get("/reset", (req, res) -> {
            DatabaseManager.resetDatabase();
            return "OK";
        });

             
        get("/items", (req, res) -> DatabaseManager.getItems());

       
        get("/version", (req, res) -> "TopBloc Code Challenge v1.0");
        
        //router for Inventory Routes
        new InventoryPostRoutes().configureRoutes();
        new InventoryPutRoutes().configureRoutes();
        new InventoryGetRoutes().configureRoutes();
        new InventoryDeleteRoutes().configureRoutes();


        //router for Distributo Routes
        new DistributorPostRoutes().configureRoutes();
        new DistributorGetRoutes().configureRoutes();
        new DistributorPutRoutes().configureRoutes();
        new DistributorDeleteRoutes().configureRoutes();

    }
}