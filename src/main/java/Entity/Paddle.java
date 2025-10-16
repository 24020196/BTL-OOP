package Entity;

public class Paddle extends GameObject {
    private final static double speed = 0;
    private String currentPowerUp;

    public void moveLeft() {
        super.move(-speed,0);
    }

    public void moveRight() {
        super.move(speed,0);
    }


}