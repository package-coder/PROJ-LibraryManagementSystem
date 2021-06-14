package sample.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String SQCONN = "jdbc:sqlite:libredb.sqlite";

    public static Connection getConnection(){

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQCONN);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
