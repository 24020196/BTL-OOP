package GameManager;

import Entity.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LevelMapController {

    @FXML AnchorPane levelLayout;
    @FXML ImageView levelMapBackground;
    @FXML ImageView level1;
    @FXML ImageView level2;
    @FXML ImageView level3;
    @FXML ImageView level4;
    @FXML ImageView level5;
    @FXML ImageView level6;
    @FXML ImageView level7;
    @FXML ImageView level8;
    @FXML ImageView level9;

    private Canvas canvas = new Canvas(1280, 720);;
    private GraphicsContext gc = canvas.getGraphicsContext2D();;
    private Image starOnImg = new Image(getClass().getResource("/res/star_full.png").toExternalForm());
    private Image starOffImg = new Image(getClass().getResource("/res/star_empty.png").toExternalForm());

    public void initialize() {
        canvas.setMouseTransparent(true);
        levelLayout.getChildren().add(canvas);
        drawStarsAllLevels(gc);
        setsize();
        levelLayoutEvents();

    }

    private void drawStarsAllLevels(GraphicsContext gc) {
        double[][] positions = {
                {84, 311},   // Level 1
                {589, 190},   // Level 2
                {1024, 255},  // Level 3
                {1207, 515},  // Level 4
                {905, 694},   // Level 5
                {517, 694},   // Level 6
                {105, 546},   // Level 7
                {569, 432},   // Level 8
                {986, 494}    // Level 9
        };

        // Số sao đạt được của mỗi level (bạn có thể đọc từ file save)

        int[] starsAchieved = new int[]{0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < User.getUser().getLevelPoint().length(); i++) {
            starsAchieved[i] = Character.getNumericValue(User.getUser().getLevelPoint().charAt(i));
        }

        double starSize = 30;   // Kích thước mỗi ngôi sao
        double spacing = 35;    // Khoảng cách giữa các sao

        for (int i = 0; i < positions.length; i++) {
            double centerX = positions[i][0];
            double centerY = positions[i][1];

            // Vẽ 3 sao theo hàng ngang
            for (int s = 0; s < 3; s++) {
                double x = centerX + (s - 1) * spacing - starSize / 2;
                double y = centerY - starSize / 2;

                // Nếu số sao đạt được >= s+1 thì sáng, ngược lại tối
                Image star = (starsAchieved[i] > s) ? starOnImg : starOffImg;
                gc.drawImage(star, x, y, starSize, starSize);
            }
        }
    }

    public void drawStar() {
        System.out.println(User.getUser().getLevelPoint());

    }

    private void levelLayoutEvents() {
        levelLayout.setOnMouseClicked(mouseEvent -> {
            //System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        levelLayout.setOnMouseMoved( mouseMove -> {

        });

        ImageView[] levels = {level1, level2, level3, level4, level5, level6, level7, level8, level9};

        for (int i = 0; i < levels.length; i++) {
            final int levelIndex = i + 1;
            levels[i].setOnMouseClicked(event -> {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/game.fxml"));
                    Parent root = loader.load();
                    GameController gameController = loader.getController();
                    gameController.setLevel(levelIndex);
                    gameController.startGame();
                    User.getUser().setCurrentLevel(levelIndex);
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
