package com.topbloc.codechallenge.db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DatabaseManager {
    private static final String jdbcPrefix = "jdbc:sqlite:";
    private static final String dbName = "challenge.db";
    private static String connectionString;
    private static Connection conn;

    static {
        File dbFile = new File(dbName);
        connectionString = jdbcPrefix + dbFile.getAbsolutePath();
    }

    public static void connect() {
        try {
            Connection connection = DriverManager.getConnection(connectionString);
            System.out.println("Connection to SQLite has been established.");
            conn = connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Schema function to reset the database if needed - do not change
    public static void resetDatabase() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            dbFile.delete();
        }
        connectionString = jdbcPrefix + dbFile.getAbsolutePath();
        connect();
        applySchema();
        seedDatabase();
    }

    // Schema function to reset the database if needed - do not change
    private static void applySchema() {
        String itemsSql = "CREATE TABLE IF NOT EXISTS items (\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL UNIQUE\n"
                + ");";
        String inventorySql = "CREATE TABLE IF NOT EXISTS inventory (\n"
                + "id integer PRIMARY KEY,\n"
                + "item integer NOT NULL UNIQUE references items(id) ON DELETE CASCADE,\n"
                + "stock integer NOT NULL,\n"
                + "capacity integer NOT NULL\n"
                + ");";
        String distributorSql = "CREATE TABLE IF NOT EXISTS distributors (\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL UNIQUE\n"
                + ");";
        String distributorPricesSql = "CREATE TABLE IF NOT EXISTS distributor_prices (\n"
                + "id integer PRIMARY KEY,\n"
                + "distributor integer NOT NULL references distributors(id) ON DELETE CASCADE,\n"
                + "item integer NOT NULL references items(id) ON DELETE CASCADE,\n"
                + "cost float NOT NULL\n" +
                ");";

        try {
            System.out.println("Applying schema");
            conn.createStatement().execute(itemsSql);
            conn.createStatement().execute(inventorySql);
            conn.createStatement().execute(distributorSql);
            conn.createStatement().execute(distributorPricesSql);
            System.out.println("Schema applied");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Schema function to reset the database if needed - do not change
    private static void seedDatabase() {
        String itemsSql = "INSERT INTO items (id, name) VALUES (1, 'Licorice'), (2, 'Good & Plenty'),\n"
            + "(3, 'Smarties'), (4, 'Tootsie Rolls'), (5, 'Necco Wafers'), (6, 'Wax Cola Bottles'), (7, 'Circus Peanuts'), (8, 'Candy Corn'),\n"
            + "(9, 'Twix'), (10, 'Snickers'), (11, 'M&Ms'), (12, 'Skittles'), (13, 'Starburst'), (14, 'Butterfinger'), (15, 'Peach Rings'), (16, 'Gummy Bears'), (17, 'Sour Patch Kids'), (18, 'Michalki'), (19, 'Doritos')";
        String inventorySql = "INSERT INTO inventory (item, stock, capacity) VALUES\n"
                + "(1, 22, 25), (2, 4, 20), (3, 15, 25), (4, 30, 50), (5, 14, 15), (6, 8, 10), (7, 10, 10), (8, 30, 40), (9, 17, 70), (10, 43, 65),\n" +
                "(11, 32, 55), (12, 25, 45), (13, 8, 45), (14, 10, 60), (15, 20, 30), (16, 15, 35), (17, 14, 60), (18, 0, 200), (19, 23,20)";
        String distributorSql = "INSERT INTO distributors (id, name) VALUES (1, 'Candy Corp'), (2, 'The Sweet Suite'), (3, 'Dentists Hate Us')";
        String distributorPricesSql = "INSERT INTO distributor_prices (distributor, item, cost) VALUES \n" +
                "(1, 1, 0.81), (1, 2, 0.46), (1, 3, 0.89), (1, 4, 0.45), (2, 2, 0.18), (2, 3, 0.54), (2, 4, 0.67), (2, 5, 0.25), (2, 6, 0.35), (2, 7, 0.23), (2, 8, 0.41), (2, 9, 0.54),\n" +
                "(2, 10, 0.25), (2, 11, 0.52), (2, 12, 0.07), (2, 13, 0.77), (2, 14, 0.93), (2, 15, 0.11), (2, 16, 0.42), (3, 10, 0.47), (3, 11, 0.84), (3, 12, 0.15), (3, 13, 0.07), (3, 14, 0.97),\n" +
                "(3, 15, 0.39), (3, 16, 0.91), (3, 17, 0.85)";

        try {
            System.out.println("Seeding database");
            conn.createStatement().execute(itemsSql);
            conn.createStatement().execute(inventorySql);
            conn.createStatement().execute(distributorSql);
            conn.createStatement().execute(distributorPricesSql);
            System.out.println("Database seeded");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Helper methods to convert ResultSet to JSON - change if desired, but should not be required
    private static JSONArray convertResultSetToJson(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<String> colNames = IntStream.range(0, columns)
                .mapToObj(i -> {
                    try {
                        return md.getColumnName(i + 1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        JSONArray jsonArray = new JSONArray();
        while (rs.next()) {
            jsonArray.add(convertRowToJson(rs, colNames));
        }
        return jsonArray;
    }

    private static JSONObject convertRowToJson(ResultSet rs, List<String> colNames) throws SQLException {
        JSONObject obj = new JSONObject();
        for (String colName : colNames) {
            obj.put(colName, rs.getObject(colName));
        }
        return obj;
    }

    //Route Created For testing
    public static JSONArray selectAllItems() {
        String sql = "SELECT * FROM items;";
        try {
            ResultSet set = conn.createStatement().executeQuery(sql);
            return convertResultSetToJson(set);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
 //Route Created For testing
 public static JSONArray selectAllInventory() {
    String sql = "SELECT * FROM ineventory;";
    try {
        ResultSet set = conn.createStatement().executeQuery(sql);
        return convertResultSetToJson(set);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}
    public static JSONArray getItems() {
        String sql = "SELECT items.id, items.name, inventory.stock, inventory.capacity FROM items JOIN inventory ON items.id = inventory.item  ORDER BY items.id;";
        try {
            ResultSet set = conn.createStatement().executeQuery(sql);
            return convertResultSetToJson(set);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static JSONArray getOutOfStock() {
        String sql = "SELECT items.id, items.name, inventory.stock, inventory.capacity FROM items JOIN inventory ON items.id = inventory.item where inventory.stock = 0  ORDER BY items.id;";
        try {
            ResultSet set = conn.createStatement().executeQuery(sql);
            return convertResultSetToJson(set);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static JSONArray GetOverStock() {
        String sql = "SELECT items.id, items.name, inventory.stock, inventory.capacity FROM items JOIN inventory ON items.id = inventory.item where inventory.capacity <  inventory.stock  ORDER BY items.id;";
        try {
            ResultSet set = conn.createStatement().executeQuery(sql);
            return convertResultSetToJson(set);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
        public static JSONArray GetLessThen35() {
            String sql = "SELECT items.id, items.name, inventory.stock, inventory.capacity FROM items JOIN inventory ON items.id = inventory.item where (CAST(inventory.stock AS REAL) / inventory.capacity) < 0.35 ORDER BY items.id;";
            try {
                ResultSet set = conn.createStatement().executeQuery(sql);
                return convertResultSetToJson(set);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
    }
    public static JSONArray GetByID(int Id) {
        String sql = "SELECT items.id, items.name, inventory.stock, inventory.capacity FROM items JOIN inventory ON items.id = inventory.item WHERE items.id = ? ORDER BY items.id;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return convertResultSetToJson(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


public static JSONArray GetAllDistributors() {
    String sql = "SELECT * FROM distributors;";
    try {
        ResultSet set = conn.createStatement().executeQuery(sql);
        return convertResultSetToJson(set);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}
public static JSONArray GetIDItems(int Id) {
    String sql = "SELECT distributor_prices.item, distributors.name, distributor_prices.cost FROM distributor_prices JOIN distributors ON distributors.id = distributor_prices.distributor WHERE distributor_prices.item = ? ORDER BY distributors.id;";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return convertResultSetToJson(resultSet);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}

public static JSONArray GetByIDDistributor(int Id) {
    String sql = "SELECT distributors.id, distributors.name, distributor_prices.cost FROM distributors JOIN distributor_prices ON distributors.id = distributor_prices.distributor WHERE distributor_prices.item = ?;";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return convertResultSetToJson(resultSet);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}
public static JSONArray getLargestId() {
    String sql = "SELECT MAX(id) AS max_id FROM items";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        ResultSet set = conn.createStatement().executeQuery(sql);
        return convertResultSetToJson(set);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}
public static JSONArray checkIfItemExists(int Id) {
    String sql = "SELECT * from items where id = ?";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return convertResultSetToJson(resultSet);       
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}
public static JSONArray checkIfDistributorExists(int Id) {
    String sql = "SELECT * from distributors where id = ?";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return convertResultSetToJson(resultSet);       
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return null;
    }
}

public static int InsertItem(int Id, String Name) {
    String sql = "INSERT INTO items (id, name) VALUES (?, ?)";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        preparedStatement.setString(2, Name);
        return preparedStatement.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return 0;
    }

}

public static int InsertItemInventory(int Id, int capacity,int stock ) {
    String sql = "INSERT INTO inventory (item, stock, capacity) VALUES (?, ?, ?)";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        preparedStatement.setInt(2, stock);
        preparedStatement.setInt(3, capacity);

        return preparedStatement.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return 0;
    }

}
public static int InsertDistributorCatalogue(int distributor, int item,int cost ) {
    String sql = "INSERT INTO distributor_prices (distributor, item, cost) VALUES VALUES (?, ?, ?)";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, distributor);
        preparedStatement.setInt(2, item);
        preparedStatement.setInt(3, cost);

        return preparedStatement.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return 0;
    }

}
public static int InsertDistributor(int Id, String Name) {
    String sql = "INSERT INTO items (id, name) VALUES (?, ?)";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setInt(1, Id);
        preparedStatement.setString(2, Name);
        return preparedStatement.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return 0;
    }

}

}
