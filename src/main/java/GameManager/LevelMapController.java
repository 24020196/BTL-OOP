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

        Bounds bound = new BoundingBox(0, 0, levelLayout.getWidth(), levelLayout.getHeight());
        resize(bound);
        levelLayoutEvents();
    }

    private void levelLayoutEvents() {
        levelLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        levelLayout.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            resize(newBounds);
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

    void resize(Bounds newBounds) {

        resize(levelLayout,0,0,1280, 720, newBounds);
        resize(levelMapBackground,0,0,1280,720,newBounds);
        resize(level1,50,100,180,125,newBounds);
    }

    void resize(ImageView imageView, double x, double y, double width, double height, Bounds bounds) {
        imageView.setLayoutX(x * bounds.getWidth() / 1280);
        imageView.setLayoutY(y *  bounds.getHeight() / 720);
        imageView.setFitWidth(width * bounds.getWidth() / 1280);
        imageView.setFitHeight(height*  bounds.getHeight() / 720);
    }

    void resize(AnchorPane anchorPane, double x, double y, double width, double height, Bounds bounds) {
        anchorPane.setLayoutX(x * bounds.getWidth() / 1280);
        anchorPane.setLayoutY(y *  bounds.getHeight() / 720);
        anchorPane.prefWidth(width * bounds.getWidth() / 1280);
        anchorPane.prefHeight(height*  bounds.getHeight() / 720);
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
