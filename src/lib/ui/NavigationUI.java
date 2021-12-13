//методы для навигации по приложению
package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUI extends MainPageObject {

    private static final String
        MY_LIST_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    //Инициализация драйвера
    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "Cannot find navigation button to My list",
                15
        );
    }
}
