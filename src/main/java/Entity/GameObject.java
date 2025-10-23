package Entity;

public abstract class GameObject {
    protected double x, y, width, height;
    protected double dx, dy; // vận tốc theo trục X/Y

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    // kiểm tra va chạm chung
    public boolean checkCollision(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    // có thể override trong lớp con nếu cần
    public void update() {}

    // getter/setter
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
}