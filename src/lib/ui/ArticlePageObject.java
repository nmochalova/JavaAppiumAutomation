//Методы для работы со статьями
package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject
{
    public static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']";

    //Инициализация драйвера
    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //мето проверяет наличие статьи на странице
    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                By.id(TITLE),
                "Cannot find article title on page",
                15);
    }

    //метод возвращает название статьи
    public String getArticleTitle()
    {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of the article",
                20
        );
    }
}
