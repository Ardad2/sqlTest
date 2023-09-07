package cse360.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionUtil {
    static String url = "jdbc:mysql://localhost:3306/patient_portal";
    static String userName = "root";
    static String password = "Devansh!99";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {

        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
