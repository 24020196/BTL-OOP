package GameManager;

import GameDatabase.UserDataAccessObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller cho màn hình Đăng ký (RegisterView.fxml).
 * Xử lý việc tạo tài khoản người dùng mới.
 */
public class RegisterController {

    @FXML private TextField tfNewUser;
    @FXML private PasswordField tfNewPass;
    @FXML private PasswordField tfConfirmPass;

    private UserDataAccessObject userDataAccessObject = new UserDataAccessObject();

    /**
     * Xử lý sự kiện khi nhấn nút "Đăng ký".
     * Kiểm tra tính hợp lệ của thông tin (không trống, mật khẩu khớp)
     * và gọi DAO để đăng ký. Hiển thị thông báo thành công hoặc thất bại.
     */
    @FXML
    public void onRegister() {
        String user = tfNewUser.getText().trim();
        String pass = tfNewPass.getText().trim();
        String confirm = tfConfirmPass.getText().trim();

        // Kiểm tra trống
        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            showAlert("Lỗi", "Không được để trống bất kỳ trường nào!");
            return;
        }

        // Kiểm tra khớp mật khẩu
        if (!pass.equals(confirm)) {
            showAlert("Lỗi", "Mật khẩu nhập lại không khớp!");
            return;
        }

        // Gọi DAO để đăng ký
        String result = userDataAccessObject.register(user, pass);

        if (result.equals("SUCCESS")) {
            showAlert("Thành công", "Đăng ký thành công! Mời bạn đăng nhập.");
            goLogin(); // Tự động quay về màn hình đăng nhập
        } else {
            // Hiển thị lỗi từ DAO (ví dụ: "Tên đăng nhập đã tồn tại")
            showAlert("Lỗi đăng ký", result);
        }
    }


    /**
     * Xử lý sự kiện khi nhấn nút "Quay lại Đăng nhập".
     * Chuyển người dùng về màn hình LoginView.fxml.
     */
    @FXML
    public void goLogin() {
        try {
            Stage stage = (Stage) tfNewUser.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/LoginView.fxml"));
            Scene scene = new Scene(loader.load(), 300, 400);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * (Hàm nội bộ) Hàm trợ giúp tiện ích để hiển thị một cửa sổ thông báo (Alert).
     *
     * @param title Tiêu đề của cửa sổ Alert.
     * @param msg   Nội dung thông báo.
     */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}