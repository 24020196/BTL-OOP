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
    }


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

    public int getLevels() {
        return levels;
    }



    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}