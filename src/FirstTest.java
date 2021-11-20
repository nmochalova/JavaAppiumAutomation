import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\org.wikipedia_50377_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                15
        );
    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot fined search field",
                5
        );

       waitForElementAndClick(
               By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find string 'Object-oriented programming language'",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        String article_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testContainText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //локатор нужного элемента.
        By locatorElement = By.id("org.wikipedia:id/search_src_text");
        waitForElementPresent(
                locatorElement,
                "Cannot find attribut text Search...",
                15
        );

        assertElementHasText(
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
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String keys_word = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                keys_word,
                "Cannot find search input",
                5
        );

        List<WebElement> elementList = waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "List of elements are empty",
                5
        );

        for (WebElement webElement : elementList)
        {
            String elementAttribute = webElement.getAttribute("text");
            System.out.println(elementAttribute);

            Assert.assertTrue("Search result does not contain "+keys_word,elementAttribute.contains(keys_word));
        }
//      Пример реализации for через лямбды
//        Assert.assertTrue("Search result does not contain word", elementList
//                .stream().allMatch(v -> v.getAttribute("text")
//                        .contains(keys_word)));
    }

    private WebElement waitForElementPresent(By by, String error_messanger, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_messanger)
    {
       return waitForElementPresent(by,error_messanger,5);
    }

    private WebElement waitForElementAndClick(By by, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_messanger,timeoutInSecond);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_messanger,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_messanger, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_messanger,timeoutInSecond);
        element.clear();
        return element;
    }

    /**
     * Метод который принимает локатор текст и сообщение об ошибке.
     * @param by    Локатор
     * @param text_element  Текст который должен содержаться в элементе локатора
     * @param error_messanger   Сообщение об ошибке если текста нет такого
     * @return  Возвращает true если в таком элементе содержится нужный текст.
     *          false если текст другой.
     */
    private boolean assertElementHasText (By by, String text_element, String error_messanger)
    {
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.attributeContains(by,"text",text_element)
        );
    }

    /**
     * Метод проверяет налиличие нескольких элементов на странице
     * @param by локатор
     * @param error_messanger ошибка в случае, если элементы не найдены
     * @param timeoutInSecond  время ожидания загрузки страницы
     * @return массив найденных элементов на странице по заданному локатору
     */
    private List<WebElement> waitForElementsPresent(By by, String error_messanger, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

}
