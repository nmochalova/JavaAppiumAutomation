package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

/**
 * Методы для работы со статьями
 */
public class ArticlePageObject extends MainPageObject
{
    public static final String
        TITLE = "id:org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
        OPTIONS_LIST_TO_MY_LIST_BUTTON = "xpath://android.widget.TextView[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";

    //Инициализация драйвера
    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //мето проверяет наличие статьи на странице
    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                TITLE,
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
                FOOTER_ELEMENT,
                "Cannot find the end of the article",
                20
        );
    }

    //метод добавляет статью в новый список Reading list
    public void addArticleToMyList(String nameOfFolder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannon find button to open article options",
                10
        );
        this.waitForElementAndClick(
                OPTIONS_LIST_TO_MY_LIST_BUTTON,
                "Cannot find option to add articale to reading list",
                10
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' in overlay",
                10
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                10
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                nameOfFolder,
                "Cannot put text into articles folder input",
                10
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press Ok button",
                10);
    }

    //метод добавляет статью в ранее созданный список Reading list
    public void addArticleToExistingMyList(String nameOfFolder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannon find button to open article options",
                15
        );
        this.waitForElementAndClick(
                OPTIONS_LIST_TO_MY_LIST_BUTTON,
                "Cannot find option to add articale to reading list",
                15
        );

        this.openFolderByName(nameOfFolder);
    }

    //метод закрывает статью (нажимет на Х в углу статьи)
    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article,cannot find X link",
                10
        );
    }
}
