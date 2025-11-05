package GameDatabase;

import Entity.User;

import java.sql.*;

public class ScoreDataAccessObject {

    public void showScores(String username) {
        String sql = "SELECT * FROM users WHERE username = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("=== BẢNG ĐIỂM ===");
                while (rs.next()) {
                    System.out.printf("%s - %d điểm - Màn %d%n",
                            rs.getString("username"),
                            rs.getInt("LevelPoint"),
                            rs.getInt("levels"));
                }
            }

        } catch (SQLException e) {
            System.out.println(" Lỗi xem điểm: " + e.getMessage());
        }
    }

    public void getPoint(String username, User user) {
        String sql = "SELECT levelPoint FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    user.setLevelPoint(rs.getString("LevelPoint"));
                    //return rs.getString("LevelPoint");
                }
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi xem điểm: " + e.getMessage());
        }
    }

    public void setPoint(String username, String levelPoint) {
        String sql = "UPDATE users SET levelPoint = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, levelPoint);
            stmt.setString(2, username);
            stmt.executeUpdate();

            System.out.println(" Đã lưu điểm!");

        } catch (SQLException e) {
            System.out.println(" Lỗi lưu điểm: " + e.getMessage());
        }
    }

}
