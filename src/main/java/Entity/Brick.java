package Entity;

public class Brick extends GameObject {
    private int hitPoints;
    private boolean destroyed = false;

    public Brick(double x, double y, double width, double height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
    }

    public void hit() {
        hitPoints--;
        if (hitPoints <= 0) destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}