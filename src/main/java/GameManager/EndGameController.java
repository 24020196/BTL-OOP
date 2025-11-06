package GameManager;

import Entity.User;
import GameDatabase.ScoreDataAccessObject;

public class EndGameController {
    private ScoreDataAccessObject scoreDataAccessObject = new ScoreDataAccessObject();
    public void initialize() {
        // mặc đinh loseBackground;
        // load set backgroundWin Invisible
    }

    public void winGame() {
        //Đổi set backgroundWin visible
        //backgroundLose inVisible;

        scoreDataAccessObject.setPoint(User.getUser().getUsername(), User.getUser().getLevelPoint());
    }

}
