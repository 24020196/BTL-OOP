package GameManager;

import Entity.User;
import GameDatabase.ScoreDataAccessObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EndGameController {
    private ScoreDataAccessObject scoreDataAccessObject = new ScoreDataAccessObject();

    @FXML AnchorPane endGameLayout;
    @FXML ImageView endGameBackground;

    public void initialize() {
        eventListener();
    }

    public void winGame() {
        endGameBackground.setImage(new Image(getClass().getResource("/res/youWin.png").toExternalForm()));
        scoreDataAccessObject.setPoint(User.getUser().getUsername(), User.getUser().getLevelPoint());
    }

    public void eventListener() {
        endGameLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
            if (mouseEvent.getX() >= 452 && mouseEvent.getX() <= 743) {
                if (mouseEvent.getY() >= 536 && mouseEvent.getY() <= 601) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/game.fxml"));
                        Parent root = loader.load();
                        GameController gameController = loader.getController();
                        gameController.setLevel(User.getUser().getCurrentLevel());
                        gameController.startGame();
                        Stage stage = (Stage) endGameLayout.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (mouseEvent.getY() >= 583 && mouseEvent.getY() <= 652) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/levelMap.fxml"));
                        Parent root = loader.load();
                        LevelMapController levelMapController = loader.getController();
                        levelMapController.drawStar();
                        Stage stage = (Stage) endGameLayout.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (mouseEvent.getX() >= 552 && mouseEvent.getX() <= 640) {
                if (mouseEvent.getY() >= 674 && mouseEvent.getY() <= 700) {
                    Platform.exit();
                }
            }
        });
    }
}
