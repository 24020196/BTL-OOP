package GameManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Controller cho màn hình Cài đặt (SettingView.fxml).
 * Quản lý cài đặt âm lượng (nhạc nền, hiệu ứng) và chức năng đăng xuất.
 * FXML này được tải và lồng vào bên trong 'Menu.fxml'.
 */
public class SettingController {

    @FXML
    private Slider bgmSlider;

    @FXML
    private Slider sfxSlider;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogout;
    private final AudioController audio = AudioController.getInstance();

    /**
     * Được gọi khi FXML được tải.
     * 1. Khởi tạo giá trị cho các thanh trượt (Slider) từ AudioController.
     * 2. Thêm các listener để cập nhật âm lượng ngay lập tức khi thay đổi.
     * 3. Thiết lập sự kiện cho nút "Quay lại" và "Đăng xuất".
     */
    @FXML
    private void initialize() {
        // 1. Đặt giá trị ban đầu cho thanh trượt
        bgmSlider.setValue(audio.getBgmVolume());
        sfxSlider.setValue(audio.getSfxVolume());

        // 2. Thêm listener cho thanh trượt nhạc nền (BGM)
        bgmSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                audio.setBgmVolume(newVal.doubleValue())
        );

        // 2. Thêm listener cho thanh trượt hiệu ứng (SFX)
        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                audio.setSfxVolume(newVal.doubleValue())
        );

        // 3. Xử lý nút "Quay lại" (Back)
        btnBack.setOnAction(e -> {
            // Tìm các Pane (Setting và Menu) từ Scene gốc
            AnchorPane settingPane = (AnchorPane) btnBack.getScene().getRoot().lookup("#setting");
            AnchorPane menuPane = (AnchorPane) btnBack.getScene().getRoot().lookup("#menuLayout");
            settingPane.setVisible(false); // Ẩn cài đặt
            menuPane.setVisible(true); // Hiện lại menu
        });

        // 3. Xử lý nút "Đăng xuất" (Logout)
        btnLogout.setOnAction(e -> {
            try {
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/LoginView.fxml"));
                Scene scene = new Scene(loader.load(), 300, 450);
                stage.setScene(scene);
                stage.setTitle("ArkanoidGame - Đăng nhập");

                // Căn giữa lại cửa sổ đăng nhập
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((screenBounds.getWidth() - 300) / 2);
                stage.setY((screenBounds.getHeight() - 450) / 2);

                audio.stopMusic();
                audio.playMenuMusic();
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}