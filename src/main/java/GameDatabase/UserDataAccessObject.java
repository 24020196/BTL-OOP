package GameDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Lớp Đối tượng Truy cập Dữ liệu (DAO) xử lý các hoạt động liên quan đến
 * xác thực người dùng, chẳng hạn như đăng ký và đăng nhập.
 */
public class UserDataAccessObject {
    /**
     * Hàm nội bộ để kiểm tra tính hợp lệ của tên đăng nhập và mật khẩu.
     * Yêu cầu: không null, ít nhất 6 ký tự, và phải chứa cả chữ và số.
     *
     * @param input Chuỗi cần kiểm tra.
     * @return true nếu hợp lệ, ngược lại là false.
     */
    private boolean isValid(String input) {
        return input != null && input.length() >= 6 &&
                Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", input);
    }

    /**
     * Thực hiện đăng ký một tài khoản mới vào cơ sở dữ liệu.
     *
     * @param username Tên đăng nhập mong muốn.
     * @param password Mật khẩu mong muốn.
     * @return Một chuỗi thông báo kết quả:
     * - "SUCCESS": Nếu đăng ký thành công.
     * - "Tên đăng nhập đã tồn tại!": Nếu vi phạm ràng buộc unique.
     * - Các thông báo lỗi khác nếu có.
     */
    public String register(String username, String password) {

        if (!isValid(username)) {
            return "Tên đăng nhập phải có ít nhất 6 ký tự và chứa cả chữ và số!";
        }

        if (!isValid(password)) {
            return "Mật khẩu phải có ít nhất 6 ký tự và chứa cả chữ và số!";
        }

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return "SUCCESS";

        } catch (SQLException e) {

            // Kiểm tra trùng username
            if (e.getMessage().toLowerCase().contains("duplicate")) {
                return "Tên đăng nhập đã tồn tại!";
            }

            return "Lỗi hệ thống: " + e.getMessage();
        }
    }

    /**
     * Xác thực thông tin đăng nhập của người dùng với cơ sở dữ liệu.
     *
     * @param username Tên đăng nhập.
     * @param password Mật khẩu.
     * @return true nếu tên đăng nhập và mật khẩu khớp, ngược lại là false.
     */
    public boolean login(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("⚠ Lỗi khi đăng nhập: " + e.getMessage());
            return false;
        }
    }
}
