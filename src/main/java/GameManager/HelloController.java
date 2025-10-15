package GameManager;

import Entity.Brick;
import Entity.GameObject;
import RenderView.Menu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static javafx.application.Application.launch;

public class HelloController {
    @FXML
    Button btn;
    @FXML
    VBox vbox;
    public void initialize() {
        GameObject gameObject= new Brick(10,10,100,200,1,1);
        btn.setPrefSize(gameObject.getWidth(),gameObject.getHeight());
        setupEvents();
    }

    private void setupEvents() {
        vbox.setOnMouseMoved(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });
        btn.setOnAction(event -> {
            System.out.println("Brick clicked!");

        });
    }

    public void startGame() {

    }
}
