package GameDatabase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp tiện ích để quản lý và cung cấp kết nối đến cơ sở dữ liệu MySQL.
 * Lớp này chứa thông tin đăng nhập và một phương thức tĩnh để lấy kết nối.
 */
public class DatabaseConnection {
    // Thông tin kết nối đến cơ sở dữ liệu Railway
    private static final String URL = "jdbc:mysql://shuttle.proxy.rlwy.net:53611/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "ygeLIStcAKxGhkfTsiKArkuDvuGrpQNO";

    /**
     * Biến kết nối tĩnh
     * Phương thức getConnection() bên dưới tạo kết nối mới mỗi lần gọi.
     */
    public static Connection connection;

    /**
     * Thiết lập và trả về một kết nối mới đến cơ sở dữ liệu.
     *
     * @return Một đối tượng {@link Connection} đã được kết nối.
     * @throws SQLException Nếu xảy ra lỗi khi cố gắng kết nối với cơ sở dữ liệu.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
