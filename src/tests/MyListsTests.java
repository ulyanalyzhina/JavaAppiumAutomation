package tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import static java.lang.Thread.sleep;

public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        String searchText = "Java";
        String articleTitle = "Java (programming language)";

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        findArticleAndClickToAddToFolder(searchText, articleTitle);
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListAndCreateFolder(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved(name_of_folder);
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();


        MyListsPageObject MyListsPageObject = MyListsFactory.get(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.swipeByArticleToDelete(articleTitle);
        } else {
            sleep(2000);
            MyListsPageObject.goToArticle(articleTitle);
            ArticlePageObject.deleteArticleFromReadingList();
            ArticlePageObject.closeArticle();

            MyListsPageObject.verifyThatArticleDisappearFromFolder(name_of_folder, articleTitle);
        }
    }

    @Test
    public void testSaveTwoArticles() throws InterruptedException {
        String search_text_first_article = "Java";
        String search_text_second_article = "Golang";
        String first_article_title = "Java (programming language)";
        String second_article_title = "Go (programming language)";

        findArticleAndClickToAddToFolder(search_text_first_article, first_article_title);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListAndCreateFolder(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved(name_of_folder);
        }

        ArticlePageObject.closeArticle();

        findArticleAndClickToAddToFolder(search_text_second_article, second_article_title);

        ArticlePageObject.addArticleToMyList(name_of_folder, second_article_title);

        MyListsPageObject MyListsPageObject = MyListsFactory.get(driver);
        MyListsPageObject.clickMyFolder(name_of_folder);
        ArticlePageObject.closeArticle();
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();
        MyListsPageObject.openFolderByName(name_of_folder);
        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.swipeByArticleToDelete(first_article_title);
        } else {
            sleep(2000);
            MyListsPageObject.goToArticle(first_article_title);
            ArticlePageObject.deleteArticleFromReadingList();
            ArticlePageObject.closeArticle();
            sleep(3000);
        }

        ArticlePageObject.clickOnArticle(second_article_title);

        ArticlePageObject.waitForTitleElement(second_article_title);
        ArticlePageObject.assertArticlePageTitlePresent(second_article_title);
        ArticlePageObject.assertElementHasTitle(second_article_title);
    }

    private void findArticleAndClickToAddToFolder(String article_text, String article_title) {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(article_text);
        SearchPageObject.clickByArticleWithSubString(article_title);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForTitleElement(article_title);
        } else {
            ArticlePageObject.waitForTitleElement();
        }
    }
}