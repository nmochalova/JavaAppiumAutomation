package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject{

    public static final String ARTICLE_BY_TITLE_TPL = "//android.widget.TextView[@text='{TITLE}']";

    //метод возвращает Xpath для статьи (заменяет параметр {TITLE} на переданное имя статьи)
    private  String getSavedArticleXpathByTitle(String articleTitle)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",articleTitle);
    }

    //Инициализация драйвера
    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //метод проверяет что указанная статья присутствует
    public void waitForArticleToAppearByTitle(String articleTitle)
    {
        String articleXpath = getFolderXpathByName(articleTitle);

        this.waitForElementPresent(
                By.xpath(articleXpath),
                "Cannot find saved article by title" + articleTitle,
                15);
    }

    //метод проверяет, что указанная статья отсуствует
    public void waitForArticleToDisappearByTitle(String articleTitle)
    {
        String articleXpath = getFolderXpathByName(articleTitle);

        this.waitForElementNotPresent(
                By.xpath(articleXpath),
                        "Saved article still present with title" + articleTitle,
                        15);
    }

    //метод удаляет статью из списка путем свайпа влево. Название статьи является параметром.
    public void swipeByArticleToDelete(String articleTitle)
    {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getFolderXpathByName(articleTitle);
        this.swipeElementToLeft(
                By.xpath(articleXpath),
                "Cannot find saved article");
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

}
