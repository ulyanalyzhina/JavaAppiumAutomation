package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        String searchText = "Java";
        String articleTitle = "Java (programming language)";
        //String name_of_folder = "Learning programming";

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
        MyListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticles() throws InterruptedException {
        String search_text_first_article = "Java";
        String search_text_second_article = "Golang";
        String first_article_title = "Java (programming language)";
        String second_article_title = "Go (programming language)";

        String name_of_folder = "Learning programming";
        findArticleAndClickToAddToFolder(search_text_first_article, first_article_title);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        ArticlePageObject.addArticleToMyListAndCreateFolder(name_of_folder);
        ArticlePageObject.closeArticle();

        findArticleAndClickToAddToFolder(search_text_second_article, second_article_title);

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);

        //MyListsPageObject.swipeByArticleToDelete(first_article_title);

        ArticlePageObject.clickOnArticle(second_article_title);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.assertArticlePageTitlePresent();
        ArticlePageObject.assertElementHasTitle(second_article_title);
    }

    private void findArticleAndClickToAddToFolder(String article_text, String article_title) {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(article_text);
        SearchPageObject.clickByArticleWithSubString(article_title);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
    }
}
