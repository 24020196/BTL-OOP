package GameManager;

import Entity.Brick;
import Entity.GameObject;
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

        Bounds bound = new BoundingBox(0, 0, mainLayout.getWidth(), mainLayout.getHeight());
        resize(bound);

        mainLayoutEvents();
    }

    private void mainLayoutEvents() {
        mainLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        mainLayout.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            resize(newBounds);
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
                Stage stage = (Stage) mainLayout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnExit.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
    }

    void resize(Bounds newBounds) {
        resize(menuLayout,0,0,1280, 720, newBounds);

        resize(menuBackground, 0 , 0 , 1280 , 720, newBounds);
        resize(btnPlayGame, 356, 207, 495, 106, newBounds);
        resize(btnHighScores, 356, 336, 495, 106, newBounds);
        resize(btnSetting, 356, 462, 495, 106, newBounds);
        resize(btnExit, 356, 590, 495, 106, newBounds);

    }

    void resize(ImageView imageView, double x, double y, double width, double height, Bounds bounds) {
        imageView.setLayoutX(x * bounds.getWidth() / 1280);
        imageView.setLayoutY(y *  bounds.getHeight() / 720);
        imageView.setFitWidth(width * bounds.getWidth() / 1280);
        imageView.setFitHeight(height*  bounds.getHeight() / 720);
    }

    void resize(AnchorPane anchorPane, double x, double y, double width, double height, Bounds bounds) {
        anchorPane.setLayoutX(x * bounds.getWidth() / 1280);
        anchorPane.setLayoutY(y *  bounds.getHeight() / 720);
        anchorPane.prefWidth(width * bounds.getWidth() / 1280);
        anchorPane.prefHeight(height*  bounds.getHeight() / 720);
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
