package GameManager;

import GameDatabase.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField tfNewUser;
    @FXML private PasswordField tfNewPass;
    @FXML private PasswordField tfConfirmPass;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void onRegister() {
        String user = tfNewUser.getText();
        String pass = tfNewPass.getText();
        String confirm = tfConfirmPass.getText();

        //Kiểm tra nhập lại mật khẩu
        if (!pass.equals(confirm)) {
            showAlert("Lỗi", "Mật khẩu nhập lại không khớp!");
            return;
        }

        //Gọi database
        boolean success = userDAO.register(user, pass);

        if (success) {
            showAlert("Thành công", "Đăng ký thành công! Mời bạn đăng nhập.");
            goLogin();
        } else {
            showAlert("Lỗi", "Tên đăng nhập đã tồn tại hoặc không hợp lệ!");
        }
    }

    @FXML
    public void goLogin() {
        try {
            Stage stage = (Stage) tfNewUser.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/LoginView.fxml"));
            Scene scene = new Scene(loader.load(), 300, 250);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
