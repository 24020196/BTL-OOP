package Entity;

import javafx.scene.image.Image;

/**
 * Đại diện cho một viên gạch trong trò chơi.
 * Quản lý máu (hitPoints), loại gạch, và trạng thái bị phá hủy.
 */
public class Brick extends GameObject {
    private double hitPoints;
    private double type;
    private boolean destroyed = false;
    private int indexImg = 0; // Dùng cho animation của gạch không thể phá hủy

    private static final Image[] brickImg = {
            new Image(Brick.class.getResource("/res/brick0.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brick1.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brick2.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brickUndestroy0.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brickUndestroy1.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brickUndestroy2.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brickUndestroy3.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brickUndestroy4.png").toExternalForm()),
            new Image(Brick.class.getResource("/res/brickUndestroy5.png").toExternalForm())
    };

    /**
     * Khởi tạo một viên gạch mới tại vị trí, kích thước và loại được chỉ định.
     *
     * @param x      Tọa độ x.
     * @param y      Tọa độ y.
     * @param width  Chiều rộng.
     * @param height Chiều cao.
     * @param type   Loại gạch (ảnh hưởng đến máu và hình ảnh).
     */
    public Brick(double x, double y, double width, double height,double type) {
        super(x, y, width, height);
        this.type = type;
        this.hitPoints = type;
    }

    /**
     * Xử lý khi gạch bị bóng va vào. Giảm máu (hitPoints).
     * Nếu máu về 0, gạch bị phá hủy.
     */
    public void hit() {
        if (hitPoints > 0 ) hitPoints--;
        if (hitPoints == 0) {
            destroyed = true;
        }else indexImg = 9; // Đặt lại chỉ số animation (có vẻ logic này dùng cho gạch_bất_tử)
    }

    /**
     * Kiểm tra xem gạch đã bị phá hủy chưa.
     *
     * @return true nếu đã bị phá hủy, ngược lại là false.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Đặt trực tiếp số máu cho gạch.
     *
     * @param hitPoints Số máu mới.
     */
    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
        if (hitPoints == 0)destroyed = true;
    }

    /**
     * Lấy loại của gạch.
     *
     * @return Loại gạch.
     */
    public double getType() {
        return type;
    }

    /**
     * Lấy số máu hiện tại của gạch.
     *
     * @return Số máu hiện tại.
     */
    public double getHp() { return hitPoints; }

    /**
     * Tính toán độ mờ (opacity) của gạch, chủ yếu dùng cho gạch loại 6 (không thể phá hủy).
     *
     * @return Giá trị độ mờ (từ 0.0 đến 1.0).
     */
    public double getOpacity() {
        if(type == 6 && hitPoints > 3) {
            return 1;
        } else if(type == 6 && hitPoints > 0) {
            return 0.5;
        }
        return Math.abs(hitPoints/type);
    }

    /**
     * Lấy hình ảnh hiện tại của gạch dựa trên loại và trạng thái.
     * Xử lý animation cho gạch loại 6.
     *
     * @return Đối tượng Image để vẽ.
     */
    public Image getImage() {

        if(type == 6) {
            if(indexImg == 0) return brickImg[3];
            indexImg++;
            if(indexImg == 9*3) {
                indexImg = 0;
                return brickImg[3];
            }
            return brickImg[indexImg/3];
        }

        if(type > 0)
            return brickImg[(int) type-1];

        return null;
    }
}