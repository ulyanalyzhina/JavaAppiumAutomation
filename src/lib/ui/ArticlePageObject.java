package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*['View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_NAME_INPUT_ERROR = "org.wikipedia:id/textinput_error",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton",
            ARTICLE_TITLE_IN_LIST_TPL = "//*[@text='{SUBSTRING}']";

    private static String getArticleTitleInList(String substring) {
        return ARTICLE_TITLE_IN_LIST_TPL.replace("{SUBSTRING}", substring);
    }

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresentBy(By.id(TITLE),
                "Cannot find article title on page", 15);
    }

    public void clickOnArticle(String article_title) {
        this.waitForElementByAndClick(
                By.xpath(getArticleTitleInList(article_title)),
                "Cannot find element",
                5
        );
    }


    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void assertArticlePageTitlePresent() {
        this.assertElementPresent(By.id(TITLE), "Something goes wrong with article title");
    }

    public void addArticleToMyListAndCreateFolder(String name_of_folder) {
        this.waitForElementByAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementByAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                //By.xpath("//android.widget.TextView[@text='Add to reading list']"),//селектор из урока у меня в AppiumViewer работает, а в тесте нет
                "cannot find option to add article to reading list",
                5
        );


        this.waitForElementByAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementPresentBy(
                By.id(MY_LIST_NAME_INPUT_ERROR),
                "Input was not been cleared and we cannot see validation error 'Please enter a title'",
                5
        );

        this.waitForElementByAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementByAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );
    }

    public void assertElementHasTitle(String second_article_title) {
        this.assertElementHasText(
                By.id(TITLE),
                second_article_title,
                "Cannot find title" + second_article_title + "as article title");
    }


    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementByAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementByAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                //By.xpath("//android.widget.TextView[@text='Add to reading list']"),//селектор из урока у меня в AppiumViewer работает, а в тесте нет
                "cannot find option to add article to reading list",
                5
        );

        this.waitForElementByAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementByAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link",
                5
        );
    }
}