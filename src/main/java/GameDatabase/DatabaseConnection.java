package GameDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/breakout_game"; // thay game_db bằng tên DB của bạn
    private static final String USER = "root"; // user MySQL
    private static final String PASSWORD = "admin"; // password MySQL của bạn

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
