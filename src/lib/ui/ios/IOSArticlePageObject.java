package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath:*//XCUIElementTypeStaticText[@name='Java (programming language)']";
        FOOTER_ELEMENT = "xpath:*//XCUIElementTypeStaticText[@name='View article in browser']";
        //FOOTER_ELEMENT = "xpath://*['View page in browser']";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }
    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
