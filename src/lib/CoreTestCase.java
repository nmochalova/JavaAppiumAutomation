//Инициализация тестов и их завершение
package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    public void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\org.wikipedia_50377_apps.evozi.com.apk");
        capabilities.setCapability("orientation","PORTRAIT"); //LANDSCAPE, PORTRAIT

        driver = new AndroidDriver(new URL(AppiumURL),capabilities);
    }

    @Override
    public void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
}
