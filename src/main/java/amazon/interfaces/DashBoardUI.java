package amazon.interfaces;

public class DashBoardUI {
    public static final String USER_NAME_LBL = "//span[@id='nav-link-accountList-nav-line-1']";
    public static final String SEARCH_TXT = "//input[@id='twotabsearchtextbox']";
    public static final String SEARCH_BTN = "//input[@id='nav-search-submit-button']";
    public static final String CAPACITY_LBL = "//div[@id='filters']//span[contains(text(),'%s')]";
    public static final String SORT_BY_CMB = "//span[contains(text(),'Sort by:')]";
    public static final String LIST_OPTION_LBL = "//ul[@role='listbox']//li[%s]";
    public static final String TITLE_ITEM_LBL = "(//h2//a)[%s]";
}
