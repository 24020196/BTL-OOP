package GameManager;

import Entity.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Controller cho màn hình Bản đồ chọn màn chơi (levelMap.fxml).
 * Hiển thị các màn chơi và số sao đạt được.
 */
public class LevelMapController {

    @FXML AnchorPane levelLayout;
    @FXML ImageView levelMapBackground;
    // Các ImageView cho từng màn chơi, dùng làm "nút"
    @FXML ImageView level1;
    @FXML ImageView level2;
    @FXML ImageView level3;
    @FXML ImageView level4;
    @FXML ImageView level5;
    @FXML ImageView level6;
    @FXML ImageView level7;
    @FXML ImageView level8;
    @FXML ImageView level9;

    // Sử dụng Canvas để vẽ các ngôi sao LÊN TRÊN ảnh nền
    private Canvas canvas = new Canvas(1280, 720);;
    private GraphicsContext gc = canvas.getGraphicsContext2D();;
    private Image starOnImg = new Image(getClass().getResource("/res/star_full.png").toExternalForm());
    private Image starOffImg = new Image(getClass().getResource("/res/star_empty.png").toExternalForm());

    /**
     * Được gọi khi FXML được tải.
     * Thêm canvas vào layout, vẽ các sao và thiết lập sự kiện click.
     */
    public void initialize() {
        canvas.setMouseTransparent(true); // Làm canvas trong suốt với chuột
        levelLayout.getChildren().add(canvas);
        drawStarsAllLevels(gc); // Vẽ các sao
        levelLayoutEvents(); // Thiết lập sự kiện click cho các ImageView
    }

    /**
     * Vẽ 3 ngôi sao (đầy hoặc rỗng) cho mỗi màn chơi dựa trên
     * chuỗi điểm (levelPoint) đã lưu của người dùng.
     *
     * @param gc Context đồ họa của canvas.
     */
    private void drawStarsAllLevels(GraphicsContext gc) {
        // Tọa độ (trung tâm) để vẽ 3 ngôi sao cho mỗi màn
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
        int[] starsAchieved = new int[]{0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < User.getUser().getLevelPoint().length(); i++) {
            starsAchieved[i] = Character.getNumericValue(User.getUser().getLevelPoint().charAt(i));
        }

        double starSize = 30;
        double spacing = 35;

        for (int i = 0; i < positions.length; i++) {
            double centerX = positions[i][0];
            double centerY = positions[i][1];
            for (int s = 0; s < 3; s++) {
                double x = centerX + (s - 1) * spacing - starSize / 2;
                double y = centerY - starSize / 2;
                Image star = (starsAchieved[i] > s) ? starOnImg : starOffImg;
                gc.drawImage(star, x, y, starSize, starSize);
            }
        }
    }

    /**
     * (Hàm này có vẻ không được sử dụng và trùng lặp với logic trong initialize)
     */
    public void drawStar() {
        // System.out.println(User.getUser().getLevelPoint());
    }

    /**
     * Thiết lập sự kiện click cho các ImageView (level1, level2, ...).
     * Khi click, tải màn chơi (game.fxml) với chỉ số màn tương ứng.
     */
    private void levelLayoutEvents() {
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

    /**
     * Xử lý sự kiện khi nhấn nút "Quay lại" (Back to Menu).
     * Chuyển cảnh về màn hình Menu.
     */
    @FXML
    private void onBackToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/Menu.fxml"));
            Scene menuScene = new Scene(loader.load(), 1280, 720);
            MenuController menu = loader.getController();
            menu.connectDatabase();
            Stage stage = (Stage) levelLayout.getScene().getWindow();
            stage.setScene(menuScene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}