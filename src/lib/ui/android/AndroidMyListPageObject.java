package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListPageObject extends MyListsPageObject {
     static
     {
         ARTICLE_BY_TITLE_TPL = "xpath://android.widget.TextView[@text='{TITLE}']";
         SEARCH_RESULT_ELEMENT_BY_LIST = "id:org.wikipedia:id/page_list_item_container";
     }

    public AndroidMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
