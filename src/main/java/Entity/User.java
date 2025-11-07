package Entity;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class User {
    private String username;
    private int levels;
    private int currentLevel = 0;
    private int Score;
    private String levelPoint;
    private static User user;
    public Queue<Pair<String, Integer>> highScores = new LinkedList<>();

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

    public void setLevelPoint(String levelPoint) {
        this.levelPoint = levelPoint;
        setScore(levelPoint);
        setLevels(levelPoint);
    }


    public void setLevelPoint(int index, int point) {
        StringBuilder sb = new StringBuilder(levelPoint);
        sb.setCharAt(index, (char) ('0' + point));
        this.levelPoint = sb.toString();
        setScore(levelPoint);
        setLevels(levelPoint);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public void setScore() {
        int sum = 0;

        for (int i = 0; i < levelPoint.length(); i++) {
            char c = levelPoint.charAt(i);

            if (Character.isDigit(c)) {
                sum += Character.getNumericValue(c);
            }
        }
        this.Score = sum;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(String levelPoint) {
        int index = -1;

        for (int i = 0; i < levelPoint.length(); i++) {
            if (levelPoint.charAt(i) != '0') {
                index = i + 2;
            }
        }

        this.levels = (index == -1) ? 1 : index;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}