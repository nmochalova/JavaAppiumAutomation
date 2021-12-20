package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListPageObject extends MyListsPageObject {
    static
    {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name='{TITLE}')]";
        SEARCH_RESULT_ELEMENT_BY_LIST = "id:org.wikipedia:id/page_list_item_container"; //ВНИМАНИЕ! Обновить локатор! Нужен id статей в Избранном
    }
    public iOSMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
