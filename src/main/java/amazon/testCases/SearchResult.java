package amazon.testCases;

import amazon.actions.DashBoardPO;
import amazon.actions.LoginPO;
import core.AbstractTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class SearchResult extends AbstractTest {

    public LoginPO loginPO;
    public DashBoardPO dashBoardPO;

    @BeforeClass()
    public void beforeClass() {
        loginPO = new LoginPO(driver);
        dashBoardPO = new DashBoardPO(driver);
    }

    @Test(dataProvider = "dataSearch")
    public void searchItems(String keyword, int capacity, int option) throws InterruptedException {
        dashBoardPO.gotoHomePage();
        dashBoardPO.searchItems(keyword);
        dashBoardPO.clickSearch();
        dashBoardPO.clickCapacity(capacity);
        sleep(2);
        dashBoardPO.clickSortBy();
        dashBoardPO.clickOption(option);
        dashBoardPO.clickTitleItem(1);
    }

    @DataProvider(name = "dataSearch")
    public Object[][] dataSearch(Method method) {
        Object[][] result = null;
        if (method.getName().equals("searchItems")) {
            result = new Object[][]{
                    {"sd card", 64, 3},
            };
        }
        return result;
    }
}
