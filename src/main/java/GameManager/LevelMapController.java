package GameManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LevelMapController {
    @FXML
    AnchorPane levelLayout;
    @FXML
    ImageView levelMapBackground;
    @FXML
    ImageView level1;

    public void initialize() {
        setsize();
        levelLayoutEvents();
    }

    private void levelLayoutEvents() {
        levelLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        levelLayout.setOnMouseMoved( mouseMove -> {


        });

        level1.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/game.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) levelLayout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void setsize() {

        update(levelMapBackground,0,0,1280,720);
        update(level1,50,100,180,125);
    }

    void update(ImageView imageView, double x, double y, double width, double height) {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    void setVisibleImageView(ImageView imageView, double x, double y) {
        double left = imageView.getLayoutX();
        double right = imageView.getLayoutX() + imageView.getFitWidth();
        double up = imageView.getLayoutY();
        double down = imageView.getLayoutY() + imageView.getFitHeight();
        imageView.setVisible(false);
        if(x >= left && x <= right)
            if(y >= up & y <= down) imageView.setVisible(true);
    }
}