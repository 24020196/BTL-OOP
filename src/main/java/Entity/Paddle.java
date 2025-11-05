package Entity;

public class Paddle extends GameObject {
    private final static double speed = 20;
    private String currentPowerUp;
    private boolean left = false;
    private boolean right = false;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void move() {
        if(left == true && this.x >= 20)super.move(-speed,0); else
        if(right == true && this.x <= 1280 - this.width - 20)super.move(speed,0);
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