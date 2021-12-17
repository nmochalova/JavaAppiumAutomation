package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Тесты на работу с сохраненным списком статей (My lists)
 */
public class MyListsTests extends CoreTestCase
{
    private static final String nameOfFolder = "prog";

    //Тест сохраняет статью в список, потом находит ее и удаляет из списка (свайпом влево).
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String articleTitle = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(nameOfFolder);
        }

        MyListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    //Ex5: Тест: сохранение двух статей. Тест сохраняет две статьи в одну папку и потом удаляет одну из статей. Убеждается, что вторая осталась.
    @Test
    public void testSaveTwoArticlesInOneFolder()
    {
        //Добавляем первую статью
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String articleTitle = ArticlePageObject.getArticleTitle();
        String nameOfFolder = "prog";
        ArticlePageObject.addArticleToMyList(nameOfFolder);
        ArticlePageObject.closeArticle();

        //Добавляем вторую статью
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");
        ArticlePageObject.waitForTitleElement();
        String titleArcticleExpected = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToExistingMyList(nameOfFolder);             //!!!!!!!!!!!!!!
        ArticlePageObject.closeArticle();

        //Идем в сохраненную группу и удаляем ону статью
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListPageObjectFactory.get(driver);
        MyListsPageObject.openFolderByName(nameOfFolder);                       //!!!!!!!!!!!!!
        MyListsPageObject.swipeByArticleToDelete(articleTitle);

        //Переходим в оставшуюся статью и убеждаемся, что title совпадает
        SearchPageObject.clickByArticleWithSubstring("Appium");
        ArticlePageObject.waitForTitleElement();
        String titleArcticleActual = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Title Arcticle does not equal 'Appium'",
                titleArcticleExpected,
                titleArcticleActual);
    }
}
