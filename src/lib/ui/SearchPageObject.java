package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_RESULT_LIST = "org.wikipedia:id/search_results_list",
            SEARCH_RESULT_LIST_ITEM = "//*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION =
                    "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                            "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']/" +
                            "../*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{DESCRIPTION}']",
            SEARCH_RESULT_CONTAINS_IN_TITLE_AND_DESCRIPTION =
            "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
            "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@contains='{SUBSTRING}']/" +
            "../*[@resource-id='org.wikipedia:id/page_list_item_description'][@contains='{SUBSTRING}']";
    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getSearchResultByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION
                .replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }

    private static String getSearchResultInTitleAndDescription(String substring){
        return SEARCH_RESULT_CONTAINS_IN_TITLE_AND_DESCRIPTION
                .replace("{SUBSTRING}", substring);
    }


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementPresent(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element"
        );
        this.waitForElementByAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element", 5
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementByAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type into search input",
                5
        );
    }

    public void waitForSearchResult(String substring) {
        String search_result = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result), "Cannot find search result" + substring);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresentBy(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button!",
                5
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button is still present!",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementByAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button",
                5
        );
    }

    public void clickByArticleWithSubString(String substring) {
        String search_result = getResultSearchElement(substring);
        this.waitForElementByAndClick(
                By.xpath(search_result),
                "Cannot find and click search result",
                10
        );
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresentBy(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request " + SEARCH_RESULT_ELEMENT,
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresentBy(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }

    public void assertSearchInputHasText() {
        this.assertElementHasText(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Search Wikipedia",
                "There is now field 'Search Wikipedia' ");
    }

    public List<WebElement> assertSearchedResultsAreMoreThanOne() {
        this.waitForElementPresentBy(
                By.id(SEARCH_RESULT_LIST),
                "Cannot find search results list",
                15
        );
        //2.2 Убеждаемся, что статей в контейнере больше чем 1
        return this.waitForElementsListPresentAndToBeMoreThan(
                By.xpath(SEARCH_RESULT_LIST_ITEM),
                "The number of elements is not the number we were waiting for",
                10,
                1
        );
    }

    public void assertSearchResultNotPresent() {
        this.waitForElementNotPresent(
                By.id(SEARCH_RESULT_LIST),
                "Cannot find search results list",
                5
        );
    }

    public void assertAllTitlesHaveText(List<WebElement> elements, String match) {
        elements.forEach(el -> {
            String text = el.findElement(
                    (
                            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']")
                    )
            ).getText();
            assertTrue(text.toLowerCase().contains(match.toLowerCase()));
        });

    }

    public void assertSearchedItemHasTitleAndDescription(
            List<WebElement> els,
            int index,
            String title,
            String description
    ){
        assertEquals((title),els.get(index)
                .findElement(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"))
                .getText());
        assertEquals((description), els.get(index)
                .findElement(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']"))
                .getText());
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        this.waitForElementPresentBy(
                By.xpath(getSearchResultByTitleAndDescription(title,  description)),
                "Cannot find article title",
                5);
    }

    public List<WebElement> getAllSearchItems(){
        return this.waitForElementsListPresentAndToBeMoreThan(
                By.xpath(SEARCH_RESULT_LIST_ITEM),
                "There is no items in the search",
                5,
                2);
    }
}
