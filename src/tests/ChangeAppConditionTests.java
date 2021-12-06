package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

/**
 * Тесты на состояние приложения после изменения его состояния (ориентация экрана, Background)
 */
public class ChangeAppConditionTests extends CoreTestCase
{
    //Тест выбирает статью, переворачивает экран и проверяет, что статья не изменилась.
    @Test
    public void testChangeScreenOrientationOnSearchResult()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String titleBeforeRotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after second rotation",
                titleBeforeRotation,
                titleAfterSecondRotation);
    }

    //Тест проверяет, что после возращения приложения из Background в нем не сбросились результаты поиска.
    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
