package GameManager;

import Entity.User;
import GameDatabase.UserDataAccessObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.*;

/**
 * Controller cho màn hình Đăng nhập (LoginView.fxml).
 * Xử lý xác thực người dùng và chức năng "Nhớ tài khoản".
 */
public class LoginController {

    public ImageView backgroundLogin;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel; // Hiển thị lỗi
    @FXML private CheckBox rememberCheck; // Ô "Nhớ tài khoản"

    private UserDataAccessObject userDataAccessObject = new UserDataAccessObject();

    /**
     * Được gọi khi FXML được tải.
     * Kiểm tra tệp "usercache.txt" để tự động điền thông tin đăng nhập
     * nếu người dùng đã chọn "Nhớ tài khoản" lần trước.
     */
    @FXML
    public void initialize() {
        File file = new File("usercache.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                usernameField.setText(br.readLine());
                passwordField.setText(br.readLine());
                rememberCheck.setSelected(true); // Tự động check lại
            } catch (Exception ignored) {
                // Bỏ qua nếu có lỗi đọc tệp cache
            }
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút "Đăng nhập".
     *
     * @param event Sự kiện (để lấy Stage hiện tại).
     */
    @FXML
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (userDataAccessObject.login(username, password)) {
            if (rememberCheck.isSelected()) {
                try (PrintWriter pw = new PrintWriter("usercache.txt")) {
                    pw.println(username);
                    pw.println(password);
                } catch (Exception ignored) {}
            } else {
                new File("usercache.txt").delete();
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/Menu.fxml"));
                Scene menuScene = new Scene(loader.load(), 1280, 720);
                MenuController menu = loader.getController();
                User.getUser().setUsername(username); // Lưu tên người dùng vào Singleton
                menu.connectDatabase(); // Kết nối CSDL để tải điểm
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(menuScene);
                stage.centerOnScreen();
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            messageLabel.setText("Sai tài khoản hoặc mật khẩu!");
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút "Đăng ký".
     * Chuyển sang màn hình Đăng ký (RegisterView.fxml).
     *
     * @param event Sự kiện (để lấy Stage hiện tại).
     */
    @FXML
    public void goRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/RegisterView.fxml"));
            Scene scene = new Scene(loader.load(), 300, 450);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}