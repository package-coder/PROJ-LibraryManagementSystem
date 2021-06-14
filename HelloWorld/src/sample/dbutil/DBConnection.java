package sample.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String PATH = "HelloWorld/src/sample/dbutil/libredb.sqlite";
    private static final String SQCONN = "jdbc:sqlite:";

    public static Connection getConnection(){

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQCONN+PATH);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
