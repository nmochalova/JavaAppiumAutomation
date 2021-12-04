//содержит методы, к которым будут обращаться тесты
package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_messanger, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(By by, String error_messanger)
    {
        return waitForElementPresent(by,error_messanger,5);
    }

    public WebElement waitForElementAndClick(By by, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_messanger,timeoutInSecond);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by,error_messanger,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_messanger, long timeoutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(By by, String error_messanger, long timeoutInSecond)
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
    public boolean assertElementHasText (By by, String text_element, String error_messanger)
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
    public List<WebElement> waitForElementsPresent(By by, String error_messanger, long timeoutInSecond)
    {
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
                .press(x,startY)
                .waitAction(timeOfSwipe)
                .moveTo(x,endY)
                .release()
                .perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by,String error_messange, int maxSwipes)
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

    public   void swipeElementToLeft(By by, String error_messange)
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

    public int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return  elements.size();
    }

    public void assertElementNotPresent(By by, String error_messange)
    {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    public void assertElementPresent(By by, String error_messange)
    {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements == 0) {
            String defaultMessage = "A title not present.";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        return  element.getAttribute(attribute);
    }
}
