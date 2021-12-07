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

   }
