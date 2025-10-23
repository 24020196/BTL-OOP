package Entity;

public class Brick extends GameObject {
    private double hitPoints;
    private double type;
    private boolean destroyed = false;

    public Brick(double x, double y, double width, double height,double type) {
        super(x, y, width, height);
        this.type = type;
        this.hitPoints = type;
    }

    public void hit() {
        if (hitPoints > 0 ) hitPoints--;
        if (hitPoints == 0) destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public double getType() {
        return type;
    }
    public double getHp() { return hitPoints; }

    public double getOpacity() {
        return  hitPoints/type;
    }
}