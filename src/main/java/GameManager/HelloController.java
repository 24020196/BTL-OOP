package GameManager;

import Entity.Brick;
import Entity.GameObject;
import RenderView.Menu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import static javafx.application.Application.launch;

public class HelloController {
    @FXML
    Button btn;
    @FXML
    AnchorPane menuLayout;
    public void initialize() {
        GameObject gameObject= new Brick(-500,10,1000,200,1,1);
        btn.setPrefSize(gameObject.getWidth(),gameObject.getHeight());
        btn.setLayoutX(gameObject.getX());
        btn.setLayoutY(gameObject.getY());
        setupEvents();
    }

    private void setupEvents() {
        menuLayout.setOnMouseMoved(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });
        btn.setOnAction(event -> {
            System.out.println("Brick clicked!");

        });
    }

    public void startGame() {

    }
}
