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
    private int currentLevel = 0;
    private int Score;
    private String levelPoint;
    private static User user;
    public Queue<Pair<String, Integer>> highScores = new LinkedList<>();

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

    public User(){}

    public User(String username) {
        this.username = username;
    }

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
        setScore(levelPoint);
    }

    /**
     * Cập nhật điểm cho một màn chơi cụ thể trong chuỗi điểm.
     *
     * @param index Chỉ số của màn chơi (bắt đầu từ 0).
     * @param point Điểm mới (0-3).
     */
    public void setLevelPoint(int index, int point) {
        StringBuilder sb = new StringBuilder(levelPoint);
        sb.setCharAt(index, (char) ('0' + point));
        this.levelPoint = sb.toString();
        setScore(levelPoint);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Tính toán và đặt tổng điểm dựa trên chuỗi điểm của các màn.
     *
     * @param levelPoint Chuỗi điểm.
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

    public int getScore() {
        return Score;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}