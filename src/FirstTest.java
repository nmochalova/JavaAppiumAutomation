import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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

    //Ex3: Тест: отмена поиска. Тест ищет слово, убеждается что найдено несколько вариантов, отменяет поиск и убеждается
    //что результат поиска пропал
    @Test
    public void testSearchResultAndCancel()
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

        Assert.assertTrue("Elements not found",elementList.size()>1);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result still present on the page",
                5
        );
    }

//Swipe: start_x, Touch action, dimension. Тест, который открывает статью и несколько раз делает свайп по ней
    @Test
    public void testSwipeArticle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find string 'Appium'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    //03,04 overlay, swipe left, variable. Тест сохраняет статью в список, потом находит ее и удаляет из списка.
    @Test
    public void saveFirstArticleToMyList()
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

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannon find button to open article options",
                10
        );
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add articale to reading list",
                10
        );
        waitForElementAndClick(
          By.id("org.wikipedia:id/onboarding_button"),
          "Cannot find 'Got it' in overlay",
                10
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                10
        );

        String nameOfFolder = "Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press Ok button",
                10);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article,cannot find X link",
                10
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                10
        );
        waitForElementAndClick(
                //By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + nameOfFolder + "']"),
                   By.id("org.wikipedia:id/item_title"),
                "Cannot find created folder",
                10);

        swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }

    //Assert: basic Тест проверяет, что по результатам поиска выдано данные (более 1 записи)
    @Test
    public void testAmountOfNotEmptySearch()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Linkin Park Diskography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
               By.xpath(searchResultLocator),
                "Cannot find anything by the request: " + searchLine,
                15
        );

        int amountOfSearchResult = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amountOfSearchResult > 0
        );
    }

    //Assert: assertion error. Тест, который ожидает пустой результат запроса по заданной строке
    @Test
    public void testAmountOfEmptySearch()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "zaqwetqw";
        //String searchLine = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by the request " + searchLine,
                15
        );

        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "We've found some results by request " + searchLine
        );
    }

    //Rotation: basic Тест выбирает статью, переворачивает экран и проверяет, что статья не изменилась.
    @Test
    public void testChangeScreenOrientationOnSearchResult()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

       String searchLine = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find string 'Object-oriented programming language' topic searching by " + searchLine,
                15
        );

        String titleBeforeRotation = waitForElementAndGetAttribute (
             By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE); //из портретной в альбомную

        String titleAfterRotation = waitForElementAndGetAttribute (
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterRotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT); //из альбомной в портретную

        String titleAfterSecondRotation = waitForElementAndGetAttribute (
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    //Тест проверяет, что после возращения приложения из бэкраунда в нем не сбросились результаты поиска.
    @Test
    public void testCheckSearchArticleInBackground()
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
                "Cannot find string 'Object-oriented programming language'",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find string 'Object-oriented programming language' after returning from background",
                5
        );
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

    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);           //важно выбрать метод для Appium
        Dimension size = driver.manage().window().getSize();    //взять размер экрана
        int x = size.width / 2;                                 //условные координаты х: ширина экрана пополам
        int startY = (int) (size.height * 0.8);                 //условная координата у: 80% от высоты экрана
        int endY = (int) (size.height * 0.2);                   //условная координата у: свайпаем до 20% по высоте экрана
        action
                .press(x,startY)
                .waitAction(timeOfSwipe)
                .moveTo(x,endY)
                .release()
                .perform();
    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by,String error_messange, int maxSwipes)
    {
        int alreadySwiped = 0;                              //счетчик свайпов
        while (driver.findElements(by).size() == 0) {       //свайпаем пока эл-т не появится на странице
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by,"Cannot find element by swiping up.\n" + error_messange,0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    protected  void swipeElementToLeft(By by, String error_messange)
    {
        WebElement element = waitForElementPresent(
                by,
                error_messange,
                10);
        int leftX = element.getLocation().getX();           //самая левая точка элемента по оси Х
        int rightX = leftX + element.getSize().getWidth();  //к крайней левой точки Х прибавляем ширину экрана
        int upperY = element.getLocation().getY();          //самая верхняя точка элемента по оси У
        int lowerY = upperY + element.getSize().getHeight();//прибавляем к верхней точке по оси У высоту элемента
        int middleY = (upperY + lowerY) / 2;                //середина элемента по оси У

        TouchAction action = new TouchAction(driver);           //важно выбрать метод для Appium
        action
                .press(rightX,middleY)
                .waitAction(300)
                .moveTo(leftX,middleY)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return  elements.size();
    }

    private void assertElementNotPresent(By by, String error_messange)
    {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        return  element.getAttribute(attribute);
    }
}
