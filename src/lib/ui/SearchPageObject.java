package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list",
            SEARCH_RESULT_LIST_ITEM = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_RESULT_LIST_ITEM_TITLE ="xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']",
            SEARCH_RESULT_LIST_ITEM_DESCRIPTION ="xpath://*[@resource-id='org.wikipedia:id/page_list_item_page_list_item_description'']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION =
                    "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                            "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']/" +
                            "../*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{DESCRIPTION}']";
    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getSearchResultByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION
                .replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element"
        );
        this.waitForElementByAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element", 5
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementByAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                5
        );
    }

    public void waitForSearchResult(String substring) {
        String search_result = getResultSearchElement(substring);
        this.waitForElementPresent(search_result, "Cannot find search result" + substring);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresentBy(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button!",
                5
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button is still present!",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementByAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }

    public void clickByArticleWithSubString(String substring) {
        String search_result = getResultSearchElement(substring);
        this.waitForElementByAndClick(
                search_result,
                "Cannot find and click search result",
                10
        );
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresentBy(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request " + SEARCH_RESULT_ELEMENT,
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresentBy(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }

    public void assertSearchInputHasText() {
        this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                "Search Wikipedia",
                "There is now field 'Search Wikipedia' ");
    }

    public List<WebElement> assertSearchedResultsAreMoreThanOne() {
        this.waitForElementPresentBy(
                SEARCH_RESULT_LIST,
                "Cannot find search results list",
                15
        );
        //2.2 Убеждаемся, что статей в контейнере больше чем 1
        return this.waitForElementsListPresentAndToBeMoreThan(
               SEARCH_RESULT_LIST_ITEM,
                "The number of elements is not the number we were waiting for",
                10,
                1
        );
    }

    public void assertSearchResultNotPresent() {
        this.waitForElementNotPresent(
                SEARCH_RESULT_LIST,
                "Cannot find search results list",
                5
        );
    }

    public void assertAllTitlesHaveText(List<WebElement> elements, String match) {
        this.assertAllElementsHaveText(elements, match, SEARCH_RESULT_LIST_ITEM_TITLE);
    }

    public void assertSearchedItemHasTitleAndDescription(
            List<WebElement> els,
            int index,
            String title,
            String description
    ){
       this.assertItemHasTitleAndDescription(els, index, title, description,
               SEARCH_RESULT_LIST_ITEM_TITLE,
               SEARCH_RESULT_LIST_ITEM_DESCRIPTION);
    }



    public void waitForElementByTitleAndDescription(String title, String description){
        this.waitForElementPresentBy(
                getSearchResultByTitleAndDescription(title,  description),
                "Cannot find article title",
                5);
    }

    public List<WebElement> getAllSearchItems(){
        return this.waitForElementsListPresentAndToBeMoreThan(
                SEARCH_RESULT_LIST_ITEM,
                "There is no items in the search",
                5,
                2);
    }
}
