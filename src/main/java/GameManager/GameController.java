package GameManager;

import Entity.Ball;
import Entity.Brick;
import Entity.Paddle;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class GameController {
    private Canvas canvas = new Canvas(1165,720);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Brick[][] bricks = new Brick[8][12];
    private Ball ball = new Ball(670, 680, 20,20);
    private Paddle paddle = new Paddle(640, 700, 80,20);
    private static final Image[] brickImg = {
            new Image(Brick.class.getResource("/res/brick0.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brick1.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brick2.png").toExternalForm())
    };
    private static final Image ballImg = new Image(Brick.class.getResource("/res/ball0.png").toExternalForm());
    private static final Image paddleImg = new Image(Brick.class.getResource("/res/paddle.png").toExternalForm());
    @FXML
    AnchorPane gameLayout;

    public void initialize() {
        for(int i = 0; i < 8; i++)
        for(int j = 0; j < 11; j++)
            bricks[i][j] = new Brick(5+ j * 105, i * 45, 102, 42,3);

        gameLayout.getChildren().add(canvas);


        Thread loopThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if(!ball.isOnPaddle()) {
                    ball.update(canvas.getWidth(), canvas.getHeight(), paddle, bricks);
                } else ball.reset(paddle);
                draw(gc);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        loopThread.start();
        //loopThread.interrupt();

        gameLayoutEvents();
    }

    private void draw(GraphicsContext gc) {
        Brick tempBrick;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 11; j++) {
                tempBrick = bricks[i][j];
                gc.setGlobalAlpha(tempBrick.getOpacity());
                if(!tempBrick.isDestroyed())
                gc.drawImage(brickImg[(int) tempBrick.getType()-1], tempBrick.getX()  , tempBrick.getY(),
                        tempBrick.getWidth() , tempBrick.getHeight());
            }
        gc.setGlobalAlpha(1);
        gc.drawImage(ballImg, ball.getX(), ball.getY(),
                ball.getWidth(), ball.getHeight());
        gc.drawImage(paddleImg, paddle.getX() , paddle.getY(),
                paddle.getWidth(), paddle.getHeight());
    }

    private void gameLayoutEvents() {

        gameLayout.setOnMouseMoved( mouseMove -> {
            paddle.setX(Math.min(mouseMove.getX(),canvas.getWidth()-paddle.getWidth()));
        });

        gameLayout.setOnMouseClicked(mouseEvent -> {
           // System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
            ball.setOnPaddle(false);
        });
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
