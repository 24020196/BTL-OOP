package GameManager;

import GameDatabase.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDAO.login(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/menu.fxml"));
                Scene menuScene = new Scene(loader.load(), 1280, 720);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(menuScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Sai tài khoản hoặc mật khẩu!");
        }
    }

    @FXML
    public void goRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/RegisterView.fxml"));
            Scene scene = new Scene(loader.load(), 350, 320);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
