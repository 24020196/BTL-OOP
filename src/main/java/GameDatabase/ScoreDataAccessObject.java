package GameDatabase;

import Entity.User;
import javafx.util.Pair;

import java.sql.*;

/**
 * Lớp Đối tượng Truy cập Dữ liệu (DAO) chịu trách nhiệm cho tất cả các hoạt động
 * cơ sở dữ liệu liên quan đến điểm số (high scores) và điểm từng màn (levelPoint).
 */
public class ScoreDataAccessObject {

    /**
     * Lấy 5 người dùng có điểm cao nhất từ cơ sở dữ liệu và lưu vào
     * hàng đợi (Queue) highScores của đối tượng User singleton.
     */
    public void getHighScorces() {
        String sql = "SELECT high_scores,username FROM users ORDER BY high_scores DESC LIMIT 5";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Đã lấy BẢNG ĐIỂM");
                while (rs.next()) {
                    User.getUser().highScores.add(new Pair<>(rs.getString("username")
                            , rs.getInt("high_scores")));
                }
            }

        } catch (SQLException e) {
            System.out.println(" Lỗi xem điểm: " + e.getMessage());
        }
    }

    /**
     * Cập nhật điểm cao (high_scores) cho một người dùng cụ thể trong cơ sở dữ liệu.
     *
     * @param username Tên người dùng cần cập nhật.
     * @param score    Điểm cao mới.
     */
    public void setHighScorces(String username, int score) {
        String sql = "UPDATE users SET high_scores = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, score);
            stmt.setString(2, username);
            stmt.executeUpdate(); // Thực thi câu lệnh cập nhật

            System.out.println(" Đã lưu highscore!");

        } catch (SQLException e) {
            System.out.println(" Lỗi lưu điểm: " + e.getMessage());
        }
    }

    /**
     * Lấy chuỗi điểm của từng màn (levelPoint) từ cơ sở dữ liệu cho một người dùng
     * và cập nhật trực tiếp vào đối tượng User được cung cấp.
     *
     * @param username Tên người dùng cần lấy điểm.
     * @param user     Đối tượng User để cập nhật thông tin.
     */
    public void getPoint(String username, User user) {
        String sql = "SELECT levelPoint FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Đã lấy levelPoint");
                if (rs.next()) { // Chỉ cần lấy 1 dòng
                    user.setLevelPoint(rs.getString("LevelPoint"));
                }
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi xem điểm: " + e.getMessage());
        }
    }

    /**
     * Cập nhật chuỗi điểm của từng màn (levelPoint) cho một người dùng cụ thể.
     *
     * @param username   Tên người dùng cần cập nhật.
     * @param levelPoint Chuỗi điểm mới (ví dụ: "31200").
     */
    public void setPoint(String username, String levelPoint) {
        String sql = "UPDATE users SET levelPoint = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, levelPoint);
            stmt.setString(2, username);
            stmt.executeUpdate();

            System.out.println(" Đã lưu levelPoint!");

        } catch (SQLException e) {
            System.out.println(" Lỗi lưu điểm: " + e.getMessage());
        }
    }

}