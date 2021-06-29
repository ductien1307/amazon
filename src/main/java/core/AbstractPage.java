package core;

import core.config.Paths;
import core.config.TimeOut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class AbstractPage {
    public static final Logger log = LogManager.getLogger();

    public void printInfo(Object param) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        log.info(stackTrace[2].getMethodName() + " : " + param);
    }

    public void printInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        log.info(stackTrace[2].getMethodName());
    }

    public void printInfoFunction() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        log.info(stackTrace[1].getMethodName());
    }

    public void printInfoFunction(Object param1, Object param2) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        log.info(stackTrace[1].getMethodName());
    }

    public void printInfoFunction(Object param) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        log.info(stackTrace[1].getMethodName() + " : " + param);
    }

    protected void openAnyUrl(WebDriver driver, String url) {
        printInfo(url);
        driver.get(url);
    }

    protected void clickToElement(WebDriver driver, String locator) {
        printInfo(locator);
        waitForElementClickable(driver, locator);
        WebElement element = driver.findElement(By.xpath(locator));
        element.click();
    }

    protected void sendKeyToElement(WebDriver driver, String locator, String value) {
        printInfo(value);
        waitForElementVisible(driver, locator);
        WebElement element = driver.findElement(By.xpath(locator));
        element.clear();
        element.sendKeys(value);
    }

    protected String getTextElement(WebDriver driver, String locator) {
        waitForElementVisible(driver, locator);
        WebElement element = driver.findElement(By.xpath(locator));
        String textElement = element.getText().trim();
        return textElement;
    }

    protected String getValueBy(WebDriver driver, String locator, String attribute) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getAttribute(attribute);
    }

    protected boolean waitForElementVisible(WebDriver driver, String locator) {
        boolean flag = true;
        try {
            By by = By.xpath(locator);
            WebDriverWait wait = new WebDriverWait(driver, TimeOut.TIMEOUT_SECOND);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    protected boolean waitForElementVisible(WebDriver driver, String locator, Integer waitSecond) {
        boolean flag = true;
        try {
            By by = By.xpath(locator);
            WebDriverWait wait = new WebDriverWait(driver, waitSecond);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    protected void waitForElementInvisibility(WebDriver driver, String locator) {
        By by = By.xpath(locator);
        WebDriverWait wait = new WebDriverWait(driver, TimeOut.TIMEOUT_SECOND);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void waitForElementInvisibility(WebDriver driver, String locator, Integer waitSecond) {
        By by = By.xpath(locator);
        WebDriverWait wait = new WebDriverWait(driver, waitSecond);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void waitForElementPresence(WebDriver driver, String locator) {
        By by = By.xpath(locator);
        WebDriverWait wait = new WebDriverWait(driver, TimeOut.TIMEOUT_SECOND);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void clickByJS(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement ele = driver.findElement(By.xpath(locator));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", ele);
    }

    protected void clickByText(WebDriver driver, String text) {
        printInfo(text);
        WebElement ele = driver.findElement(By.xpath(String.format("//*[contains(text(),'%s')]", text)));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", ele);
    }

    protected void switchToNewTab(WebDriver driver) {
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(0));
        driver.close();
        driver.switchTo().window(newTab.get(1));
    }

    protected void switchToTab(WebDriver driver, Integer index) {
        ArrayList<String> window = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(window.get(index));
    }

    protected void switchToFrame(WebDriver driver, String name) throws InterruptedException {
        Thread.sleep(1000);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.switchTo().frame(name);
        driver.manage().timeouts().implicitlyWait(TimeOut.TIMEOUT_SECOND, TimeUnit.SECONDS);
    }

    protected void switchToModal(WebDriver driver) {
        driver.switchTo().activeElement();
    }

    protected void defaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    protected void acceptAlert(WebDriver driver) throws InterruptedException {
        printInfo();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
    }

    protected void pressTabKey(WebDriver driver) {
        printInfoFunction();
        Actions act = new Actions(driver);
        act.sendKeys(Keys.TAB).build().perform();
    }

    protected void pressEnterKey(WebDriver driver) {
        printInfoFunction();
        Actions act = new Actions(driver);
        act.sendKeys(Keys.ENTER).build().perform();
    }

    protected void pressDeleteKey(WebDriver driver) {
        printInfoFunction();
        Actions act = new Actions(driver);
        act.sendKeys(Keys.BACK_SPACE).build().perform();
    }

    protected void waitForElementClickable(WebDriver driver, String locator) {
        By by = By.xpath(locator);
        WebDriverWait wait = new WebDriverWait(driver, TimeOut.TIMEOUT_SECOND);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected boolean waitForElementClickable(WebDriver driver, String locator, int timeOutSecond) {
        boolean flag;
        try {
            By by = By.xpath(locator);
            WebDriverWait wait = new WebDriverWait(driver, timeOutSecond);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    protected void clearTextByJS(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", element);
    }

    protected void selectItemInDropdown(WebDriver driver, String locator, String value) {
        printInfo(value);
        Select select = new Select(driver.findElement(By.xpath(locator)));
        select.selectByVisibleText(value);
    }

    protected String getCssStyle(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getAttribute("style");
    }

    protected void waitForAlertIsPresent(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, TimeOut.TIMEOUT_SECOND);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    }

    protected boolean isDisplayed(WebDriver driver, String locator) {
        printInfo(locator);
        try {
            if (waitForElementVisible(driver, locator) == true) {
                WebElement element = driver.findElement(By.xpath(locator));
                if (element.isDisplayed())
                    return element.isDisplayed();
            }
        } catch (Throwable e) {
            return false;
        }
        return false;
    }

    protected boolean isDisplayed(WebDriver driver, String locator, int waitSecond) {
        printInfo(locator);
        try {
            if (waitForElementVisible(driver, locator, waitSecond) == true) {
                WebElement element = driver.findElement(By.xpath(locator));
                if (element.isDisplayed())
                    return element.isDisplayed();
            }
        } catch (Throwable e) {
            return false;
        }
        return false;
    }

    protected boolean isDisplayedImage(WebDriver driver, String locator) {
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            Boolean imageLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
            if (imageLoaded)
                return true;
        } catch (NoSuchElementException e) {
            return false;
        }
        return false;
    }

    protected void hoverToElement(WebDriver driver, String locator) {
        printInfo(locator);
        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(locator));
        builder.moveToElement(element).perform();
    }

    protected void clearTextElement(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        element.clear();
    }

    protected String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    protected String getTitlePage(WebDriver driver) {
        return driver.getTitle();
    }

    protected void back(WebDriver driver) {
        printInfo();
        driver.navigate().back();
    }

    protected void refresh(WebDriver driver) {
        printInfo();
        driver.navigate().refresh();
    }

    protected void forward(WebDriver driver) {
        printInfo();
        driver.navigate().forward();
    }

    protected String getFirstItemSelected(WebDriver driver, String locator) {
        printInfo(locator);
        Select select = new Select(driver.findElement(By.xpath(locator)));
        return select.getFirstSelectedOption().getText();
    }

    protected String getAttributeValue(WebDriver driver, String locator, String attribute) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        return element.getAttribute(attribute);
    }

    protected void checkTheCheckbox(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        if (!element.isSelected()) {
            element.click();
        }
    }

    protected void unCheckTheCheckbox(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        if (element.isSelected()) {
            element.click();
        }
    }

    protected boolean isControlEnabled(WebDriver driver, String locator) {
        boolean flag;
        try {
            waitForElementVisible(driver, locator);
            WebElement element = driver.findElement(By.xpath(locator));
            flag = element.isEnabled();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    protected boolean isControlSelected(WebDriver driver, String locator) {
        printInfo(locator);
        WebElement element = driver.findElement(By.xpath(locator));
        return element.isSelected();
    }

    protected int getSizeElement(WebDriver driver, String locator) {
        waitForElementVisible(driver, locator, TimeOut.DISPLAY_TIMEOUT);
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        return elements.size();
    }

    protected void scrollToElement(WebDriver driver, Object locator) {
        printInfo(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (locator instanceof String) {
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(locator.toString())));
        } else if (locator instanceof WebElement) {
            js.executeScript("arguments[0].scrollIntoView();", locator);
        }
    }

    protected List<WebElement> findElements(WebDriver driver, String locator) {
        printInfo(locator);
        return driver.findElements(By.xpath(locator));
    }

    protected String getCssValue(WebDriver driver, String locator, String propertyName) {
        printInfo(locator);
        return driver.findElement(By.xpath(locator)).getCssValue(propertyName);
    }

    protected void scrollBottomPage(WebDriver driver) {
        printInfo();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected void scrollTopPage(WebDriver driver) {
        printInfo();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollTop)");
    }

    protected void scrollDownByPixel(WebDriver driver, int pixel) {
        printInfoFunction(pixel);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixel + ")");
    }

    protected void scrollUpByPixel(WebDriver driver, int pixel) {
        printInfoFunction(pixel);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + -pixel + ")");
    }

    protected void uploadMedia(WebDriver driver, String locator, String fileName) throws InterruptedException {
        printInfo(fileName);
        Thread.sleep(1000);
        String filePath = Paths.PATH_SYSTEM + "/src/main/resources/media/" + fileName;
        WebElement element = driver.findElement(By.xpath(locator));
        element.sendKeys(filePath);
    }

    protected String xpathDynamic(String xpath, String expected) {
        return String.format(xpath, expected);
    }

    protected void setAttribute(WebDriver driver, WebElement element, String attName, String attValue) {
        printInfo(attValue);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attName, attValue);
    }

    protected String getTextParentElement(WebDriver driver, String locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        return (String) js.executeScript("return arguments[0].childNodes[0].textContent;", element);
    }

    protected String getTextHiddenElement(WebDriver driver, String locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        return (String) js.executeScript("return arguments[0].textContent;", element);
    }

    protected Boolean isDisplayedInDom(WebDriver driver, String locator) {
        printInfo(locator);
        try {
            waitForElementPresence(driver, locator);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    protected void switchToNewWindow(WebDriver driver) {
        printInfo();
        String winHandleBefore = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            if (!winHandleBefore.equals(winHandle)) {
                driver.switchTo().window(winHandle);
            }
        }
    }

    protected String getWindowHandle(WebDriver driver) {
        printInfo();
        return driver.getWindowHandle();
    }

    public void switchToWindowBefore(WebDriver driver, String winHandleBefore) {
        printInfo(winHandleBefore);
        driver.switchTo().window(winHandleBefore);
    }

    public void closeTab(WebDriver driver) {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

    public void closeTab(WebDriver driver, Integer index) {
        ArrayList<String> window = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(window.get(index));
        driver.close();
    }

    protected String getValueHidden(WebDriver driver, String locator) {
        printInfo(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locator));
        return (String) js.executeScript("return arguments[0].value;", element);
    }

    protected String getParameterJS(WebDriver driver, String parameter) {
        printInfo(parameter);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return window." + parameter).toString();
    }

    protected boolean isClickAble(WebDriver driver, String locator) {
        try {
            printInfo(locator);
            waitForElementVisible(driver, locator);
            waitForElementClickable(driver, locator, TimeOut.DISPLAY_TIMEOUT);
            WebElement element = driver.findElement(By.xpath(locator));
            element.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
