package Entity;

import javafx.scene.image.Image;
import GameManager.AudioController;
import java.util.ArrayList;
import java.util.List;

public class Ball extends GameObject {
    private double speed;
    private double angle;
    private double vectorX;
    private double vectorY;
    private boolean fireBall = false;
    private int lives;
    private static final Image[] ballImg = {
            new Image(PowerUp.class.getResource("/res/ball0.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/ball1.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/ball2.png").toExternalForm())
    };
    private boolean onPaddle = true;
    public boolean newDestroyBrick = false;
    private AudioController audio = AudioController.getInstance();

    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.speed = 10;
        this.angle = Math.toRadians(90);
        this.vectorX = Math.cos(angle);
        this.vectorY = -Math.sin(angle);
        this.lives = 3;
    }

    public void update(double sceneWidth, double sceneHeight, Paddle paddle, Brick[][] bricks) {
        if(lives>0) {
            move(speed * vectorX, speed * vectorY);
            bounceOff(paddle, bricks, sceneWidth, sceneHeight);
            if (getY() > paddle.getY()) {
                lives--;
                if (lives <= 0) {
                    //System.out.println("Game Over!");
                } else {
                    reset(paddle);
                }
            }
        }
    }

    public void reset(Paddle paddle) {
        setX(paddle.getX() + paddle.getWidth() / 2.0 - getWidth() / 2.0);
        setY(paddle.getY() - getHeight() );
        this.angle = Math.toRadians(90);
        this.vectorX = Math.cos(this.angle);
        this.vectorY = -Math.sin(this.angle);
        this.onPaddle = true;
        this.fireBall = false;
    }
    public void bounceOff(Paddle paddle, Brick[][] bricks, double sceneWidth, double sceneHeight) {
        // chạm khung trái phải
        if (getX() <= 0 || getX() + getWidth() >= sceneWidth) {
            audio.playWallHit();
            move(-speed * vectorX, -speed * vectorY);
            vectorX *= -1;
        }
        if (getY() <= 0) {
            move(-speed * vectorX, -speed * vectorY);
            vectorY*=-1;
        }

        if (checkCollision(paddle)) {
            if (checkCollision(paddle)) {
                audio.playPaddleHit();
                double ballCenter = getX() + getWidth() / 2.0;
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
                double distance = (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0); // -1..1
                double bounceAngle = Math.toRadians(60 * distance); // góc lệch tối đa ±60°
                move(-speed * vectorX, -speed * vectorY);
                vectorX = Math.sin(bounceAngle);
                vectorY = -Math.cos(bounceAngle);
                setY(paddle.getY() - getHeight() - 1);
            }
            setY(paddle.getY() - getHeight()-1);
        }

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 12; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed() && checkCollision(brick)) {
                    brick.hit();
                    if (brick.isDestroyed()) {
                        audio.playBrickBreak();
                        newDestroyBrick = true;
                    } else {
                        audio.playBrickHit();
                        newDestroyBrick = false;
                    }
                    move(-speed * vectorX, -speed * vectorY);
                    double overlapLeft = getX() + getWidth() - brick.getX();
                    double overlapRight = brick.getX() + brick.getWidth() - getX();
                    double overlapTop = getY() + getHeight() - brick.getY();
                    double overlapBottom = brick.getY() + brick.getHeight() - getY();

                    double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                            Math.min(overlapTop, overlapBottom));
                    if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                        vectorX *= -1;
                    } else {
                        vectorY *= -1;
                    }
                    if(fireBall) {
                        fireBall = false;
                        for (int di = -1; di <= 1; di++) {
                            for (int dj = -1; dj <= 1; dj++) {
                                int ni = i + di;
                                int nj = j + dj;
                                if (ni >= 0 && ni < 8 && nj >= 0 && nj < 12) {
                                    bricks[ni][nj].setHitPoints(0);
                                }
                            }
                        }

                    }
                    break;
                }
            }
    }

    public Image getImg(int index)
    {
        if(fireBall) return ballImg[2];
        return ballImg[index];
    }

    public int getLives() {
        return lives;
    }

    public boolean isOnPaddle() {
        return onPaddle;
    }

    public void setOnPaddle(boolean onPaddle) {
        this.onPaddle = onPaddle;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getVectorX() {
        return vectorX;
    }

    public double getVectorY() {
        return vectorY;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isFireBall() {
        return fireBall;
    }

    public void setFireBall(boolean fireBall) {
        this.fireBall = fireBall;
    }
}