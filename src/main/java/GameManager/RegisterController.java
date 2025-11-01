package GameManager;

import GameDatabase.UserDataAccessObject;
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

    private UserDataAccessObject userDataAccessObject = new UserDataAccessObject();

    @FXML
    public void onRegister() {
        String user = tfNewUser.getText().trim();
        String pass = tfNewPass.getText().trim();
        String confirm = tfConfirmPass.getText().trim();

        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            showAlert("Lỗi", "Không được để trống bất kỳ trường nào!");
            return;
        }

        if (!pass.equals(confirm)) {
            showAlert("Lỗi", "Mật khẩu nhập lại không khớp!");
            return;
        }

        String result = userDataAccessObject.register(user, pass);

        if (result.equals("SUCCESS")) {
            showAlert("Thành công", "Đăng ký thành công! Mời bạn đăng nhập.");
            goLogin();
        } else {
            showAlert("Lỗi đăng ký", result);
        }
    }


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

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
