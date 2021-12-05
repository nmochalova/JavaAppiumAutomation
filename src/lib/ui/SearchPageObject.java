//методы для поиска
package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";

    //Инициализация драйвера
    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }
    /*TEMPLATES METHODS*/

    public void initSearchInput()
    {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element");

        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5);
    }

    public void typeSearchLine(String searchLine)
    {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                searchLine,
                "Cannot find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);

        this.waitForElementPresent
                (By.xpath(searchResultXpath),
                        "Cannot find search result with substring " + substring);
    }

    //Проверка наличия кнопки отмены поиска Х на странице
    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find X to cancel search",
                5);
    }

    //Проверка, что кнопки отмены поиска Х нет на странице
    public void waitForCancelButtonToDisAppear()
    {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "X still present on the page",
                5);
    }

    //Клик по кнопке отмены поиска Х
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click by button X cancel search",
                5
        );
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);

        this.waitForElementAndClick(
                By.xpath(searchResultXpath),
                "Cannot find and click search result with substring " + substring,
                10);
    }
}
