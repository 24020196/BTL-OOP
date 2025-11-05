package GameDatabase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserDataAccessObject {

    private boolean isValid(String input) {
        return input != null && input.length() >= 6 &&
                Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", input);
    }

    /**
     * Đăng ký tài khoản → Trả về chuỗi thông báo
     * "SUCCESS" = đăng ký thành công
     * Còn lại trả về nội dung lỗi
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
     * Đăng nhập → true = đúng, false = sai
     */
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("⚠ Lỗi khi đăng nhập: " + e.getMessage());
            return false;
        }
    }
}
