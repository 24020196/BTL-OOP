package GameDatabase;

import java.sql.*;
import java.util.regex.Pattern;
import java.util.Scanner;

public class UserDAO {

    private boolean isValid(String input) {
        return input != null && input.length() >= 6 &&
                Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", input);
    }

    public boolean register(String username, String password) {
        if (!isValid(username)) {
            System.out.println("Tên đăng ký phải có ít nhất 6 ký tự và chứa cả chữ lẫn số.");
            return false;
        }
        if (!isValid(password)) {
            System.out.println("Mật khẩu phải có ít nhất 6 ký tự và chứa cả chữ lẫn số.");
            return false;
        }
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(" Lỗi khi đăng ký: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Đăng nhập thành công!");
                return true;
            } else {
                System.out.println("Sai tên đăng nhập hoặc mật khẩu.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("⚠Lỗi khi đăng nhập: " + e.getMessage());
            return false;
        }
    }
}
