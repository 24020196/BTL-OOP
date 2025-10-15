package GameManager;

import Entity.Brick;
import Entity.GameObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static javafx.application.Application.launch;

public class HelloController {
    @FXML
    Button btn;
    @FXML
    AnchorPane menuLayout;
    @FXML
    ImageView menuBackground;

    public void initialize() {
        GameObject gameObject= new Brick(500,100,1000,200);
        btn.setPrefSize(gameObject.getWidth(),gameObject.getHeight());
        btn.setLayoutX(gameObject.getX());
        btn.setLayoutY(gameObject.getY());
        setupEvents();
    }

    private void setupEvents() {
        menuLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });
        btn.setOnAction(event -> {
            System.out.println("Brick clicked!");

        });
        menuLayout.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            menuBackground.fitWidthProperty().bind(menuLayout.widthProperty());
            btn.setPrefWidth(1000 * newWidth.doubleValue() / 1280);
            btn.setLayoutX(500 * newWidth.doubleValue() / 1280);
        });

        menuLayout.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            menuBackground.fitHeightProperty().bind(menuLayout.heightProperty());
            btn.setPrefHeight(200 * newHeight.doubleValue() / 720);
            btn.setLayoutY(100 * newHeight.doubleValue() / 720);
        });
    }

    public void startGame() {

    }
}
