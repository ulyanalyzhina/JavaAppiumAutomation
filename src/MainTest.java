import org.junit.Assert;
import org.junit.Test;

public class MainTest {
    @Test
    public void testGetLocalNumber() {
        Assert.assertTrue("Метод returnFourteen не возвращает 14", new MainClass().getLocalNumber() == 14);
    }
}