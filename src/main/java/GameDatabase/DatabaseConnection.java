package GameDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://shuttle.proxy.rlwy.net:53611/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "ygeLIStcAKxGhkfTsiKArkuDvuGrpQNO";

    public static Connection connection;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
