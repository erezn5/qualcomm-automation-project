package com.qualcomm.automation.tests.ui_tests;

import com.qualcomm.automation.tests.BaseUiTest;
import org.openqa.selenium.WebElement;
import org.qualcomm.automation.framework.ui.pages.careers.CareersPage;
import org.qualcomm.automation.framework.ui.pages.careers.detailed_open_positions.DetailedOpenPositionsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class UITest extends BaseUiTest {
    CareersPage careersPage;
    DetailedOpenPositionsPage detailedOpenPositionsPage;

    @BeforeClass(alwaysRun = true)
    public void setupTest(){
        careersPage = new CareersPage(driver);
    }

    @Test(priority = 10)
    public void testProgramManagerApplyAvailability(){
        homePage.clickOnOurCompanyOption("Careers");

        detailedOpenPositionsPage = careersPage.clickSearchForOpenPositions();
        detailedOpenPositionsPage.selectItemFromJobCategoryDropDownMenu("Engineering Services Group");
        detailedOpenPositionsPage.filterSearchJobTxt("Program Manager");

        List<WebElement> openedPositionsLstElm = detailedOpenPositionsPage.getOpenedPositions();

        Assert.assertFalse(openedPositionsLstElm.isEmpty(), "no ‘Program Manager’ jobs were found.");

        openedPositionsLstElm.get(openedPositionsLstElm.size()-1).click();

        detailedOpenPositionsPage.applyJob();

        Assert.assertEquals(detailedOpenPositionsPage.getApplicationPopupTitle(), "Start Your Application");
    }
}
