import org.junit.Assert;
import org.junit.Test;

public class MainTest {
    @Test
    public void testGetLocalNumber() {
        Assert.assertTrue("Метод returnFourteen не возвращает 14", new MainClass().getLocalNumber() == 14);
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Метод getClassNumber возвращает число < 45 или == 45",
                new MainClass().getClassNumber() > 45);
    }
}