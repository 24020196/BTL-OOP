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
        this.angle = Math.toRadians(80);
        this.vectorX = Math.cos(angle);
        this.vectorY = -Math.sin(angle);
        this.lives = 3;
    }

    public void update(double sceneWidth, double sceneHeight, Paddle paddle, Brick[][] bricks) {
        move(speed * vectorX, speed * vectorY);
        bounceOff(paddle, bricks, sceneWidth, sceneHeight);

        if (getY() > sceneHeight) {
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
        setY(paddle.getY() - getHeight() );
        this.angle = Math.toRadians(80);
        this.vectorX = Math.cos(this.angle);
        this.vectorY = -Math.sin(this.angle);
    }

    public void bounceOff(Paddle paddle, Brick[][] bricks, double sceneWidth, double sceneHeight) {
        // chạm khung trái phải
        if (getX() <= 0 || getX() + getWidth() >= sceneWidth) {
            vectorX *= -0.98;
        }
        if (getY() <= 0) {
            vectorY*=-0.98;
        }

        if (checkCollision(paddle)) {
            vectorY*=-0.98;
        }

        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 11; j++) {
            Brick brick = bricks[i][j];
            if (!brick.isDestroyed() && checkCollision(brick)) {
                brick.hit();
                double overlapLeft = getX() + getWidth() - brick.getX();
                double overlapRight = brick.getX() + brick.getWidth() - getX();
                double overlapTop = getY() + getHeight() - brick.getY();
                double overlapBottom = brick.getY() + brick.getHeight() - getY();

                double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                        Math.min(overlapTop, overlapBottom));

                if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                    vectorX *= -0.98;
                } else {
                    vectorY *= -0.98;
                }
                break;

            }
        }
    }

    public boolean isOnPaddle() {
        return onPaddle;
    }

    public void setOnPaddle(boolean onPaddle) {
        this.onPaddle = onPaddle;
    }

    public static void main(String[] args) {
        double sceneWidth = 400;
        double sceneHeight = 300;

        Paddle paddle = new Paddle(350, 550, 100, 20);
        List<Brick> bricks = new ArrayList<>();
        bricks.add(new Brick(200, 100, 60, 20, 2));
        bricks.add(new Brick(300, 100, 60, 20, 1));

        Ball ball = new Ball(50, 50, 20, 20);

        for (int i = 0; i < 40000; i+=10) {
            if (ball.lives >=0) {
                //ball.update(sceneWidth, sceneHeight, paddle, bricks);
                System.out.printf("Frame %d: Ball(%.1f, %.1f) Dir(%.2f, %.2f) Lives: %d%n",
                        i, ball.getX(), ball.getY(), ball.vectorX, ball.vectorY, ball.lives);


                for (int j = 0; j < bricks.size(); j++) {
                    Brick b = bricks.get(j);
                    System.out.printf("   Brick %d: %s (HP=%d)\n", j, b.isDestroyed() ? "DESTROYED" : "ALIVE", b.getHp());
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}