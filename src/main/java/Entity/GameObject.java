package Entity;

public class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;
    private double ratioX;
    private double ratioY;

    public GameObject(double x, double y, double width, double height, double ratioX, double ratioY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.ratioX = ratioX;
        this.ratioY = ratioY;
    }

    public void update(double x, double y, double width, double height, double ratioX, double ratioY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.ratioX = ratioX;
        this.ratioY = ratioY;
    }

    public GameObject() {
    }

    public double getRatioX() {
        return ratioX;
    }

    public void setRatioX(double ratioX) {
        this.ratioX = ratioX;
    }

    public double getRatioY() {
        return ratioY;
    }

    public void setRatioY(double ratioY) {
        this.ratioY = ratioY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}