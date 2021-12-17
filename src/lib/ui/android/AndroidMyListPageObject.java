package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListPageObject extends MyListsPageObject {
     static
     {
         ARTICLE_BY_TITLE_TPL = "xpath://android.widget.TextView[@text='{TITLE}']";
     }

    public AndroidMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
