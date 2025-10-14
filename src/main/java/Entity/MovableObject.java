package Entity;

public class MovableObject extends GameObject {
    private double dx;
    private double dy;

    public void move(double dx, double dy, double ratioX, double ratioY) {
//        super(ratioX, ratioY);
        super.setX(super.getX()+dx);
        super.setY(super.getY()+dy);
    }


}