package Entity;

/**
 * Lớp cơ sở (base class) trừu tượng cho tất cả các đối tượng trong trò chơi
 * như Bóng, Gạch, Thanh đỡ, Bullet và PowerUp.
 * Cung cấp các thuộc tính cơ bản về vị trí, kích thước và kiểm tra va chạm.
 */
public abstract class GameObject {
    protected double x, y, width, height;

    /**
     * Khởi tạo một đối tượng trò chơi mới.
     *
     * @param x      Tọa độ x ban đầu.
     * @param y      Tọa độ y ban đầu.
     * @param width  Chiều rộng.
     * @param height Chiều cao.
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Di chuyển đối tượng bằng cách thay đổi tọa độ x, y.
     *
     * @param dx Lượng thay đổi trên trục x.
     * @param dy Lượng thay đổi trên trục y.
     */
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Kiểm tra va chạm hình với một đối tượng khác.
     *
     * @param other Đối tượng GameObject khác cần kiểm tra va chạm.
     * @return true nếu có va chạm, ngược lại là false.
     */
    public boolean checkCollision(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
}