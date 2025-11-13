package Entity;

import javafx.scene.image.Image;
import GameManager.AudioController;
import java.util.ArrayList;
import java.util.List;

/**
 * Đại diện cho quả bóng trong trò chơi, xử lý chuyển động, va chạm và trạng thái của nó.
 * Nó tự quản lý vị trí, vận tốc và tương tác với các đối tượng khác trong trò chơi
 * như thanh đỡ, gạch và các bức tường.
 */
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

    /**
     * Khởi tạo một đối tượng Ball mới tại vị trí và kích thước được chỉ định.
     *
     * @param x      Tọa độ x ban đầu.
     * @param y      Tọa độ y ban đầu.
     * @param width  Chiều rộng của quả bóng.
     * @param height Chiều cao của quả bóng.
     */
    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.speed = 10;
        this.angle = Math.toRadians(90);
        this.vectorX = Math.cos(angle);
        this.vectorY = -Math.sin(angle);
        this.lives = 3;
    }

    /**
     * Cập nhật vị trí và trạng thái của quả bóng cho mỗi khung hình.
     * Xử lý chuyển động, va chạm và việc mất mạng.
     *
     * @param sceneWidth  Chiều rộng của màn chơi.
     * @param sceneHeight Chiều cao của màn chơi.
     * @param paddle      Đối tượng thanh đỡ của người chơi.
     * @param bricks      Mảng 2D chứa các viên gạch.
     */
    public void update(double sceneWidth, double sceneHeight, Paddle paddle, Brick[][] bricks) {
        if(lives>0) {
            move(speed * vectorX, speed * vectorY);
            bounceOff(paddle, bricks, sceneWidth, sceneHeight);
            if (getY() > paddle.getY()) {
                audio.playBallFailing();
                lives--;
                if (lives > 0) {
                    reset(paddle);
                }
            }
        }
    }

    /**
     * Đặt lại vị trí và véc-tơ chuyển động của quả bóng, gắn nó vào thanh đỡ.
     *
     * @param paddle Thanh đỡ để đặt lại quả bóng lên trên.
     */
    public void reset(Paddle paddle) {
        setX(paddle.getX() + paddle.getWidth() / 2.0 - getWidth() / 2.0);
        setY(paddle.getY() - getHeight() );
        this.angle = Math.toRadians(90);
        this.vectorX = Math.cos(this.angle);
        this.vectorY = -Math.sin(this.angle);
        this.onPaddle = true;
        this.fireBall = false;
    }

    /**
     * Quản lý tất cả logic phát hiện và phản ứng va chạm cho quả bóng.
     * Xử lý việc nảy khỏi tường, thanh đỡ và các viên gạch.
     *
     * @param paddle      Thanh đỡ của người chơi.
     * @param bricks      Mảng 2D chứa các viên gạch.
     * @param sceneWidth  Chiều rộng của màn chơi.
     * @param sceneHeight Chiều cao của màn chơi.
     */
    public void bounceOff(Paddle paddle, Brick[][] bricks, double sceneWidth, double sceneHeight) {
        // Nảy khỏi tường trái và phải
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
                double distance = (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0); // Giá trị từ -1 đến 1
                double bounceAngle = Math.toRadians(60 * distance); // Góc lệch tối đa ±60°
                move(-speed * vectorX, -speed * vectorY);
                vectorX = Math.sin(bounceAngle);
                vectorY = -Math.cos(bounceAngle);
                setY(paddle.getY() - getHeight() - 1);
            }
            setY(paddle.getY() - getHeight()-1);
        }

        // Nảy khỏi gạch
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
                    // Lùi lại một bước để tránh bị kẹt trong gạch
                    move(-speed * vectorX, -speed * vectorY);

                    // Xác định cạnh va chạm
                    double overlapLeft = getX() + getWidth() - brick.getX();
                    double overlapRight = brick.getX() + brick.getWidth() - getX();
                    double overlapTop = getY() + getHeight() - brick.getY();
                    double overlapBottom = brick.getY() + brick.getHeight() - getY();

                    double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                            Math.min(overlapTop, overlapBottom));

                    // Đảo ngược vận tốc dựa trên cạnh có độ chồng lấn ít nhất
                    if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                        vectorX *= -1;
                    } else {
                        vectorY *= -1;
                    }

                    // Xử lý logic cho fireball (bóng lửa)
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

    /**
     * Lấy hình ảnh của quả bóng dựa trên chỉ số.
     * Trả về hình ảnh bóng lửa nếu đang ở trạng thái fireball.
     *
     * @param index Chỉ số của hình ảnh (0 hoặc 1).
     * @return Hình ảnh tương ứng.
     */
    public Image getImg(int index)
    {
        if(fireBall) return ballImg[2];
        return ballImg[index];
    }

    /**
     * Lấy số mạng còn lại.
     *
     * @return Số mạng.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Kiểm tra xem quả bóng có đang nằm trên thanh đỡ không.
     *
     * @return true nếu quả bóng đang ở trên thanh đỡ, ngược lại là false.
     */
    public boolean isOnPaddle() {
        return onPaddle;
    }

    /**
     * Đặt trạng thái 'trên thanh đỡ' của quả bóng.
     *
     * @param onPaddle true để đặt bóng lên thanh đỡ, false để thả bóng.
     */
    public void setOnPaddle(boolean onPaddle) {
        this.onPaddle = onPaddle;
    }

    /**
     * Đặt tốc độ của quả bóng.
     *
     * @param speed Tốc độ mới.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Lấy thành phần véc-tơ x của hướng di chuyển.
     *
     * @return Thành phần x.
     */
    public double getVectorX() {
        return vectorX;
    }

    /**
     * Lấy thành phần véc-tơ y của hướng di chuyển.
     *
     * @return Thành phần y.
     */
    public double getVectorY() {
        return vectorY;
    }

    /**
     * Lấy tốc độ hiện tại của quả bóng.
     *
     * @return Tốc độ.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Kiểm tra xem quả bóng có phải là bóng lửa không.
     *
     * @return true nếu là bóng lửa, ngược lại là false.
     */
    public boolean isFireBall() {
        return fireBall;
    }

    /**
     * Đặt trạng thái bóng lửa cho quả bóng.
     *
     * @param fireBall true để biến thành bóng lửa, false để trở lại bình thường.
     */
    public void setFireBall(boolean fireBall) {
        this.fireBall = fireBall;
    }
}