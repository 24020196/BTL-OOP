package RenderView;

import GameManager.AudioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Lớp chính (Main class) của ứng dụng, kế thừa từ {@link Application} của JavaFX.
 * Đây là điểm khởi đầu của toàn bộ trò chơi, chịu trách nhiệm tải
 * màn hình đăng nhập (LoginView.fxml) đầu tiên.
 */
public class Login extends Application {

    private static final AudioController audio = AudioController.getInstance();

    /**
     * Phương thức bắt buộc của JavaFX, được gọi khi ứng dụng bắt đầu.
     * Thiết lập và hiển thị cửa sổ (Stage) ban đầu.
     *
     * @param stage Cửa sổ chính (Stage) được cung cấp bởi JavaFX runtime.
     * @throws Exception Nếu không thể tải tệp FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        audio.playMenuMusic();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/LoginView.fxml"));
        Scene scene = new Scene(loader.load(), 300, 450); // Đặt kích thước cho cửa sổ đăng nhập

        stage.setTitle("ArkanoidGame");
        stage.setScene(scene);
        stage.setResizable(false); // Không cho phép thay đổi kích thước
        stage.show(); // Hiển thị cửa sổ
    }

    /**
     * Phương thức main, điểm vào (entry point) chuẩn của một ứng dụng Java.
     * Gọi {@link Application#launch(String...)} để khởi chạy ứng dụng JavaFX.
     *
     * @param args Các đối số dòng lệnh (nếu có).
     */
    public static void main(String[] args) {
        launch(args);
    }
}