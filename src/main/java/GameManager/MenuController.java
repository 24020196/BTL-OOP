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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import static javafx.application.Application.launch;


/**
 * Controller cho màn hình Menu chính (Menu.fxml).
 * Xử lý điều hướng, hiệu ứng nút và tải dữ liệu người dùng từ cơ sở dữ liệu.
 */
public class MenuController {
    @FXML AnchorPane mainLayout;
    @FXML AnchorPane menuLayout; // Pane chính của menu
    @FXML AnchorPane setting; // Pane dùng để chứa màn hình Setting
    @FXML ImageView menuBackground;
    @FXML ImageView btnPlayGame;
    @FXML ImageView btnHighScores;
    @FXML ImageView btnSetting;
    @FXML ImageView btnExit;
    @FXML Label loadingText; // Hiển thị khi tải CSDL

    private ScoreDataAccessObject data = new ScoreDataAccessObject();

    /**
     * Được gọi khi FXML được tải.
     * Cài đặt kích thước ban đầu và các sự kiện di chuột.
     */
    public void initialize() {
        setsize();
        mainLayoutEvents();
    }

    /**
     * Kết nối cơ sở dữ liệu trên một luồng riêng để tải điểm (levelPoint)
     * và điểm cao (highScores) của người dùng.
     * Kích hoạt các sự kiện click ({@link #clickEvents()}) sau khi tải xong.
     */
    public void connectDatabase() {
        Thread thread = new Thread(() -> {
            {
                loadingText.setVisible(true);
                // Tải điểm chi tiết từng màn
                data.getPoint(User.getUser().getUsername(),User.getUser());
                // Xóa cache điểm cao cũ và tải lại
                User.getUser().highScores.clear();
                data.getHighScorces();
                // Quay lại luồng JavaFX để kích hoạt các nút
                Platform.runLater(() -> {
                    clickEvents();
                    loadingText.setVisible(false);
                });

            }
        });
        thread.start();
    }

    /**
     * Thiết lập sự kiện di chuyển chuột trên layout chính để xử lý
     * hiệu ứng "hover" (hiển thị/ẩn) cho các nút ImageView.
     */
    public void mainLayoutEvents() {
        mainLayout.setOnMouseMoved( mouseMove -> {
            // Các ImageView này (là ảnh PNG trong suốt) sẽ hiện lên
            // khi chuột di vào vùng của chúng
            setVisibleImageView(btnPlayGame, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnHighScores, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnSetting, mouseMove.getX(), mouseMove.getY());
            setVisibleImageView(btnExit, mouseMove.getX(), mouseMove.getY());
        });
    }

    /**
     * Gán sự kiện click cho các nút (Play, High Scores, Setting, Exit)
     * để điều hướng đến các màn hình tương ứng hoặc thoát ứng dụng.
     * Hàm này chỉ được gọi sau khi {@link #connectDatabase()} hoàn tất.
     */
    public void clickEvents() {
        mainLayout.setOnMouseClicked(mouseEvent -> {
            //System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        // Chuyển đến màn hình Bản đồ Level
        btnPlayGame.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/levelMap.fxml"));
                Parent root = loader.load();
                //LevelMapController levelMapController = loader.getController();
                //levelMapController.drawStar(); // (Đã gọi trong initialize của LevelMapController)
                Stage stage = (Stage) mainLayout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Chuyển đến màn hình Bảng xếp hạng
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

        // Tải và hiển thị màn hình Cài đặt (lồng vào pane 'setting')
        btnSetting.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/SettingView.fxml"));
                AnchorPane settingPane = loader.load();
                setting.getChildren().setAll(settingPane); // Đặt nội dung FXML vào pane 'setting'
                menuLayout.setVisible(false); // Ẩn menu chính
                setting.setVisible(true); // Hiển thị pane cài đặt

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Thoát ứng dụng
        btnExit.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
    }

    /**
     * (Hàm nội bộ) Cài đặt kích thước và vị trí ban đầu cho các thành phần giao diện.
     */
    void setsize() {
        update(menuBackground, 0 , 0 , 1280 , 720);
        update(btnPlayGame, 356, 207, 495, 106);
        update(btnHighScores, 356, 336, 495, 106);
        update(btnSetting, 356, 462, 495, 106);
        update(btnExit, 356, 590, 495, 106);

    }

    /**
     * (Hàm nội bộ) Hàm trợ giúp để cập nhật vị trí và kích thước của ImageView.
     */
    void update(ImageView imageView, double x, double y, double width, double height) {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }


    /**
     * (Hàm nội bộ) Xử lý logic hiệu ứng hover: hiển thị ImageView nếu chuột
     * nằm trong vùng (bounds) của nó.
     */
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