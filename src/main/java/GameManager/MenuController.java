package GameManager;

import Entity.Brick;
import Entity.GameObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static javafx.application.Application.launch;


public class MenuController {
    @FXML
    AnchorPane menuLayout;
    @FXML
    ImageView menuBackground;
    @FXML
    ImageView btnPlayGame;

    public void initialize() {
        btnPlayGame.setVisible(false);

        menuLayoutEvents();
    }

    private void menuLayoutEvents() {
        menuLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        menuLayout.setOnMouseMoved( mouseMove -> {
            btnPlayGame.setVisible(false);
            if(mouseMove.getX() >= btnPlayGame.getLayoutX() && mouseMove.getX() <= btnPlayGame.getLayoutX()+btnPlayGame.getFitWidth())
                if (mouseMove.getY() >= btnPlayGame.getLayoutY() && mouseMove.getY() <= btnPlayGame.getLayoutY()+btnPlayGame.getFitHeight())
                    btnPlayGame.setVisible(true);
        });

        menuLayout.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            menuBackground.fitWidthProperty().bind(menuLayout.widthProperty());

            btnPlayGame.setLayoutX(356 * newWidth.doubleValue() / 1280);
            btnPlayGame.setFitWidth(495 * newWidth.doubleValue() / 1280);

        });

        menuLayout.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            menuBackground.fitHeightProperty().bind(menuLayout.heightProperty());

            btnPlayGame.setLayoutY(207 * newHeight.doubleValue() / 720);
            btnPlayGame.setFitHeight(106 * newHeight.doubleValue() / 720);
        });

        btnPlayGame.setOnMouseClicked(event -> {
            System.out.println("Brick clicked!");
        });
    }

    public void startGame() {

    }
}
