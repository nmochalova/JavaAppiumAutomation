package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.WelcomePageObject;
import org.junit.Test;

/**
 * Тесты на статьи
 */
public class ArticleTests extends CoreTestCase
{
    //Тест, который находит в поиске статью, открывает ее и сверяет заголовок с требуемым
    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String articleTitle = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    //Тест, который открывает статью и несколько раз делает swipe по ней пока не достигнет конца статьи
    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    //Ex6: Тест, который открывает статью и убеждается, что у нее есть элемент title (тест всегда падает!)
    @Test
    public void testPresentOfTitle() throws InterruptedException {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        // Thread.sleep(5000); //чтобы тест не падал не хватает времени для загрузки страницы. Тест демонстрирует важность задержек.
        SearchPageObject.assertThereIsResultOfSearch();
    }

    //Тест, который прощелкивает все приветственные экраны
    // ВНИМАНИЕ!! Только для новой версии apks/org.wikipedia_50377_apps.evozi.com.apk (переключить в CoreTestCase)
    //для теста есть дублер для iOS в классе iOS/GetStartedTest.testPassThroughWelcome
//    @Test
//    public void testPassThroughWelcome()
//    {
//        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
//
//        WelcomePageObject.waitForScreenAddLang();
//        WelcomePageObject.clickNextButton();
//        WelcomePageObject.waitForNewWaysToExp();
//        WelcomePageObject.clickNextButton();
//        WelcomePageObject.waitForReadingList();
//        WelcomePageObject.clickNextButton();
//        WelcomePageObject.waitForSendAnonymousData();
//        WelcomePageObject.clickGetStartedButton();
//    }
}
