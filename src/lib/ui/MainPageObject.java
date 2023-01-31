package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.CoreTestCase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject extends CoreTestCase {


    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }


    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public List<WebElement> waitForElementsListPresentAndToBeMoreThan(String locator, String error_message, long timeout_in_seconds, int numberOfElements) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, numberOfElements)
        );
    }

    public WebElement waitForElementPresentBy(String locator, String error_message, long timeout_in_seconds) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementByAndClick(String locator, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(locator, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    public WebElement waitForElementByAndSendKeys(String locator, String value, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(locator, error_message, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        By by = this.getLocatorWithByString(locator);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public void assertElementHasText(String locator, String expectedValue, String error_message) {
        WebElement elementWithText = waitForElementPresentBy(locator, error_message, 5);
        String text = elementWithText.getAttribute("text");

        Assert.assertEquals(
                error_message,
                expectedValue,
                text
        );
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action.press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y)).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorWithByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresentBy(locator, "Cannot find Element by swiping. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentBy(
                locator,
                error_message,
                timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeElementToLeft(String locator, String error_message) {
        WebElement element = waitForElementPresentBy(
                locator,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release().
                perform();
    }

    public void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public void assertElementPresent(String locator, String error_message) {
        By by = this.getLocatorWithByString(locator);
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements == 0) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        } else if (amount_of_elements > 1) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be only one but found more then one";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentBy(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorWithByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")){
            return By.xpath(locator);
        } else if (by_type.equals("id")){
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }

    public void assertAllElementsHaveText(List<WebElement> elements, String match, String locator) {
        By by = this.getLocatorWithByString(locator);
        elements.forEach(el -> {
            String text = el.findElement(
                    (by)
            ).getText();
            assertTrue(text.toLowerCase().contains(match.toLowerCase()));
        });
    }

    public void assertItemHasTitleAndDescription(
            List<WebElement> els,
            int index,
            String title,
            String description,
            String locatorTitle,
            String locatorDescription
    ){
        By byTitle = this.getLocatorWithByString(locatorTitle);
        assertEquals((title),els.get(index)
                .findElement( byTitle)
                .getText());
        By byDescription = this.getLocatorWithByString(locatorTitle);
        assertEquals((description), els.get(index)
                .findElement(byDescription)
                .getText());
    }
}
