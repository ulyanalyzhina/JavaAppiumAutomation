package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath:*//XCUIElementTypeStaticText[@name='Java (programming language)']";
        FOOTER_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='View article in browser']";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        ADD_TO_MY_LIST_NOTIFICATION = "xpath://XCUIElementTypeStaticText[@name='Add “Java (programming language)” to a reading list?']";
        ADDED_ARTICLE_NOTIFICATION_IN_HEADER = "xpath://XCUIElementTypeStaticText[@name='Add 1 article to reading list']";
        ADD_TO_MY_LIST_ICON = "xpath://XCUIElementTypeImage[@width='26']";
        ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Create a new list']";
        MY_LIST_NAME_INPUT = "xpath:(//XCUIElementTypeTextField)[1]";
        HOME_BUTTON = "xpath://XCUIElementTypeButton[@name='W']";
        CREATE_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Create reading list']";
    }
    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}