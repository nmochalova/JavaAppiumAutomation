package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Методы для работы со статьями
 */
public class ArticlePageObject extends MainPageObject
{
    public static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_LIST_TO_MY_LIST_BUTTON = "//android.widget.TextView[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

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

    //метод добавляет статью в список Reading list
    public void addArticleToMyList(String nameOfFolder)
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannon find button to open article options",
                10
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_LIST_TO_MY_LIST_BUTTON),
                "Cannot find option to add articale to reading list",
                10
        );
        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' in overlay",
                10
        );
        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of article folder",
                10
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                nameOfFolder,
                "Cannot put text into articles folder input",
                10
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press Ok button",
                10);
    }

    //метод закрывает статью (нажимет на Х в углу статьи)
    public void closeArticle()
    {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article,cannot find X link",
                10
        );
    }
}
