package GameManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LevelMapController {
    @FXML
    AnchorPane levelLayout;
    @FXML
    ImageView levelMapBackground;
    @FXML ImageView level1;
    @FXML ImageView level2;
    @FXML ImageView level3;
    @FXML ImageView level4;
    @FXML ImageView level5;
    @FXML ImageView level6;
    @FXML ImageView level7;
    @FXML ImageView level8;
    @FXML ImageView level9;

    public void initialize() {
        setsize();
        levelLayoutEvents();
    }

    private void levelLayoutEvents() {
        levelLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        levelLayout.setOnMouseMoved( mouseMove -> {


        });

        ImageView[] levels = {level1, level2, level3, level4, level5, level6, level7, level8, level9};

        for (int i = 0; i < levels.length; i++) {
            final int levelIndex = i + 1;
            levels[i].setOnMouseClicked(event -> {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/Game.fxml"));
                    Parent root = loader.load();
                    GameController gameController = loader.getController();
                    gameController.setLevel(levelIndex);
                    gameController.startGame();
                    Stage stage = (Stage) levelLayout.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                    System.out.println("Loading level " + levelIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    void setsize() {

        update(levelMapBackground,0,0,1280,720);
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