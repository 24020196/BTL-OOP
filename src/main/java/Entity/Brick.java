package Entity;

public class Brick extends GameObject {
    private int hitPoints;
    private String type;

    public Brick(double x, double y, double width, double height, double ratioX, double ratioY) {
        super(x, y, width, height, ratioX, ratioY);
    }

}