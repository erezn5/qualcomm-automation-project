package org.qualcomm.automation.framework.ui.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.awaitility.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qualcomm.automation.framework.conf.EnvConf;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DriverWrapper implements WebDriver {
    private final WebDriver driver;
    private static final Duration WAIT_ELEMENT_TIMEOUT = new Duration(EnvConf.getAsInteger("ui.locator.timeout.sec"), TimeUnit.SECONDS);

    private DriverWrapper(WebDriver driver){
        this.driver = driver;
    }

    public static DriverWrapper open(Browser browser){
        switch (browser) {
            case FIREFOX:
                return createFireFoxInst();
            case CHROME:
                return createChromeInst();
            default:
                throw new IllegalArgumentException("'" + browser + "'no such browser type");
        }
    }

    private static DriverWrapper createFireFoxInst() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.setHeadless((EnvConf.getAsBoolean("selenium.headless")));
        FirefoxDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return new DriverWrapper(driver);
    }

    private static DriverWrapper createChromeInst(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--lang=" + EnvConf.getProperty("selenium.locale"));
        options.setHeadless(EnvConf.getAsBoolean("selenium.headless"));
        options.setAcceptInsecureCerts(true);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.SEVERE);

        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        ChromeDriverService service = ChromeDriverService.createDefaultService();
        ChromeDriver driver = new ChromeDriver(service, options);

        if(!EnvConf.getAsBoolean("selenium.headless")) {//for local testings and visibility
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //enabling downloading resources when driver is headless

        return new DriverWrapper(driver);
    }

    public File getScreenshotAsFile() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public byte[] getScreenshotAsByte() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public boolean isVisible(By by, Duration duration) {
        try {
            waitForElmVisibility(duration, by);
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    public WebElement waitForElmVisibility(Duration duration, By by) {
        WebDriverWait wait = new WebDriverWait(driver, duration.getTimeUnit().toSeconds(duration.getValue()));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForElmClickable(Duration duration, By by) {
        WebDriverWait wait = new WebDriverWait(driver, duration.getTimeUnit().toSeconds(duration.getValue()));
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public boolean waitForElmContains(WebElement element, String text) {
        return waitForElmContains(WAIT_ELEMENT_TIMEOUT, element, text);
    }

    private boolean waitForElmContains(Duration duration, WebElement element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, duration.getTimeUnit().toSeconds(duration.getValue()));
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
//            Log.error(
//                    String.format("failed waiting to locator=[%s] text=[%s] for timeout [%s] secs", element, text, duration));
            return false;
        }
    }

    public void mouseOver(WebElement targetElm) {
        Actions actions = new Actions(driver);
        actions.moveToElement(targetElm).build().perform();
    }

    private Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    //This will scroll the page till the element is found
    public void scrollToViewScript(WebElement element){
        executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToTop(){
        executeScript("window.scrollBy(0,-5000)", "");
    }

    public static boolean isChildElmVisible(WebElement parentElm, By childBy) {
        try {
            return parentElm.findElement(childBy).isEnabled();
        } catch (NoSuchElementException e) {
//            Log.info("child element don't exist", e);
            return false;
        }
    }

    public boolean waitForElementInvisible(Duration duration, By by) {
        WebDriverWait wait = new WebDriverWait(driver, duration.getTimeUnit().toSeconds(duration.getValue()));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement clickOnEnabledElm(Duration duration, By by) {
        WebDriverWait wait = new WebDriverWait(driver, duration.getTimeUnit().toSeconds(duration.getValue()));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Override
    public void get(String s) {
        driver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    public void scrollTopOFThePage() {
        executeScript("window.scrollBy(0,-5000)", "");
    }

    public void waitPageLoadCompletely() {
        new WebDriverWait(driver, Duration.ONE_MINUTE.getValue()).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}
