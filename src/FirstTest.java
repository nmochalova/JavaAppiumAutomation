import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {
    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   @Test
    public void testContainText() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //локатор нужного элемента.
        By locatorElement = By.id("org.wikipedia:id/search_src_text");
        MainPageObject.waitForElementPresent(
                locatorElement,
                "Cannot find attribut text Search...",
                15
        );

        MainPageObject.assertElementHasText(
                locatorElement,
                "Search…",
                "Text does not contain 'Search…'"
        );
    }

// Ex4*: Тест: проверка слов в поиске. Тест делает поиск по какому-то ключевому слову. Например, JAVA.
// Затем убеждается, что в каждом результате поиска есть это слово. Ошибка выдаетсяв случае, если хотя бы один элемент
// не содержит ключевого слова.
    @Test
    public void testSearchWordInResultsList()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String keys_word = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                keys_word,
                "Cannot find search input",
                5
        );

        List<WebElement> elementList = MainPageObject.waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "List of elements are empty",
                5
        );

        for (WebElement webElement : elementList)
        {
            String elementAttribute = webElement.getAttribute("text");
            System.out.println(elementAttribute);

            assertTrue("Search result does not contain "+keys_word,elementAttribute.contains(keys_word));
        }
//      Пример реализации for через лямбды
//        assertTrue("Search result does not contain word", elementList
//                .stream().allMatch(v -> v.getAttribute("text")
//                        .contains(keys_word)));
    }

    //Ex3: Тест: отмена поиска. Тест ищет слово, убеждается что найдено несколько вариантов, отменяет поиск и убеждается
    //что результат поиска пропал
    @Test
    public void testSearchResultAndCancel()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String keys_word = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                keys_word,
                "Cannot find search input",
                5
        );

        List<WebElement> elementList = MainPageObject.waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "List of elements are empty",
                5
        );

        assertTrue("Elements not found",elementList.size()>1);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"),
                "Cannot find X to cancel search",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result still present on the page",
                5
        );
    }

    //Ex6: Тест: assert title. Тест, который открывает статью и убеждается, что у нее есть элемент title (тест всегда падает!)
    @Test
    public void testPresentOfTitle() throws InterruptedException {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );


        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find string 'Object-oriented programming language' topic searching by " + searchLine,
                15
        );

        //      Thread.sleep(5000);

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/view_page_header_container']/*[@resource-id='org.wikipedia:id/view_page_title_text']";
        MainPageObject.assertElementPresent(
                By.xpath(searchResultLocator),
                "Cannot find title element."
        );
    }

    //Ex5: Тест: сохранение двух статей. Тест сохраняет две статьи в одну папку и потом удаляет одну из статей. Убеждается, что вторая осталась.
    @Test
    public void testSaveTwoArticlesInOneFolder()
    {
        //Добавляем первую статью
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find string 'Object-oriented programming language'",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannon find button to open article options",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add articale to reading list",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' in overlay",
                10
        );
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                10
        );

        String nameOfFolder = "Learning programming";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press Ok button",
                10);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article,cannot find X link",
                10
        );

        //Добавляем втору статью
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find string 'Appium'",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String titleArcticleExpected = MainPageObject.waitForElementAndGetAttribute (
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannon find button to open article options",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add articale to reading list",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_title"),
                "Cannot find folder " + nameOfFolder,
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article,cannot find X link",
                10
        );

       //Идем в сохраненную группу и удаляем ону статью
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                10
        );
        MainPageObject.waitForElementAndClick(
                //By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + nameOfFolder + "']"),
                By.id("org.wikipedia:id/item_title"),
                "Cannot find created folder",
                10);

        MainPageObject.swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

        //Переходим в оставшуюся статью и убеждаемся, что title совпадает
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find string 'Appium'",
                5
        );

        String titleArcticleActual = MainPageObject.waitForElementAndGetAttribute (
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        assertEquals(
                "Title Arcticle does not equal 'Appium'",
                titleArcticleExpected,
                 titleArcticleActual
        );
    }
}
