package amazon.testCases;

import amazon.actions.CartPO;
import amazon.actions.LoginPO;
import core.AbstractTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class Cart extends AbstractTest {

    public LoginPO loginPO;
    public CartPO cartPO;

    @BeforeClass()
    public void beforeClass() {
        loginPO = new LoginPO(driver);
        cartPO = new CartPO(driver);
    }

    @Test(dataProvider = "dataCart")
    public void verifyCart(int qty) throws InterruptedException {
        String priceItem = cartPO.getPriceItem();
        cartPO.addToCart();
        cartPO.clickCartInfo();
        sleep(2);
        cartPO.clickQuantityComboBox();
        cartPO.clickQuantityLabel(qty);
        sleep(2);
        String priceTotalSub = cartPO.getPriceSubTotal();
        priceItem = priceItem.substring(1);
        priceTotalSub = priceTotalSub.substring(1);
        String[] priceTotalSubArr = priceTotalSub.split(",");
        priceTotalSub = priceTotalSubArr[0] + priceTotalSubArr[1];
        Double priceTotalSubExpected = Double.parseDouble(priceItem) * qty;
        verifyEquals(Double.parseDouble(priceTotalSub), priceTotalSubExpected);
    }

    @DataProvider(name = "dataCart")
    public Object[][] dataCart(Method method) {
        Object[][] result = null;
        if (method.getName().equals("verifyCart")) {
            result = new Object[][]{
                    {3},
            };
        }
        return result;
    }
}
