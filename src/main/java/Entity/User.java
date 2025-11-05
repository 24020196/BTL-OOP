package Entity;

import java.util.ArrayList;

public class User {
    private int id;
    private int levels;
    private int high_scores;
    private String levelPoint;

    public User() {}

    public User(int id, String levelPoint) {
        this.id = id;
        this.levelPoint = levelPoint;

        setHigh_scores(levelPoint);
        setLevels();
    }

    public String getLevelPoint() {
        return levelPoint;
    }

    public void setLevelPoint(String levelPoint) {
        this.levelPoint = levelPoint;
    }

    public int getHigh_scores() {
        return high_scores;
    }

    public void setHigh_scores(String levelPoint) {
        int sum = 0;

        for (int i = 0; i < levelPoint.length(); i++) {
            char c = levelPoint.charAt(i);

            if (Character.isDigit(c)) {
                sum += Character.getNumericValue(c);
            }
        }
        this.high_scores = sum;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels() {
        int index = -1;

        for (int i = 0; i < levelPoint.length(); i++) {
            if (levelPoint.charAt(i) != '0') {
                index = i + 2;
            }
        }

            if (index >= 9) {
                index = 9;
            }

        this.levels = (index == -1) ? 1 : index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) {

        // levelPoint gồm 9 ký tự, ví dụ:
        String levelPoint = "111111110";

        User user = new User(1, levelPoint);

        System.out.println("User ID: " + user.getId());
        System.out.println("Level Point String: " + user.getLevelPoint());
        System.out.println("High Score (Tổng điểm): " + user.getHigh_scores());
        System.out.println("Levels (Chỉ số level cao nhất khác 0): " + user.getLevels());
    }
}
