package GameManager;

import Entity.*;
import GameDatabase.ScoreDataAccessObject;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class GameController {
    private Canvas canvas = new Canvas(1280,720);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Brick[][] bricks = new Brick[8][12];
    private int[][] bricktypes;
    private Ball ball = new Ball(670, 680, 20,20);
    private Paddle paddle = new Paddle(640, 700, 120,20);
    private Queue<PowerUp> powerUp = new LinkedList<>();
    private Queue<PowerUp> powerUpDelete = new LinkedList<>();
    private int powerUpLeft = 25;
    private Queue<GameObject> bullet = new LinkedList<>();
    private Queue<GameObject> bulletDelete = new LinkedList<>();
    private int bulletLeft = 0;
    private int bulletCooldown = 0;
    private static final Image bulletImg = new Image(Brick.class.getResource("/res/ball1.png").toExternalForm());
    private VariableValue variableValue = new VariableValue();
    private static final Image ballImg = new Image(Brick.class.getResource("/res/ball0.png").toExternalForm());
    private static final Image paddleImg = new Image(Brick.class.getResource("/res/paddle.png").toExternalForm());
    private int level = 0;
    private static final AudioController audio = AudioController.getInstance();
    private Thread LogicLoop;
    private AnimationTimer uiLoop;
    private final Object lock = new Object();

    private static final Image[] background =  {
            new Image(GameController.class.getResource("/res/level1.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level2.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level3.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level4.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level5.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level6.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level7.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level8.png").toExternalForm()),
            new Image(GameController.class.getResource("/res/level9.png").toExternalForm())};

    @FXML AnchorPane gameLayout;

    public void initialize() {
        AudioController.getInstance().playGameMusic();
        gameLayout.getChildren().add(canvas);
        uiLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                synchronized(lock) {
                    draw();
                    endgame();
                }
            }
        };
        uiLoop.start();

        LogicLoop = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized(lock) {
                    paddle.move();
                    renderPowerUp();
                    renderBullet();
                    renderBall();
                }
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        LogicLoop.start();

        gameLayoutEvents();
    }

    public void startGame() {

        bricktypes = variableValue.brickMap(level);
        for(int i = 0; i < 8; i++)
        for(int j = 0; j < 12; j++) {
            bricks[i][j] = new Brick(10+ j * 105, i * 45+60, 102, 42,bricktypes[i][j]);
            if(bricktypes[i][j] == 0)bricks[i][j].setHitPoints(0);
        }
    }

    private void gameLayoutEvents() {
        canvas.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A:
                    paddle.setLeft(true);
                    paddle.setRight(false);
                    break;
                case D:
                    paddle.setRight(true);
                    paddle.setLeft(false);
                    break;
                case SPACE:
                    ball.setOnPaddle(false);
                    break;
            }
        });
        canvas.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A:
                    paddle.setLeft(false);
                    paddle.setRight(false);

                    break;
                case D:
                    paddle.setRight(false);
                    paddle.setLeft(false);
                    break;
            }
        });
        canvas.setFocusTraversable(true);
        canvas.requestFocus();

        gameLayout.setOnMouseMoved( mouseMove -> {

        });

        gameLayout.setOnMouseClicked(mouseEvent -> {
                ScoreDataAccessObject data = new ScoreDataAccessObject();
                //data.getPoint(1);
        });
    }

    private void renderBall() {
        if(!ball.isOnPaddle()) {
            ball.update(canvas.getWidth(), canvas.getHeight(), paddle, bricks);
            if(ball.newDestroyBrick && powerUpLeft > 0) {
                if(powerUpLeft>=Math.random()*brickLeft()) {
                    powerUpLeft--;
                    System.out.println(powerUpLeft);
                    //powerUp.add(new PowerUp(ball.getX(), ball.getY(),(int) 5));
                    powerUp.add(new PowerUp(ball.getX(), ball.getY(),(int) (Math.random()*6)));
                }
            }
            ball.newDestroyBrick = false;
        } else {
            ball.reset(paddle);
        }
    }

    private void renderPowerUp() {
        for(PowerUp tmpPowerUp:powerUp) {
            tmpPowerUp.move(0,3);
            if(tmpPowerUp.checkCollision(paddle)) {
                audio.playPowerUp();
                setPowerUp(tmpPowerUp.getType());
                powerUpDelete.add(tmpPowerUp);
            }else
            if(tmpPowerUp.getY()>paddle.getY()) {
                powerUpDelete.add(tmpPowerUp);
            }
        }
        for(PowerUp tmpPowerUpDelete : powerUpDelete) {
            powerUp.remove(tmpPowerUpDelete);
        }
        powerUpDelete.clear();

    }

    private  void renderBullet() {
        if(bulletLeft > 0 && bulletCooldown >= 300) {
            audio.playPowerUpShoot();
            bulletCooldown = 0;
            bulletLeft--;
            System.out.println(bulletLeft);
            bullet.add(new GameObject(paddle.getX() + paddle.getWidth()/2, paddle.getY() - 10, 10,10));
        } else bulletCooldown += 25;
        boolean tempBreak = false;
        for(GameObject tmpBullet : bullet) {
            tmpBullet.move(0,-12);
            for(int i = 0; i < 8; i++) {
                for (int j = 0; j < 12; j++) {
                    Brick tempBrick = bricks[i][j];
                    if(!tempBrick.isDestroyed() && tempBrick.checkCollision(tmpBullet)) {
                        tempBrick.hit();
                        bulletDelete.add(tmpBullet);
                        tempBreak = true;
                        break;
                    }
                }
                if(tempBreak)break;
            }
            if(tmpBullet.getY() < 0 && !tempBreak)
                bulletDelete.add(tmpBullet);
        }
        for(GameObject tmpBulletDelete : bulletDelete)
            bullet.remove(tmpBulletDelete);
        bulletDelete.clear();
    }

    public void endgame() {
        if(brickLeft() == 0 || ball.getLives() == 0) {
            System.out.println("Game Over");
            uiLoop.stop();
            LogicLoop.interrupt();
            if(ball.getLives() > User.getUser().getLevelPoint().charAt(level - 1) - '0') {
                User.getUser().setLevelPoint(level - 1, ball.getLives());
            }
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/RenderView/endGame.fxml"));
                    Parent root = loader.load();
                    EndGameController endGameController = loader.getController();
                    if (ball.getLives() > 0) {
                        endGameController.winGame();
                        User.getUser().setCurrentLevel(Math.max(9,level + 1));
                    } else endGameController.eventListener();
                    Stage stage = (Stage) gameLayout.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


    }

    private void draw() {
        Brick tempBrick;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(background[level-1], 0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < ball.getLives(); i++)
            gc.drawImage(ballImg, 1280 - (ball.getWidth() + 5) * i, 10,
                    ball.getWidth(), ball.getHeight());
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 12; j++) {
                tempBrick = bricks[i][j];
                gc.setGlobalAlpha(tempBrick.getOpacity());
                if (!tempBrick.isDestroyed()) {
                    gc.drawImage(tempBrick.getImage(), tempBrick.getX(), tempBrick.getY(),
                            tempBrick.getWidth(), tempBrick.getHeight());
                }
            }
        }
        gc.setGlobalAlpha(1);
        for(PowerUp tmpPowerUp:powerUp) {
            gc.drawImage(tmpPowerUp.getImage(), tmpPowerUp.getX(), tmpPowerUp.getY(),
                    tmpPowerUp.getWidth(), tmpPowerUp.getHeight());
        }
        for(GameObject tmpBullet:bullet) {

            gc.drawImage(bulletImg, tmpBullet.getX(), tmpBullet.getY(),
                    tmpBullet.getWidth(), tmpBullet.getHeight());
        }
        gc.drawImage(ball.getImg(0), ball.getX(), ball.getY(),
                ball.getWidth(), ball.getHeight());

        gc.drawImage(paddleImg, paddle.getX() , paddle.getY(),
                paddle.getWidth(), paddle.getHeight());
    }



    /** types
     *      powerUp_BigPaddle = 0;
     *      powerUp_SmallPaddle = 1;
     *      powerUp_Fast = 2;
     *      powerUp_Slow = 3;
     *      powerUp_Shoot = 4;
     *      powerUp_FireBall = 5;
     *      powerUp_TripleBall = 6;
     */
    public void setPowerUp(int type) {
        switch (type) {
            case 0:
                paddle.setWidth(paddle.getWidth()*1.5);
                break;
            case 1:
                paddle.setWidth(paddle.getWidth()*0.75);
                break;
            case 2:
                ball.setSpeed(ball.getSpeed() * 1.2);
                break;
            case 3:
                ball.setSpeed(ball.getSpeed() * 0.8);
                break;
            case 4:
                bulletLeft = 30;
                break;
            case 5:
                ball.setFireBall(true);
                break;
            case 6:

                break;
        }
    }

    public int brickLeft() {
        int temp = 0;
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 12; j++)
                if(bricks[i][j].getHp() > 0 && bricks[i][j].getType() != 6)temp++;
        return temp;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
