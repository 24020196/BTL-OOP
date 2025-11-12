package GameManager;

import Entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.Queue;

public class HighScoresController {

    @FXML private Label name1, name2, name3, name4, name5;
    @FXML private Label score1, score2, score3, score4, score5;

    public void initialize() {
        loadHighScores();
    }

    @FXML
    private void onBackToMenu() {
        try {FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/Menu.fxml"));
            Scene menuScene = new Scene(loader.load(), 1280, 720);
            MenuController menu = loader.getController();
            menu.clickEvents();
            Stage stage = (Stage) name1.getScene().getWindow();
            stage.setScene(menuScene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHighScores() {
        Label[] nameLabels = {name1, name2, name3, name4, name5};
        Label[] scoreLabels = {score1, score2, score3, score4, score5};

        int i = 0;
        for (Pair<String, Integer> temp : User.getUser().highScores) {
                nameLabels[i].setText(temp.getKey());
                scoreLabels[i].setText(String.valueOf(temp.getValue()));
                i++;
        }
    }
}
