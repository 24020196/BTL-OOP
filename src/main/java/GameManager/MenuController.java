package GameManager;

import Entity.Brick;
import Entity.GameObject;
import Entity.User;
import GameDatabase.ScoreDataAccessObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class MenuController {
    @FXML
    AnchorPane mainLayout;
    @FXML
    AnchorPane menuLayout;
    @FXML
    AnchorPane setting;
    @FXML
    ImageView menuBackground;
    @FXML
    ImageView btnPlayGame;
    @FXML
    ImageView btnHighScores;
    @FXML
    ImageView btnSetting;
    @FXML
    ImageView btnExit;

    private ScoreDataAccessObject data = new ScoreDataAccessObject();

    public void initialize() {
        setsize();
        setting.setVisible(false);
        mainLayoutEvents();
    }

    public void connectDatabase() {
        data.getPoint(User.getUser().getUsername(),User.getUser());
        User.getUser().highScores.clear();
        data.getHighScorces();
    }

    private void mainLayoutEvents() {
        mainLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());

        });


        mainLayout.setOnMouseMoved( mouseMove -> {
            setVisibleImageView(btnPlayGame, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnHighScores, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnSetting, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnExit, mouseMove.getX(), mouseMove.getY());

        });

        btnPlayGame.setOnMouseClicked(event -> {
            //levelLayout.setVisible(true);
            //menuLayout.setVisible(false);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/levelMap.fxml"));
                Parent root = loader.load();
                LevelMapController levelMapController = loader.getController();
                levelMapController.drawStar();
                Stage stage = (Stage) mainLayout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        btnHighScores.setOnMouseClicked(event -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/highScore.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) mainLayout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        btnSetting.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/SettingView.fxml"));
                AnchorPane settingPane = loader.load();
                setting.getChildren().setAll(settingPane);

                // Ẩn menu và hiện Setting
                menuLayout.setVisible(false);
                setting.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        btnExit.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
    }

    void setsize() {
        update(menuBackground, 0 , 0 , 1280 , 720);
        update(btnPlayGame, 356, 207, 495, 106);
        update(btnHighScores, 356, 336, 495, 106);
        update(btnSetting, 356, 462, 495, 106);
        update(btnExit, 356, 590, 495, 106);

    }

    void update(ImageView imageView, double x, double y, double width, double height) {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }


    void setVisibleImageView(ImageView imageView, double x, double y) {
        double left = imageView.getLayoutX();
        double right = imageView.getLayoutX() + imageView.getFitWidth();
        double up = imageView.getLayoutY();
        double down = imageView.getLayoutY() + imageView.getFitHeight();
        imageView.setVisible(false);
        if(x >= left && x <= right)
            if(y >= up & y <= down) imageView.setVisible(true);
    }
}