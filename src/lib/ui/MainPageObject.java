//содержит методы, к которым будут обращаться тесты
package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    public static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_messanger, long timeoutInSecond)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_messanger)
    {
        return waitForElementPresent(locator,error_messanger,5);
    }

    public WebElement waitForElementAndClick(String locator, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator,error_messanger,timeoutInSecond);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator,error_messanger,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_messanger, long timeoutInSecond)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator,error_messanger,timeoutInSecond);
        element.clear();
        return element;
    }

    /**
     * Метод который принимает локатор текст и сообщение об ошибке.
     * @param locator    Локатор
     * @param text_element  Текст который должен содержаться в элементе локатора
     * @param error_messanger   Сообщение об ошибке если текста нет такого
     * @return  Возвращает true если в таком элементе содержится нужный текст.
     *          false если текст другой.
     */
    public boolean assertElementHasText (String locator, String text_element, String error_messanger)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.attributeContains(by,"text",text_element)
        );
    }

    /**
     * Метод проверяет налиличие нескольких элементов на странице
     * @param locator локатор
     * @param error_messanger ошибка в случае, если элементы не найдены
     * @param timeoutInSecond  время ожидания загрузки страницы
     * @return массив найденных элементов на странице по заданному локатору
     */
    public List<WebElement> waitForElementsPresent(String locator, String error_messanger, long timeoutInSecond)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    public void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);           //важно выбрать метод для Appium
        Dimension size = driver.manage().window().getSize();    //взять размер экрана
        int x = size.width / 2;                                 //условные координаты х: ширина экрана пополам
        int startY = (int) (size.height * 0.8);                 //условная координата у: 80% от высоты экрана
        int endY = (int) (size.height * 0.2);                   //условная координата у: свайпаем до 20% по высоте экрана
        action
                .press(PointOption.point(x,startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, endY))
                .release()
                .perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator,String error_messange, int maxSwipes)
    {
        By by = this.getLocatorByString(locator);
        int alreadySwiped = 0;                              //счетчик свайпов
        while (driver.findElements(by).size() == 0) {       //свайпаем пока эл-т не появится на странице
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator,"Cannot find element by swiping up.\n" + error_messange,0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public  void swipeElementToLeft(String locator, String error_messange)
    {
        WebElement element = waitForElementPresent(
                locator,
                error_messange,
                10);
        int leftX = element.getLocation().getX();           //самая левая точка элемента по оси Х
        int rightX = leftX + element.getSize().getWidth();  //к крайней левой точки Х прибавляем ширину экрана
        int upperY = element.getLocation().getY();          //самая верхняя точка элемента по оси У
        int lowerY = upperY + element.getSize().getHeight();//прибавляем к верхней точке по оси У высоту элемента
        int middleY = (upperY + lowerY) / 2;                //середина элемента по оси У

        TouchAction action = new TouchAction(driver);           //важно выбрать метод для Appium
        action
                .press(PointOption.point(rightX,middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(leftX,middleY))
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return  elements.size();
    }

    public void assertElementNotPresent(String locator, String error_messange)
    {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    public void assertElementPresent(String locator, String error_messange)
    {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements == 0) {
            String defaultMessage = "A title not present.";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message,timeoutInSeconds);
        return  element.getAttribute(attribute);
    }

    //метод заменяет параметр {FOLDER_NAME} на переданное имя папки
    protected String getFolderXpathByName(String nameOfFolder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",nameOfFolder);
    }

    //метод, который выбирает ранее созданную папку в reading list по имени папки
    public void openFolderByName(String nameOfFolder)
    {
       String folderNameXpath = getFolderXpathByName(nameOfFolder);

        this.waitForElementAndClick(
              //  By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + nameOfFolder + "']"),
              //  By.xpath(folderNameXpath),
                folderNameXpath,
                "Cannot find folder by name" + nameOfFolder,
                15);
    }

    private By getLocatorByString(String locatorWithType)
    {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"),2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return  By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
