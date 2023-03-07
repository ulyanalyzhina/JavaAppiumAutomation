package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_NAME_INPUT_ERROR,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            ARTICLE_TITLE_IN_LIST_TPL,
            ADD_TO_MY_LIST_NOTIFICATION,
            ADD_TO_MY_LIST_ICON,
            ADDED_ARTICLE_NOTIFICATION_IN_HEADER,
    ADD_TO_MY_LIST_BUTTON,
            CREATE_LIST_BUTTON,
            HOME_BUTTON
    ;

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
        if(Platform.getInstance().isAndroid()){
            return titleElement.getAttribute("text");
        } else {
            return titleElement.getAttribute("name");
        }

    }

    public void swipeToFooter() {
        if(Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    50
            );
        } else {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article(ios)",
                    50
            );
        }

    }

    public void assertArticlePageTitlePresent() {
        this.assertElementPresent(TITLE, "Something goes wrong with article title");
    }

    public void addArticleToMySaved(String name_of_folder){
        this.waitForElementByAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
        this.waitForElementPresentBy(
                ADD_TO_MY_LIST_NOTIFICATION,
                "Cannot find notification about adding article to reading list",
                15);
        this.tapPointOnTheScreen(15, 720);
        this.waitForElementPresentBy(ADDED_ARTICLE_NOTIFICATION_IN_HEADER,
                "There is no notification about adding article on the creating list page",
                15);

        this.waitForElementByAndClick(
                ADD_TO_MY_LIST_BUTTON ,
                "Cannot find button to create list",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );
        this.waitForElementByAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementByAndClick(
                CREATE_LIST_BUTTON,
                "Cannot find or click 'Create reading list' button",
                5
        );
    }

    public void tapPointOnTheScreen(int x, int y){
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(PointOption.point(x, y)).perform();
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
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementByAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        } else {

            this.waitForElementByAndClick(
                    HOME_BUTTON,
                    "Cannot find 'W' button to close article",
                    5
            );
        }
    }
}