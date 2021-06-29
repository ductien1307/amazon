package amazon.actions;

import amazon.interfaces.DashBoardUI;
import core.config.Domains;
import org.openqa.selenium.WebDriver;
import core.AbstractPage;

public class DashBoardPO extends AbstractPage {

    WebDriver driver;

    public DashBoardPO(WebDriver driver) {
        this.driver = driver;
    }

    public void gotoHomePage() {
        openAnyUrl(driver, Domains.HOME + "ref=nav_logo");
    }

    public String getUserName() {
        return getTextElement(driver, DashBoardUI.USER_NAME_LBL);
    }

    public void searchItems(String keyword) {
        sendKeyToElement(driver, DashBoardUI.SEARCH_TXT, keyword);
    }

    public void clickSearch() {
        clickToElement(driver, DashBoardUI.SEARCH_BTN);
    }

    public void clickCapacity(int capacity) {
        clickToElement(driver, String.format(DashBoardUI.CAPACITY_LBL, String.valueOf(capacity)));
    }

    public void clickSortBy() {
        clickToElement(driver, DashBoardUI.SORT_BY_CMB);
    }

    public void clickOption(int option) {
        clickToElement(driver, String.format(DashBoardUI.LIST_OPTION_LBL, option));
    }

    public void clickTitleItem(int index) {
        clickToElement(driver, String.format(DashBoardUI.TITLE_ITEM_LBL, index));
    }

}
