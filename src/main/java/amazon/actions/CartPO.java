package amazon.actions;

import amazon.interfaces.CartUI;
import core.AbstractPage;
import org.openqa.selenium.WebDriver;

public class CartPO extends AbstractPage {

    WebDriver driver;

    public CartPO(WebDriver driver) {
        this.driver = driver;
    }

    public void addToCart() {
        clickToElement(driver, CartUI.ADD_TO_CART_BTN);
    }

    public void clickCartInfo() {
        clickToElement(driver, CartUI.CART_INFO_LBL);
    }

    public void clickQuantityComboBox() {
        waitForElementVisible(driver, CartUI.QUANTITY_CMB);
        clickToElement(driver, CartUI.QUANTITY_CMB);
    }

    public void clickQuantityLabel(int qty) {
        clickToElement(driver, String.format(CartUI.QUANTITY_OPTION_LBL, String.valueOf(qty)));
    }

    public String getPriceItem() {
        return getTextElement(driver, CartUI.PRICE_ITEM_LBL);
    }

    public String getPriceSubTotal() {
        return getTextElement(driver, CartUI.PRICE_SUBTOTAL_LBL);
    }

    public void clickDeleteCart() {
        clickToElement(driver, CartUI.DELETE_LBL);
    }

    public boolean checkCartExist() {
        return isDisplayed(driver, CartUI.DELETE_LBL);
    }

}
