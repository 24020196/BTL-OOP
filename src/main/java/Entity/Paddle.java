package Entity;

/**
 * Đại diện cho thanh đỡ (paddle) của người chơi.
 * Xử lý logic di chuyển của thanh đỡ.
 */
public class Paddle extends GameObject {
    private final static double speed = 20;
    private boolean left = false;
    private boolean right = false;

    /**
     * Khởi tạo thanh đỡ tại vị trí và kích thước chỉ định.
     *
     * @param x      Tọa độ x.
     * @param y      Tọa độ y.
     * @param width  Chiều rộng.
     * @param height Chiều cao.
     */
    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    /**
     * Cập nhật vị trí của thanh đỡ dựa trên các cờ di chuyển (trái/phải).
     * Giới hạn di chuyển trong phạm vi màn hình.
     */
    public void move() {
        if(left == true && this.x >= 20) {
            super.move(-speed,0);
        } else
        if(right == true && this.x <= 1280 - this.width - 20) {
            super.move(speed,0);
        }
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}