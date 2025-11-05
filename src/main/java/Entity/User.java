package Entity;

import java.util.ArrayList;

public class User {
    private String username;
    private int levels;
    private int high_scores;
    private String levelPoint;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public String getLevelPoint() {
        return levelPoint;
    }

    public void setLevelPoint(String levelPoint) {
        this.levelPoint = levelPoint;
        setHigh_scores(levelPoint);
        setLevels(levelPoint);
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

    public void setLevels(String levelPoint) {
        int index = -1;

        for (int i = 0; i < levelPoint.length(); i++) {
            if (levelPoint.charAt(i) != '0') {
                index = i + 2;
            }
        }

        this.levels = (index == -1) ? 1 : index;
    }


}