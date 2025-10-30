package GameManager;

import GameDatabase.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.*;

public class LoginController {

    public ImageView backgroundLogin;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private CheckBox rememberCheck;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        File file = new File("usercache.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                usernameField.setText(br.readLine());
                passwordField.setText(br.readLine());
                rememberCheck.setSelected(true); // tự tick lại
            } catch (Exception ignored) {}
        }
    }

    @FXML
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDAO.login(username, password)) {
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
            Scene scene = new Scene(loader.load(), 300, 450);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
