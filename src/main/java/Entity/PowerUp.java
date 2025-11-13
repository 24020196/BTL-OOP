package Entity;
import javafx.scene.image.Image;

/**
 * Đại diện cho một vật phẩm tăng sức mạnh (power-up) rơi ra khi phá gạch.
 */
public class PowerUp extends GameObject{
    private int type;
    public static final int width = 80;
    public static final int heigh = 20;
    private static final Image[] powerUpImg = {
            new Image(PowerUp.class.getResource("/res/powerUp_BigPaddle.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/powerUp_SmallPaddle.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/powerUp_Fast.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/powerUp_Slow.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/powerUp_Shoot.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/powerUp_FireBall.png").toExternalForm()),
            new Image(PowerUp.class.getResource("/res/powerUp_TripleBall.png").toExternalForm())
    };

    /**
     * Khởi tạo một đối tượng PowerUp mới.
     *
     * @param x    Tọa độ x.
     * @param y    Tọa độ y.
     * @param type Loại power-up (0-5).
     */
    public PowerUp(double x, double y,int type) {
        super(x,y,width,heigh);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Image getImage() {
        return powerUpImg[type];
    }
}

