package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        //SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
        //SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        //XCUIElementTypeStaticText[@name="Object-oriented programming language"]
        //XCUIElementTypeLink[contains(@name,'Object-oriented programming language')]
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[@name='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list";
        SEARCH_RESULT_LIST_ITEM = "xpath://XCUIElementTypeCell";
        SEARCH_RESULT_LIST_ITEM_TITLE = "xpath://../XCUIElementTypeStaticText[1]";
        SEARCH_RESULT_LIST_ITEM_DESCRIPTION = "xpath://../XCUIElementTypeStaticText[2]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION =
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                        "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']/" +
                        "../*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{DESCRIPTION}']";
    }
    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
