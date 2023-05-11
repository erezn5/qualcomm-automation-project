package com.qualcomm.automation.tests;

import org.qualcomm.automation.framework.conf.EnvConf;
import org.qualcomm.automation.framework.ui.pages.home.QualCommHomePage;
import org.qualcomm.automation.framework.ui.selenium.Browser;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseUiTest extends BaseTest {
    private static final Browser BROWSER = Browser.valueOf(EnvConf.getProperty("ui.browser.type"));
    protected QualCommHomePage homePage;
    protected DriverWrapper driver;
    @BeforeClass(alwaysRun = true)
    public final void BaseUiSetup(ITestContext context) {
        driver = DriverWrapper.open(BROWSER);
        homePage = new QualCommHomePage(driver);
        navigateTo(UI_BASE_URL);
    }

    @AfterClass(alwaysRun = true)
    public final void baseTeardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void navigateTo(String url) {
        driver.get(url);
        System.out.println("Successfully navigated to =[" + url + "]");
    }
}