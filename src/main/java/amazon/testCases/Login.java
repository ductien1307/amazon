package amazon.testCases;

import amazon.actions.DashBoardPO;
import amazon.actions.LoginPO;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import core.AbstractTest;

import java.lang.reflect.Method;

public class Login extends AbstractTest {

    public LoginPO loginPO;
    public DashBoardPO dashBoardPO;

    @BeforeClass()
    public void beforeClass() {
        loginPO = new LoginPO(driver);
        dashBoardPO = new DashBoardPO(driver);
    }

    @Test(dataProvider = "dataSignIn")
    public void validAccount(String userName, String passWord) {
        loginPO.gotoLoginPage();
        loginPO.inputUserName(userName);
        loginPO.clickContinue();
        loginPO.inputPassWord(passWord);
        loginPO.clickLogin();
        String userNameActual = dashBoardPO.getUserName();
        verifyContains(userNameActual, "Tien");
    }

    @DataProvider(name = "dataSignIn")
    public Object[][] dataSignIn(Method method) {
        Object[][] result = null;
        if (method.getName().equals("validAccount")) {
            result = new Object[][]{
                    {"tienphp@gmail.com", "Auto@123"},
            };
        }
        return result;
    }
}
