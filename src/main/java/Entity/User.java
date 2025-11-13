package Entity;

import javafx.util.Pair;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Quản lý thông tin của người dùng hiện tại, bao gồm tên, điểm số và tiến độ màn chơi.
 * Sử dụng mẫu thiết kế Singleton (getUser) để đảm bảo chỉ có một thể hiện (instance) người dùng
 * trong suốt trò chơi.
 */
public class User {
    private String username;
    private int levels; // Tổng số màn chơi (có vẻ chưa được sử dụng)
    private int currentLevel = 0; // Màn chơi hiện tại
    private int Score; // Tổng điểm
    private String levelPoint; // Chuỗi lưu điểm của từng màn (ví dụ: "32010")
    private static User user; // Thể hiện Singleton
    public Queue<Pair<String, Integer>> highScores = new LinkedList<>(); // Hàng đợi cho điểm cao

    /**
     * Phương thức tĩnh để lấy thể hiện duy nhất của User (Singleton).
     *
     * @return Thể hiện User.
     */
    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    /**
     * Khởi tạo mặc định.
     */
    public User(){}

    /**
     * Khởi tạo với tên người dùng.
     *
     * @param username Tên người dùng.
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Lấy chuỗi điểm của các màn.
     *
     * @return Chuỗi điểm.
     */
    public String getLevelPoint() {
        return levelPoint;
    }

    /**
     * Đặt chuỗi điểm của các màn và tự động cập nhật tổng điểm.
     *
     * @param levelPoint Chuỗi điểm mới.
     */
    public void setLevelPoint(String levelPoint) {
        this.levelPoint = levelPoint;
        setScore(levelPoint); // Tự động tính lại tổng điểm
    }

    /**
     * Cập nhật điểm cho một màn chơi cụ thể trong chuỗi điểm.
     *
     * @param index Chỉ số của màn chơi (bắt đầu từ 0).
     * @param point Điểm mới (0-9).
     */
    public void setLevelPoint(int index, int point) {
        StringBuilder sb = new StringBuilder(levelPoint);
        sb.setCharAt(index, (char) ('0' + point));
        this.levelPoint = sb.toString();
        setScore(levelPoint); // Tự động tính lại tổng điểm
    }

    /**
     * Lấy tên người dùng.
     *
     * @return Tên người dùng.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Đặt tên người dùng.
     *
     * @param username Tên mới.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Tính toán và đặt tổng điểm dựa trên chuỗi điểm của các màn.
     *
     * @param levelPoint Chuỗi điểm (ví dụ: "321").
     */
    public void setScore(String levelPoint) {
        int sum = 0;
        for (int i = 0; i < levelPoint.length(); i++) {
            char c = levelPoint.charAt(i);
            if (Character.isDigit(c)) {
                sum += Character.getNumericValue(c);
            }
        }
        this.Score = sum;
    }

    /**
     * Lấy tổng điểm hiện tại.
     *
     * @return Tổng điểm.
     */
    public int getScore() {
        return Score;
    }

    /**
     * Lấy tổng số màn chơi.
     *
     * @return Tổng số màn.
     */
    public int getLevels() {
        return levels;
    }

    /**
     * Lấy chỉ số của màn chơi hiện tại.
     *
     * @return Chỉ số màn chơi.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Đặt màn chơi hiện tại.
     *
     * @param currentLevel Chỉ số màn chơi mới.
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}