package GameManager;

import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class GameController {
    Canvas canvas = new Canvas();
    @FXML
    AnchorPane gameLayout;

    public void initialize() {

        gameLayout.getChildren().add(canvas);

        gameLayoutEvents();
    }

    private void draw(GraphicsContext gc) {
        Image brickImg = new Image(getClass().getResource("/res/brick1.png").toExternalForm());
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 12; j++){
            gc.drawImage(brickImg, 10+ j * 105, 40+ i * 45, 102, 42);
        }

    }

    private void gameLayoutEvents() {
        gameLayout.setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
        });

        gameLayout.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            resize(newBounds);
        });

        gameLayout.setOnMouseMoved( mouseMove -> {


        });
    }

    void resize(Bounds newBounds) {

        resize(gameLayout,0,0,1280, 720, newBounds);
        resize(canvas,0,0,1280, 720, newBounds);
    }

    void resize(ImageView imageView, double x, double y, double width, double height, Bounds bounds) {
        imageView.setLayoutX(x * bounds.getWidth() / 1280);
        imageView.setLayoutY(y *  bounds.getHeight() / 720);
        imageView.setFitWidth(width * bounds.getWidth() / 1280);
        imageView.setFitHeight(height*  bounds.getHeight() / 720);
    }

    void resize(Canvas canvas, double x, double y, double width, double height, Bounds bounds) {
        canvas.setLayoutX(x * bounds.getWidth() / 1280);
        canvas.setLayoutY(y *  bounds.getHeight() / 720);
        canvas.setWidth(width * bounds.getWidth() / 1280);
        canvas.setHeight(height*  bounds.getHeight() / 720);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        draw(gc);
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
