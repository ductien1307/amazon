package core;

import core.config.Domains;
import core.config.Paths;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AbstractTest extends AbstractPage {
    public static RemoteWebDriver driver;
    //public Logger log = LogManager.getLogger();
    public static Logger log;
    public static File folder;
    public String osName = System.getProperty("os.name").toLowerCase();
    public DesiredCapabilities cap = null;

    @Parameters({"browser", "environment", "headless", "grid"})
    @BeforeSuite
    public void beforeSuite(@Optional String browser, @Optional String environment, @Optional Integer headless, @Optional Integer grid) throws MalformedURLException {
        log = LogManager.getLogger();
        setPathYourOS();
        setDomain(environment);
        openMultiBrowser(browser, headless, grid);
    }

    @AfterSuite
    protected void afterSuite() throws IOException, InterruptedException {
        //closeBrowser();
    }

    protected void openMultiBrowser(String browser, Integer headless, Integer grid) throws MalformedURLException {
        folder = new File(UUID.randomUUID().toString());
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions optionsChrome = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.prompt_for_download", false);
            optionsChrome.setExperimentalOption("prefs", prefs);
            prefs.put("download.default_directory", folder.getAbsolutePath());
            optionsChrome.setExperimentalOption("prefs", prefs);
            optionsChrome.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            optionsChrome.setPageLoadStrategy(PageLoadStrategy.NONE);
            optionsChrome.addArguments("window-size=2000,1500");
            if (headless == 1) {
                optionsChrome.setHeadless(true);
            }
            if (grid == 1) {
                cap = DesiredCapabilities.chrome();
                driver = new RemoteWebDriver(new URL(Domains.HUB), cap);
            } else {
                driver = new ChromeDriver(optionsChrome);
                driver.manage().deleteAllCookies();
            }
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions optionsFirefox = new FirefoxOptions();
            optionsFirefox.addArguments("window-size=2000,1500");
            if (headless == 1) {
                optionsFirefox.setHeadless(true);
            }
            if (grid == 1) {
                cap = DesiredCapabilities.firefox();
                driver = new RemoteWebDriver(new URL(Domains.HUB), cap);
            } else {
                driver = new FirefoxDriver(optionsFirefox);
            }
        } else if (browser.equals("opera")) {
            WebDriverManager.operadriver().setup();
            if (grid == 1) {
                cap = DesiredCapabilities.opera();
                driver = new RemoteWebDriver(new URL(Domains.HUB), cap);
            } else {
                driver = new OperaDriver();
            }
        }
    }

    protected void setPathYourOS() {
        if (isWindows()) {
            Paths.PATH_MEDIA = Paths.PATH_SYSTEM + Paths.PATH_MEDIA_WINDOWS;
            Paths.PATH_SCREENSHOT = Paths.PATH_SYSTEM + Paths.PATH_SCREENSHOT_WINDOWS;
            Paths.PATH_DOWNLOAD = Paths.PATH_SYSTEM + Paths.PATH_DOWNLOAD_WINDOWS;
        } else if (isMac()) {
            Paths.PATH_MEDIA = Paths.PATH_SYSTEM + Paths.PATH_MEDIA_MAC;
            Paths.PATH_SCREENSHOT = Paths.PATH_SYSTEM + Paths.PATH_SCREENSHOT_MAC;
            Paths.PATH_DOWNLOAD = Paths.PATH_SYSTEM + Paths.PATH_DOWNLOAD_MAC;
        }
    }

    protected void closeBrowser() throws InterruptedException, IOException {
        String cmd = "";
        driver.quit();
        if (isMac()) {
            cmd = "pkill chromedriver";
        } else if (isWindows()) {
            cmd = "taskkill /IM chromedriver.exe /F";
        }
        if (cmd != "") {
            if (driver.toString().toLowerCase().contains("chrome")) {
                killProcess(cmd);
            } else if (driver.toString().toLowerCase().contains("internetexplorer")) {
                killProcess(cmd);
            }
        }
    }

    public void killProcess(String cmd) throws InterruptedException, IOException {
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
    }

    protected void setDomain(String environment) {
        if (environment.equals(Domains.STG)) {
            Domains.ENVIRONMENT = Domains.STG;
            Domains.HOME = Domains.HOME_STG;

        } else if (environment.equals(Domains.DEV)) {
            Domains.ENVIRONMENT = Domains.DEV;
            Domains.HOME = Domains.HOME_DEV;

        } else if (environment.equals(Domains.PROD)) {
            Domains.ENVIRONMENT = Domains.PROD;
            Domains.HOME = Domains.HOME_PROD;

        }
        if (isMac()) {
            Domains.HUB = Domains.HUB_LOCAL;
        } else {
            Domains.HUB = Domains.HUB_SERVER;
        }
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        printInfoFunction(actual);
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
        } catch (Throwable e) {
            pass = false;
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyContains(String actual, String expected) {
        printInfoFunction(actual);
        boolean pass = true;
        try {
            Assert.assertTrue(actual.toUpperCase().contains(expected.toUpperCase()));
        } catch (Throwable e) {
            pass = false;
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        printInfoFunction();
        return verifyPassed(condition, false);
    }

    protected boolean verifyFalse(boolean condition) {
        printInfoFunction();
        return verifyFailed(condition, false);
    }

    private boolean verifyPassed(boolean condition, boolean flag) {
        boolean pass = true;
        if (flag == false) {
            try {
                Assert.assertTrue(condition);
            } catch (Throwable e) {
                pass = false;
                Reporter.getCurrentTestResult().setThrowable(e);
            }
        } else {
            Assert.assertTrue(condition);
        }
        return pass;
    }

    private boolean verifyFailed(boolean condition, boolean flag) {
        boolean pass = true;
        if (flag == false) {
            try {
                Assert.assertFalse(condition);
            } catch (Throwable e) {
                pass = false;
                Reporter.getCurrentTestResult().setThrowable(e);
            }
        } else {
            Assert.assertFalse(condition);
        }
        return pass;
    }

    protected boolean isWindows() {
        return (osName.indexOf("win") >= 0);
    }

    protected boolean isMac() {
        return (osName.indexOf("mac") >= 0);
    }

    protected boolean isUnix() {
        return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0);
    }

    protected boolean isSolaris() {
        return (osName.indexOf("sunos") >= 0);
    }

    protected String getEmailRandom() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr + "@yopmail.com";
    }

    protected String getTextRandom() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected String getNumberRandom() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected Integer convertStringToInt(String str_num) {
        String str_num_temp = String.format("%.0f", Double.parseDouble(str_num));
        Integer num_int = Integer.parseInt(str_num_temp);
        return num_int;
    }

    protected void uploadMediaByRobot(String fileName) {
        //File Need to be imported
        File file = new File(Paths.PATH_SYSTEM + "/src/main/resources/media/" + fileName);
        StringSelection stringSelection = new StringSelection(file.getAbsolutePath());
        //Copy to clipboard
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        // Cmd + Tab is needed since it launches a Java app and the browser looses focus
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_META);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(1000);
        //Open Goto window
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_G);
        robot.keyRelease(KeyEvent.VK_META);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_G);
        //Paste the clipboard value
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_META);
        robot.keyRelease(KeyEvent.VK_V);
        robot.delay(1000);
        //Press Enter key to close the Goto window and Upload window
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void checkBrokenLinks() {
        java.util.List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        //java.util.List<WebElement> allImages = driver.findElements(By.tagName("img"));
        boolean flag = true;
        for (WebElement item : allLinks) {
            String urlLink = item.getAttribute("href");
            if (urlLink == null || urlLink.isEmpty()) {
                log.debug("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            if (urlLink.contains("linkedin.com") == true || urlLink.contains("faq.vietnamworks.com") == true || urlLink.contains("vnexpress.net") == true) {
                log.debug("URL belongs to another domain, skipping it.");
                continue;
            }
            if (verifyLink(urlLink) == false) {
                flag = false;
            }
        }
        /*for (WebElement item : allImages) {
            String urlLink = item.getAttribute("src");
            if (verifyLink(urlLink) == false) {
                flag = false;
            }
        }*/
        if (flag == false) {
            verifyTrue(false);
        }
    }

    private Boolean verifyLink(String urlLink) {
        Boolean flag = true;
        int responseCode = 200;
        try {
            java.net.URLEncoder.encode(urlLink, "UTF-8");
            URL link = new URL(urlLink);
            HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
            httpConn.setRequestMethod("HEAD");
            httpConn.setConnectTimeout(3000);
            httpConn.connect();
            responseCode = httpConn.getResponseCode();
            if (responseCode >= 400) {
                log.error("This is broken link(" + responseCode + ") " + urlLink);
                flag = false;
            }
        } catch (Exception e) {
            log.error(urlLink);
            flag = false;
        }
        return flag;
    }

    protected void sleep(int second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }

    public void deleteFolderDownload() {
        for (File file : folder.listFiles()) {
            file.delete();
        }
        folder.delete();
    }

        public void takeScreenshot(String screenshotName) throws IOException {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = Paths.PATH_SCREENSHOT + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
