package amazon.actions;

import amazon.interfaces.LoginUI;
import org.openqa.selenium.WebDriver;
import core.AbstractPage;
import core.config.Domains;

public class LoginPO extends AbstractPage {

    WebDriver driver;

    public LoginPO(WebDriver driver) {
        this.driver = driver;
    }

    public void gotoLoginPage() {
        openAnyUrl(driver, Domains.HOME + "ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");
    }

    public void inputUserName(String userName) {
        sendKeyToElement(driver, LoginUI.USERNAME_TXT, userName);
    }

    public void inputPassWord(String userPass) {
        sendKeyToElement(driver, LoginUI.PASSWORD_TXT, userPass);
    }

    public void clickContinue() {
        clickToElement(driver, LoginUI.CONTINUE_BTN);
    }

    public void clickLogin() {
        clickToElement(driver, LoginUI.LOGIN_BTN);
    }

}
