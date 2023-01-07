import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability(
                "app",
                "/Users/ulyana/IdeaProjects/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk"
        );
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                15
        );

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find element",
                15

        );

        waitForElementPresentBy(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find element",
                15
        );
    }

    @Test
    public void assertTitle() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find element",
                5

        );

        waitForElementByAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find element",
                15
        );

        assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"),"Something goes wrong with article title");
    }

    @Test
    public void cancelSearch() {
        waitForElementByAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                10
        );

        waitForElementByAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                10
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                10
        );
    }

    @Test
    public void compareArticleTitle() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find element",
                5

        );

        waitForElementByAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find element",
                5
        );

        WebElement title_element = waitForElementPresentBy(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                20
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void fieldForSearchContainsText() {
        assertElementHasText(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Search Wikipedia",
                "There is now field 'Search Wikipedia' ");
    }

    @Test
    public void cancelSearchOfArticles() {
        //1. Ищем слово "winter"
        waitForElementByAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );
        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "winter",
                "Cannot find element",
                15
        );
        //2. Убеждаемся, что найдено несколько статей
        //2.1 Убеждаемся, что отобразился контейнер
        waitForElementPresentBy(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results list",
                15
        );
        //2.2 Убеждаемся, что статей в контейнере больше чем 1
        waitForElementsListPresentAndToBeMoreThan(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "The number of elements is not the number we were waiting for",
                10,
                1
        );
        //3. Отменяем поиск
        waitForElementByAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        //4. Убеждаемся, что результат поиска пропал
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results list",
                5
        );
    }

    @Test
    public void verifyTheWorldInTheResultSearch() {
        //1. Ищем слово "Java"
        waitForElementByAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );
        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find element",
                15
        );
        //2. Убеждаемся, что найдено несколько статей
        //2.1 Убеждаемся, что отобразился контейнер
        waitForElementPresentBy(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results list",
                15
        );
        //2.2 Убеждаемся, что статей в контейнере больше чем 1
        List<WebElement> els = waitForElementsListPresentAndToBeMoreThan(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "The number of elements is not the number we were waiting for",
                10,
                1
        );
        //3. Проверяем, что каждая статья содержит вхождение 'java' или 'Java'
        String match = "java";
        els.forEach(el -> {
            try {
                el.findElement((By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                        "//*[contains(@text, match)]")));
            } catch (Exception e) {
                Assert.fail("Some of the element doesn't have" + match + "in the article item");
            }
        });
    }

    @Test
    public void swipeArticle() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find element",
                5

        );

        waitForElementByAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find element",
                5
        );

        waitForElementPresentBy(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                5
        );

        swipeUpToFindElement(
                By.xpath("//*['View page in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test
    public void saveTwoArticleToMyList() {
        String search_text_first_article = "Java";
        String search_text_second_article = "Golang";
        String first_article_title = "Java (programming language)";
        String second_article_title = "Go (programming language)";

        findArticleAndClickToAddToFolder(search_text_first_article, first_article_title);

        waitForElementByAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        waitForElementPresentBy(
                By.id("org.wikipedia:id/textinput_error"),
                "Input was not been cleared and we cannot see validation error 'Please enter a title'",
                15
        );


        String name_of_folder = "Learning programming";

        waitForElementByAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into into articles folder input",
                5
        );

        waitForElementByAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        waitForElementByAndClick(
                By.xpath("//android.widget.ImageButton"),
                "Cannot close article, cannot find X link",
                5
        );

        findArticleAndClickToAddToFolder(search_text_second_article, second_article_title);

        waitForElementByAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        waitForElementByAndClick(
                By.xpath("//android.widget.ImageButton"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementByAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my lists",
                5
        );

        waitForElementByAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find navigation button to my lists",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + first_article_title + "']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='" + first_article_title + "']"),
                "Cannot delete saved article",
                5
        );

        waitForElementByAndClick(
                By.xpath("//*[@text='" + second_article_title + "']"),
                "Cannot find element",
                5
        );

        waitForElementPresentBy(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        assertElementHasText(
                By.id("org.wikipedia:id/view_page_title_text"),
                second_article_title,
                "Cannot find title" + second_article_title + "as article title");
    }

    @Test
    public void saveFirstArticleToMyList() {

        findArticleAndClickToAddToFolder("Java", "Java (programming language)");
        waitForElementByAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        waitForElementPresentBy(
                By.id("org.wikipedia:id/textinput_error"),
                "Input was not been cleared and we cannot see validation error 'Please enter a title'",
                5
        );


        String name_of_folder = "Learning programming";

        waitForElementByAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into into articles folder input",
                5
        );

        waitForElementByAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        waitForElementByAndClick(
                By.xpath("//android.widget.ImageButton"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementByAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my lists",
                5
        );

        waitForElementByAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Linkin Park Discography";

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find element",
                5
        );

        String search_amount_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresentBy(
                By.xpath(search_amount_locator),
                "Cannot find anything by the request " + search_amount_locator,
                15
        );

        int amount_of_search_results = getAmountOfElements(By.xpath(search_amount_locator));

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void AmountOfEmptySearch() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Java";

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find element",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresentBy(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Java";

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find element",
                5
        );

        waitForElementByAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find element",
                5
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void SearchArticleInBackGround() {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Java";

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find element",
                5
        );

        waitForElementPresentBy(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find element",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresentBy(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                5
        );
    }

    private void findArticleAndClickToAddToFolder(String searchText, String articleTitle) {
        waitForElementByAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementByAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "Cannot find element",
                5

        );

        waitForElementByAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + articleTitle + "']"),
                "Cannot find element",
                5
        );

        waitForElementPresentBy(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementByAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForElementByAndClick(
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                //By.xpath("//android.widget.TextView[@text='Add to reading list']"),//селектор из урока у меня в AppiumViewer работает, а в тесте нет
                "cannot find option to add article to reading list",
                5
        );
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private List<WebElement> waitForElementsListPresentAndToBeMoreThan(By by, String error_message, long timeout_in_seconds, int numberOfElements) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, numberOfElements)
        );
    }

    private WebElement waitForElementPresentBy(By by, String error_message, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementByAndClick(By by, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(by, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    private WebElement waitForElementByAndSendKeys(By by, String value, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(by, error_message, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private void assertElementHasText(By by, String expectedValue, String error_message) {
        WebElement elementWithText = waitForElementPresentBy(by, error_message, 5);
        String text = elementWithText.getAttribute("text");

        Assert.assertEquals(
                error_message,
                expectedValue,
                text
        );
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
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

    protected void swipeElementToLeft(By by, String error_message) {
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
                .waitAction(400)
                .moveTo(left_x, middle_y)
                .release().
                perform();
    }

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private void assertElementPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements == 0) {
            String default_message = "An element ' " + by.toString() + " 'supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        } else if (amount_of_elements > 1)  {
            String default_message = "An element ' " + by.toString() + " 'supposed to be only one but found more then one";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentBy(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}