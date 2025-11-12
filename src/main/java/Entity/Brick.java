package Entity;

import javafx.scene.image.Image;

public class Brick extends GameObject {
    private double hitPoints;
    private double type;
    private boolean destroyed = false;
    private int indexImg = 0;
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
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    public Brick(double x, double y, double width, double height,double type) {
        super(x, y, width, height);
        this.type = type;
        this.hitPoints = type;
    }

    public void hit() {
        if (hitPoints > 0 ) hitPoints--;
        if (hitPoints == 0) {
            destroyed = true;
        }else indexImg = 9;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
        if (hitPoints == 0)destroyed = true;
    }

    public double getType() {
        return type;
    }
    public double getHp() { return hitPoints; }



    public double getOpacity() {
        if(type == 6 && hitPoints > 3) {
            return 1;
        } else if(type == 6 && hitPoints > 0) {
            return 0.5;
        }
        return Math.abs(hitPoints/type);
    }
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