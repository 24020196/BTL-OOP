import Entity.Bullet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckCollisionTest {

    @Test
    public void test1() {
        Bullet a = new Bullet(0, 0, 10, 10);
        Bullet b = new Bullet(20, 20, 10, 10);
        assertFalse(a.checkCollision(b));
    }

    @Test
    public void test2() {
        Bullet a = new Bullet(0, 0, 10, 10);
        Bullet b = new Bullet(0, 0, 10, 10);
        assertTrue(a.checkCollision(b));
    }

    @Test
    public void test3() {
        Bullet a = new Bullet(0, 0, 10, 10);
        Bullet b = new Bullet(5, 5, 10, 10);
        assertTrue(a.checkCollision(b));
    }

    @Test
    public void test4() {
        Bullet a = new Bullet(0, 0, 10, 10);
        Bullet b = new Bullet(10, 0, 10, 10);
        assertFalse(a.checkCollision(b));
    }

    @Test
    public void test5() {
        Bullet a = new Bullet(0, 0, 20, 20);
        Bullet b = new Bullet(5, 5, 5, 5);
        assertTrue(a.checkCollision(b));
    }
}
