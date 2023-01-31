package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*['View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_NAME_INPUT_ERROR = "id:org.wikipedia:id/textinput_error",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton",
            ARTICLE_TITLE_IN_LIST_TPL = "xpath://*[@text='{SUBSTRING}']";

    private static String getArticleTitleInList(String substring) {
        return ARTICLE_TITLE_IN_LIST_TPL.replace("{SUBSTRING}", substring);
    }

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresentBy(TITLE,
                "Cannot find article title on page", 15);
    }

    public void clickOnArticle(String article_title) {
        this.waitForElementByAndClick(
               getArticleTitleInList(article_title),
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
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void assertArticlePageTitlePresent() {
        this.assertElementPresent(TITLE, "Something goes wrong with article title");
    }

    public void addArticleToMyListAndCreateFolder(String name_of_folder) {
        this.waitForElementByAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementByAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "cannot find option to add article to reading list",
                5
        );


        this.waitForElementByAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementPresentBy(
                MY_LIST_NAME_INPUT_ERROR,
                "Input was not been cleared and we cannot see validation error 'Please enter a title'",
                5
        );

        this.waitForElementByAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementByAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void assertElementHasTitle(String second_article_title) {
        this.assertElementHasText(
                TITLE,
                second_article_title,
                "Cannot find title" + second_article_title + "as article title");
    }


    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementByAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementByAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "cannot find option to add article to reading list",
                5
        );

        this.waitForElementByAndClick(
                "//android.widget.TextView[@text='" + name_of_folder + "']",
                "Cannot find created folder",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementByAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }
}