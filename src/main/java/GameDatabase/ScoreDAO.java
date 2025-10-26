package GameDatabase;

import java.sql.*;

public class ScoreDAO {
    public void saveScore(String username, int score, int level) {
        String sql = "INSERT INTO scores (username, score, level) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setInt(2, score);
            stmt.setInt(3, level);
            stmt.executeUpdate();

            System.out.println(" Đã lưu điểm!");

        } catch (SQLException e) {
            System.out.println(" Lỗi lưu điểm: " + e.getMessage());
        }
    }

    public void showScores() {
        String sql = "SELECT * FROM scores ORDER BY score DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== BẢNG ĐIỂM ===");
            while (rs.next()) {
                System.out.printf("%s - %d điểm - Màn %d%n",
                        rs.getString("username"),
                        rs.getInt("score"),
                        rs.getInt("level"));
            }

        } catch (SQLException e) {
            System.out.println(" Lỗi xem điểm: " + e.getMessage());
        }
    }
}
