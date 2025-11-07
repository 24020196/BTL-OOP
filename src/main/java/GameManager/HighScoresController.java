package GameManager;

import Entity.User;
import GameDatabase.ScoreDataAccessObject;
import javafx.util.Pair;

public class HighScoresController {
    private ScoreDataAccessObject scoreDataAccessObject = new ScoreDataAccessObject();
    public void initialize() {
        for (Pair<String, Integer> temp : User.getUser().highScores) {
            System.out.println(temp.getKey() + " " + temp.getValue());
        }

    }



}