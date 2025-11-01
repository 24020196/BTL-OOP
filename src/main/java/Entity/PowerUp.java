package Entity;

import javafx.scene.image.Image;

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