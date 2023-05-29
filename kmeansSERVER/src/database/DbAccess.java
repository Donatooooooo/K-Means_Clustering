package database;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {

    private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final int PORT = 3306;
    private String SERVER = "localhost";
    private String DATABASE = "MapDB";
    private String USER_ID = "MapUser";
    private String PASSWORD = "map";

    private Connection conn;

    public DbAccess() {

    }

    public DbAccess(String SERVER, String DATABASE, String userId, String PASSWORD) {
        this.SERVER = SERVER;
        this.DATABASE = DATABASE;
        this.USER_ID = userId;
        this.PASSWORD = PASSWORD;
    }

    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME).newInstance();
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException(e.getMessage());
        } catch (InstantiationException e) {
            throw new DatabaseConnectionException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }

        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE +
            "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        System.out.println("+++ " + "Connessione in: " + connectionString + " +++\n");
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(
                "--- SQLException: " + e.getMessage() + " ---\n");
        }
    }

    Connection getConnection() {
        return conn;
    }

    public void closeConnection() throws DatabaseConnectionException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionException
            	("--- Errore durante la chiusura della connessione: " + e.getMessage() + " ---\n");
        }
    }
}
