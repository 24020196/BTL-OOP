package Entity;

import java.util.ArrayList;
import java.util.List;

public class Ball extends GameObject {
    private double speed;
    private double angle;
    private double vectorX;
    private double vectorY;
    private int lives;
    private boolean onPaddle = true;

    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.speed = 10;
        this.angle = Math.toRadians(90);
        this.vectorX = Math.cos(angle);
        this.vectorY = -Math.sin(angle);
        this.lives = 3;
    }

    public void update(double sceneWidth, double sceneHeight, Paddle paddle, Brick[][] bricks) {
        move(speed * vectorX, speed * vectorY);
        bounceOff(paddle, bricks, sceneWidth, sceneHeight);


        if (getY() > paddle.getY()) {
            lives--;
            if (lives <= 0) {
                System.out.println("Game Over!");
            } else {
                reset(paddle);
            }
        }
    }

    public void reset(Paddle paddle) {
        setX(paddle.getX() + paddle.getWidth() / 2.0 - getWidth() / 2.0);
        setY(paddle.getY() - getHeight());
        this.angle = Math.toRadians(90);
        this.vectorX = Math.cos(this.angle);
        this.vectorY = -Math.sin(this.angle);
        this.onPaddle = true;
    }

    public void bounceOff(Paddle paddle, Brick[][] bricks, double sceneWidth, double sceneHeight) {
        // chạm khung trái phải
        if (getX() <= 0) {
            setX(0.1);
            vectorX = Math.abs(vectorX);  // đảm bảo đi sang phải
        }
        else if (getX() + getWidth() >= sceneWidth) {
            setX(sceneWidth - getWidth() - 0.1);
            vectorX = -Math.abs(vectorX); // đảm bảo đi sang trái
        }
        if (getY() <= 0) {
            vectorY *= -0.98;
        }

        if (checkCollision(paddle)) {
            if (checkCollision(paddle)) {
                double ballCenter = getX() + getWidth() / 2.0;
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
                double distance = (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0); // -1..1
                double bounceAngle = Math.toRadians(60 * distance); // góc lệch tối đa ±60°
                vectorX = Math.sin(bounceAngle);
                vectorY = -Math.cos(bounceAngle);
                setY(paddle.getY() - getHeight() - 1);
            }
            setY(paddle.getY() - getHeight() - 1);
        }

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 12; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed() && checkCollision(brick)) {
                    brick.hit();

                    move(-speed * vectorX, -speed * vectorY);
                    double overlapLeft = getX() + getWidth() - brick.getX();
                    double overlapRight = brick.getX() + brick.getWidth() - getX();
                    double overlapTop = getY() + getHeight() - brick.getY();
                    double overlapBottom = brick.getY() + brick.getHeight() - getY();

                    double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                            Math.min(overlapTop, overlapBottom));
                    if (minOverlap == overlapLeft) {
                        // đẩy bóng ra bên trái
                        setX(brick.getX() - getWidth() - 0.5);
                        vectorX = -vectorX;
                    }
                    else if (minOverlap == overlapRight) {
                        // đẩy bóng ra bên phải
                        setX(brick.getX() + brick.getWidth() + 0.5);
                        vectorX = -vectorX;
                    }
                    else if (minOverlap == overlapTop) {
                        // đẩy bóng lên trên
                        setY(brick.getY() - getHeight() - 0.5);
                        vectorY = -vectorY;
                    }
                    else {
                        // đẩy bóng xuống dưới
                        setY(brick.getY() + brick.getHeight() + 0.5);
                        vectorY = -vectorY;
                    }


                }
            }
    }

    public boolean isOnPaddle() {
        return onPaddle;
    }

    public void setOnPaddle(boolean onPaddle) {
        this.onPaddle = onPaddle;
    }
}