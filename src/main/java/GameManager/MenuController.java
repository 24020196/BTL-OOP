package GameManager;

import Entity.Brick;
import Entity.GameObject;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static javafx.application.Application.launch;


public class MenuController {
    @FXML
    AnchorPane menuLayout;
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

    public void initialize() {
        resizeImageView(menuBackground, 0 , 0 , 1280 , 720);
        resizeImageView(btnPlayGame, 356, 207, 495, 106);
        resizeImageView(btnHighScores, 356, 336, 495, 106);
        resizeImageView(btnSetting, 356, 462, 495, 106);
        resizeImageView(btnExit, 356, 590, 495, 106);
        menuLayoutEvents();
    }

    private void menuLayoutEvents() {
        menuLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        menuLayout.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            resizeImageView(menuBackground, 0 , 0 , 1280 , 720 , newBounds);
            resizeImageView(btnPlayGame, 356, 207, 495, 106, newBounds);
            resizeImageView(btnHighScores, 356, 336, 495, 106, newBounds);
            resizeImageView(btnSetting, 356, 462, 495, 106, newBounds);
            resizeImageView(btnExit, 356, 590, 495, 106, newBounds);
        });

        menuLayout.setOnMouseMoved( mouseMove -> {
            setVisibleImageView(btnPlayGame, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnHighScores, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnSetting, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnExit, mouseMove.getX(), mouseMove.getY());

        });

        btnPlayGame.setOnMouseClicked(event -> {
            System.out.println("Brick clicked!");
        });
    }

    void resizeImageView(ImageView imageView, double x, double y, double width, double height) {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    void resizeImageView(ImageView imageView, double x, double y, double width, double height, Bounds bounds) {
        imageView.setLayoutX(x * bounds.getWidth() / 1280);
        imageView.setLayoutY(y *  bounds.getHeight() / 720);
        imageView.setFitWidth(width * bounds.getWidth() / 1280);
        imageView.setFitHeight(height*  bounds.getHeight() / 720);
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

    public void startGame() {

    }
}
