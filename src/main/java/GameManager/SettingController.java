package GameManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class SettingController {

    @FXML
    private Slider bgmSlider;

    @FXML
    private Slider sfxSlider;

    @FXML
    private Button btnClose;

    private final AudioController audio = AudioController.getInstance();

    @FXML
    private void initialize() {
        // Set giá trị ban đầu
        bgmSlider.setValue(audio.getBgmVolume());
        sfxSlider.setValue(audio.getSfxVolume());

        // Lắng nghe thay đổi
        bgmSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                audio.setBgmVolume(newVal.doubleValue())
        );

        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                audio.setSfxVolume(newVal.doubleValue())
        );

        btnClose.setOnAction(e -> btnClose.getScene().getWindow().hide());
    }
}