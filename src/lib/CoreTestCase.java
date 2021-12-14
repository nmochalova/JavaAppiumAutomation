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
public class CoreTestCase extends TestCase {
    private static final String PLATFORM_IOS = "iOS";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
        getDriverByPlatform(capabilities);
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

    //метод получает значение параметра PLATFORM из переменной среды
    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE"); //название симулятора
            capabilities.setCapability("platformVersion", "11.3");
            capabilities.setCapability("app", "C:\\Work\\Git\\JavaAppiumAutomation\\apks\\Wikipedia.app");
            capabilities.setCapability("orientation", "PORTRAIT"); //LANDSCAPE, PORTRAIT
        } else if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("automationName","Appium"); //"Appium" "UIAutomator2
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            //capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\org.wikipedia_50377_apps.evozi.com.apk");
            capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\old-wiki.apk");
            capabilities.setCapability("orientation","PORTRAIT"); //LANDSCAPE, PORTRAIT

        } else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }

        return capabilities;
    }

    private void getDriverByPlatform(DesiredCapabilities capabilities) throws Exception {
        String platform = System.getenv("PLATFORM");

        if (platform.equals(PLATFORM_ANDROID)) {
            driver = new AndroidDriver(new URL(AppiumURL),capabilities);
        } else if (platform.equals(PLATFORM_IOS)) {
            driver = new IOSDriver(new URL(AppiumURL),capabilities);
        } else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }
    }
}
