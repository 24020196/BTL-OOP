import Entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelPointTest {

    @Test
    public void testAllZeros() {
        User user = new User();
        user.setScore("000000000");
        assertEquals(0, user.getScore());
    }

    @Test
    public void testAllOnes() {
        User user = new User();
        user.setScore("111111111");
        assertEquals(9, user.getScore());
    }

    @Test
    public void testMixed0123() {
        User user = new User();
        user.setScore("012301230");
        assertEquals(12, user.getScore());
    }

    @Test
    public void testMaxThrees() {
        User user = new User();
        user.setScore("333333333");
        assertEquals(27, user.getScore());
    }

    @Test
    public void testRandomPattern() {
        User user = new User();
        user.setScore("102312033"); // tổng = 1+0+2+3+1+2+0+3+3 = 15
        assertEquals(15, user.getScore());
    }
}
