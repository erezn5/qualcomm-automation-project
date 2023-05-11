package org.qualcomm.automation.framework.ui.infra_components;

import org.awaitility.Duration;
import org.awaitility.core.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.qualcomm.automation.framework.conf.EnvConf;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;
import org.qualcomm.automation.framework.utils.Waiter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class PageElement {
    protected static final Duration WAIT_TIMEOUT = new Duration(EnvConf.getAsInteger("ui.locator.timeout.sec"), TimeUnit.SECONDS);
    protected final DriverWrapper driver;
    protected final By loaderSpinner = By.cssSelector("div[class='text-center mx-auto vertical-center form-loaer']");

    public PageElement(DriverWrapper driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> createElmListFromCss(String text) {
        return driver.findElements(By.cssSelector(text));
    }

    protected void setText(WebElement txtElm, String text) {
        txtElm.sendKeys(text);
    }

    protected void clickButton(By byBth, Duration timeout) {
        WebElement bthElem = waitForClickableElm(byBth, timeout);
        clickElmRetry(bthElem);
    }

    protected void scrollElmIntoView(WebElement elem) {
        driver.scrollToViewScript(elem);
    }

    private WebElement waitForClickableElm(By by, Duration timeout) {
        return driver.waitForElmClickable(timeout, by);
    }

    protected void clickButton(By byBth) {
        clickButton(byBth, WAIT_TIMEOUT);
    }

    protected boolean isVisible(By by, Duration duration) {
        return driver.isVisible(by, duration);
    }

    protected void clickElm(WebElement btnElem) {
        btnElem.click();
    }

    protected void clickElmRetry(final WebElement bthElm) {
        Condition<Boolean> condition = () -> {
            try {
                clickElm(bthElm);
//                Log.i("button=[%s] clicked successfully", bthElm);
                return true;
            } catch (Exception e) {
//                Log.debug(String.format("failed to clickRow on button=[%s]", bthElm), e);
                return false;
            }
        };
        Boolean success = Waiter.waitCondition(WAIT_TIMEOUT, condition);
        throwElmNotClickable(success, bthElm);
    }

    protected static void throwElmNotClickable(Boolean clicked, WebElement bthElm) {
        if (clicked == null || !clicked) {
            throw new ElementClickInterceptedException(String.format("failed to clickRow on bth element=[%s]", bthElm));
        }
    }

    protected void waitElmBecomeInvisible(Duration duration, By by) {
        driver.waitForElementInvisible(duration, by);
    }

    protected boolean waitForElmBecomeVisible(By by) {
        try {
            return driver.waitForElmVisibility(Duration.TEN_SECONDS, by) != null;
        }catch(TimeoutException timeoutException){
            return false;
        }
    }

    protected void waitForLoaderSpinnerBecomeInvisible() {
        waitElmBecomeInvisible(new Duration(60, TimeUnit.SECONDS), loaderSpinner);
    }

    public void switchBrowserWindow() {
        // Get the handles of all open windows/tabs
        Set<String> allWindows = driver.getWindowHandles();

        // Switch to the new window
        for (String handle : allWindows) {
            driver.switchTo().window(handle);
        }
        waitPageLoadCompletely();
    }

    protected void waitPageLoadCompletely() {
        driver.waitPageLoadCompletely();
    }

    protected void scrollTopOFThePage() {
        driver.scrollTopOFThePage();
    }

    protected String getTxt(WebElement elm){
        return elm.getText();
    }
}
