package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TPL = "xpath://XCUIElementTypeStaticText[@name='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']";
        READING_LIST_TAB = "xpath://XCUIElementTypeStaticText[@name='Reading lists']";
    }
    public IOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
