package Entity;

import java.util.List;

public class Ball extends GameObject {
    private double speed;
    private double directionX;
    private double directionY;
    private int lives = 3;

    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.speed = 5;
        this.directionX = 1;
        this.directionY = -1;
    }


    // 🧩 Hàm update chính: di chuyển và xử lý va chạm
    public void update(double sceneWidth, double sceneHeight, Paddle paddle, List<Brick> bricks) {
        move(speed * directionX, speed * directionY);
        bounceOff(paddle, bricks, sceneWidth, sceneHeight);

        // 🔥 Kiểm tra nếu rơi khỏi màn hình
        if (getY() > sceneHeight) {
            lives--;
            if (lives <= 0) {
                System.out.println("Game Over!");
            } else {
                reset(paddle);
            }
        }
    }

    // 🔄 Reset bóng lại giữa paddle
    public void reset(Paddle paddle) {
        double newX = paddle.getX() + paddle.getWidth() / 2.0 - getWidth() / 2.0;
        double newY = paddle.getY() - getHeight() - 5; // cách 1 khoảng nhỏ
        setX(newX);
        setY(newY);
        directionX = 1;
        directionY = -1;
    }

    // ⚡ Xử lý va chạm (3 tường, paddle, bricks)
    public void bounceOff(Paddle paddle, List<Brick> bricks,
                          double sceneWidth, double sceneHeight) {

//        va chạm bên trái phải
        if (getX() <= 0) {
            setX(0);
            directionX *= -1;
        } else if (getX() + getWidth() >= sceneWidth) {
            setX(sceneWidth - getWidth());
            directionX *= -1;
        }

        // chạm trên
        if (getY() <= 0) {
            setY(0);
            directionY *= -1;
        }

        // Va chạm paddle
        if (checkCollision(paddle)) {
            double ballCenter = getX() + getWidth() / 2.0;
            double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
            double distance = ballCenter - paddleCenter;
            double percent = distance / (paddle.getWidth() / 2.0);

            // Phản xạ gương có hướng
            directionX = percent;
            directionY = -Math.abs(1 - Math.abs(percent));

            // Chuẩn hóa vector
            double length = Math.sqrt(directionX * directionX + directionY * directionY);
            directionX /= length;
            directionY /= length;

            // Đặt bóng ra khỏi paddle để tránh bị kẹt
            setY(paddle.getY() - getHeight() - 1);
        }

        // 🧩 Va chạm gạch
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && checkCollision(brick)) {
                brick.hit(); // Trừ máu gạch

                // Xác định hướng phản xạ
                double ballCenterX = getX() + getWidth() / 2.0;
                double ballCenterY = getY() + getHeight() / 2.0;
                double brickCenterX = brick.getX() + brick.getWidth() / 2.0;
                double brickCenterY = brick.getY() + brick.getHeight() / 2.0;

                double dx = Math.abs(ballCenterX - brickCenterX);
                double dy = Math.abs(ballCenterY - brickCenterY);

                if (dx > dy) {
                    directionX *= -1;
                } else {
                    directionY *= -1;
                }

                break; // tránh xử lý 2 gạch cùng lúc
            }
        }
    }

    // kiểm tra va chạm
    public boolean checkCollision(GameObject other) {
        return getX() < other.getX() + other.getWidth() &&
                getX() + getWidth() > other.getX() &&
                getY() < other.getY() + other.getHeight() &&
                getY() + getHeight() > other.getY();
    }

    // Getters & setters
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }
}