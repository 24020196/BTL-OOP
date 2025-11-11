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

    @FXML
    private void initialize() {
        bgmSlider.setValue(audio.getBgmVolume());
        sfxSlider.setValue(audio.getSfxVolume());
        bgmSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                audio.setBgmVolume(newVal.doubleValue())
        );

        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                audio.setSfxVolume(newVal.doubleValue())
        );

        btnBack.setOnAction(e -> {
            AnchorPane settingPane = (AnchorPane) btnBack.getScene().getRoot().lookup("#setting");
            AnchorPane menuPane = (AnchorPane) btnBack.getScene().getRoot().lookup("#menuLayout");
            settingPane.setVisible(false);
            menuPane.setVisible(true);
        });

        btnLogout.setOnAction(e -> {
            try {
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/LoginView.fxml"));
                Scene scene = new Scene(loader.load(), 300, 450);
                stage.setScene(scene);
                stage.setTitle("ArkanoidGame - Đăng nhập");
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