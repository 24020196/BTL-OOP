package Entity;

/**
 * Đại diện cho một viên đạn trong trò chơi.
 * Quản lý vị trí, kích thước.
 */
public class Bullet extends GameObject {
    /**
     * Khởi tạo một đối tượng mới.
     *
     * @param x      Tọa độ x ban đầu.
     * @param y      Tọa độ y ban đầu.
     * @param width  Chiều rộng.
     * @param height Chiều cao.
     */
    public Bullet(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
