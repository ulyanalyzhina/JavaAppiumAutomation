package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject extends CoreTestCase {


    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }


    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public List<WebElement> waitForElementsListPresentAndToBeMoreThan(By by, String error_message, long timeout_in_seconds, int numberOfElements) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, numberOfElements)
        );
    }

    public WebElement waitForElementPresentBy(By by, String error_message, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(By by, String error_message) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementByAndClick(By by, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(by, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    public WebElement waitForElementByAndSendKeys(By by, String value, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(by, error_message, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public void assertElementHasText(By by, String expectedValue, String error_message) {
        WebElement elementWithText = waitForElementPresentBy(by, error_message, 5);
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
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresentBy(by, "Cannot find Element by swiping. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentBy(
                by,
                error_message,
                timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresentBy(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(1000)
                .moveTo(left_x, middle_y)
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

    public void assertElementPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements == 0) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        } else if (amount_of_elements > 1) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be only one but found more then one";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentBy(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
