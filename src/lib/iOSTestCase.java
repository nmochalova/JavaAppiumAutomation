package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

/**
 * Инициализация тестов и их завершение
 * Вся работа по запуску и остановке приложения. Здесь приложение запускается с необходимыми параметрами,
 * в том числе с разворотом в портретную ориентацию в начале тестов. А также здесь тесты останавливаются.
 * Здесь же находятся методы по развору экрана в портретную/альбомную ориентацию и уход в бэкграунд.
 */
public class iOSTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone SE"); //название симулятора
        capabilities.setCapability("platformVersion","11.3");
        capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\Wikipedia.app");
        capabilities.setCapability("orientation","PORTRAIT"); //LANDSCAPE, PORTRAIT

        driver = new IOSDriver(new URL(AppiumURL),capabilities);
    }

    @Override
    protected void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }

    //метод переворачивает экран из альбомную в портретную
    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    //метод переворачивает экран из портретной в альбомную
    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    //метод отправляет приложение в background
    protected void backgroundApp(int seconds)
    {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }
}