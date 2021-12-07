//методы для поиска
package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
        SEARCH_ARTICLE_FOR_TITLE_AND_DESC = "//android.widget.LinearLayout[*[@text='{SUBSTRING_TITLE}'] and *[@text='{SUBSTRING_DESC}']]",
        SEARCH_RESULT_TITLE = "//*[@resource-id='org.wikipedia:id/view_page_header_container']/*[@resource-id='org.wikipedia:id/view_page_title_text']";

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

    //метод заменяет в Xpath одновременно заголовок и описание
    private static String getResultSearchElementForTitleAndDesc(String substringTitle, String substringDesc)
    {
        return SEARCH_ARTICLE_FOR_TITLE_AND_DESC
                .replace("{SUBSTRING_TITLE}",substringTitle)
                .replace("{SUBSTRING_DESC}",substringDesc);
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

    //Ввод переданного значения в строку поиска
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

    //клик по результату поиска с учетом заданной подстроки
    public void clickByArticleWithSubstring(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);

        this.waitForElementAndClick(
                By.xpath(searchResultXpath),
                "Cannot find and click search result with substring " + substring,
                10);
    }

    //Подсчет количества результатов поиска
    public int getAmountOfFoundArticle()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15);

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    //метод, который ожидает пустой результат запроса (строку No results found на странице)
    public void waitForEmptyResultsLabel()
    {
         this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result label by the request ",
                15);
    }

    //метод, который проверяет отсуствие результатов поиска
    public void assertThereIsNotResultOfSearch()
    {
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We've found some results by request " );
    }

    //метод, который проверяет наличие заголовка в статье
    public void assertThereIsResultOfSearch()
    {
        this.assertElementPresent(
                By.xpath(SEARCH_RESULT_TITLE),
                "A title not present." );
    }

    //метод дожидатся результата поиска по двум строкам - по заголовку и описанию. Если такого не появляется,
    // то ошибка.
    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String searchResultXpath =  getResultSearchElementForTitleAndDesc(title, description);

        this.waitForElementPresent(By.xpath(searchResultXpath),
                "Cannot find element in search result by title and description \n" + searchResultXpath);
    }
}
