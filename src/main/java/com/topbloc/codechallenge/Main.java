package com.topbloc.codechallenge;

import com.topbloc.codechallenge.db.DatabaseManager;

import static spark.Spark.*;

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

        /**
         * This is a Javadoc comment for the InventoryItem class.
         * It provides information about the purpose of the class,
         * its fields, constructors, methods, and usage.
         */
        get("/reset", (req, res) -> {
            DatabaseManager.resetDatabase();
            return "OK";
        });

        /**
         * This is a Javadoc comment for the InventoryItem class.
         * It provides information about the purpose of the class,
         * its fields, constructors, methods, and usage.
         */        
        get("/items", (req, res) -> DatabaseManager.getItems());

        /**
         * This is a Javadoc comment for the InventoryItem class.
         * It provides information about the purpose of the class,
         * its fields, constructors, methods, and usage.
         */
        get("/version", (req, res) -> "TopBloc Code Challenge v1.0");



        /**
         *  localhost:4567/GetInventory- route gets All items in your inventory, including the item name, ID, amount in stock, and total capacity.
         */
                get("/GetInventory", (req, res) -> {
                return  DatabaseManager.getItems();
                });
            
        /**
         *  localhost:4567/GetOutOfStock- 
         */
        get("/Inventory/GetOutOfStock", (req, res) -> {
            return  DatabaseManager.getOutOfStock();
        });
    /**
         *  localhost:4567/GetOverStock- 
         */
        get("/Inventory/GetOverStock", (req, res) -> {
            return  DatabaseManager.GetOverStock();
        });

        /**
         *  localhost:4567/GetLowStock- 
         */
        get("/Inventory/GetLowStock", (req, res) -> {
            return  DatabaseManager.GetLessThen35();
        });

        get("/Inventory/:id", (req, res) -> {
            String id = req.params(":id");
            ///CECK FOR INT if not int return 500
            //STATUS CODES ERROR HANDLING
            int intValue =Integer.parseInt(id);
            return  DatabaseManager.GetByID(intValue);
        });
/////////////////////////////////


            get("/Distributor/GetAllDistributors", (req, res) -> {
                ///CECK FOR INT if not int return 500
                //STATUS CODES ERROR HANDLING
                
                return  DatabaseManager.GetAllDistributors();
            });

            get("/Distributor/items/:id", (req, res) -> {
                String id = req.params(":id");
                ///CECK FOR INT if not int return 500
                //STATUS CODES ERROR HANDLING
                
                int intValue =Integer.parseInt(id);
                return  DatabaseManager.GetIDItems(intValue);
            });


            get("/Distributor/:id", (req, res) -> {
                String id = req.params(":id");
                ///CECK FOR INT if not int return 500
                //STATUS CODES ERROR HANDLING
                
                int intValue =Integer.parseInt(id);
                return  DatabaseManager.GetByIDDistributor(intValue);
            });


    }
}