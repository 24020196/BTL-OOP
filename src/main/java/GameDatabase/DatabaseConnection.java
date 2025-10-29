package GameDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://shuttle.proxy.rlwy.net:53611/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "ygeLIStcAKxGhkfTsiKArkuDvuGrpQNO";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" Kết nối MySQL thành công!");
            } catch (SQLException e) {
                System.out.println(" Lỗi kết nối MySQL: " + e.getMessage());
            }
        }
        return connection;
    }

}
