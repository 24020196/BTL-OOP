package RenderView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import GameManager.AudioController;

public class Login extends Application {
    private static final AudioController audio = AudioController.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        audio.playMenuMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/LoginView.fxml"));
        Scene scene = new Scene(loader.load(), 300, 450); // Kích thước nhỏ
        stage.setTitle("Arkanoid");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
