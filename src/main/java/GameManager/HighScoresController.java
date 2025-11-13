package GameManager;

import Entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.Queue;

/**
 * Controller cho màn hình Bảng xếp hạng (highScores.fxml).
 * Hiển thị 5 người dùng có điểm cao nhất.
 */
public class HighScoresController {

    // Các Label để hiển thị tên và điểm, được tiêm (inject) từ FXML
    @FXML private Label name1, name2, name3, name4, name5;
    @FXML private Label score1, score2, score3, score4, score5;

    /**
     * Phương thức này được gọi tự động khi FXML được tải xong.
     * Tải và hiển thị điểm cao.
     */
    public void initialize() {
        loadHighScores();
    }

    /**
     * Xử lý sự kiện khi nhấn nút "Quay lại" (Back to Menu).
     * Chuyển cảnh về màn hình Menu.
     */
    @FXML
    private void onBackToMenu() {
        try {FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/Menu.fxml"));
            Scene menuScene = new Scene(loader.load(), 1280, 720);
            MenuController menu = loader.getController();
            menu.clickEvents(); // (Có vẻ hàm này không cần thiết ở đây, nên gọi connectDatabase)
            Stage stage = (Stage) name1.getScene().getWindow();
            stage.setScene(menuScene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tải dữ liệu điểm cao từ hàng đợi (Queue) trong User Singleton
     * và điền vào các Label tương ứng.
     */
    private void loadHighScores() {
        Label[] nameLabels = {name1, name2, name3, name4, name5};
        Label[] scoreLabels = {score1, score2, score3, score4, score5};

        int i = 0;
        for (Pair<String, Integer> temp : User.getUser().highScores) {
            nameLabels[i].setText(temp.getKey());
            scoreLabels[i].setText(String.valueOf(temp.getValue()));
            i++;
            if (i >= 5) break; // Đảm bảo không vượt quá 5
        }
    }
}