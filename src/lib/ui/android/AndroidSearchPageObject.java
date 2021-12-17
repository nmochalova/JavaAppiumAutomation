package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
        SEARCH_ARTICLE_FOR_TITLE_AND_DESC = "xpath://android.widget.LinearLayout[*[@text='{SUBSTRING_TITLE}'] and *[@text='{SUBSTRING_DESC}']]";
        SEARCH_RESULT_TITLE = "xpath://*[@resource-id='org.wikipedia:id/view_page_header_container']/*[@resource-id='org.wikipedia:id/view_page_title_text']";
        SEARCH_STRING_TEXT = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_CONTAINER = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
    }
        public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
