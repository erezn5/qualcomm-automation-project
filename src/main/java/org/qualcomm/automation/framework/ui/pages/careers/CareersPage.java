package org.qualcomm.automation.framework.ui.pages.careers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qualcomm.automation.framework.ui.infra_components.BasePage;
import org.qualcomm.automation.framework.ui.pages.careers.detailed_open_positions.DetailedOpenPositionsPage;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

public class CareersPage extends BasePage {
    @FindBy(css = "a[href*='External']")
    private WebElement searchOpenCareersBtnElm;

    public CareersPage(DriverWrapper driver) {
        super(driver, "/company/careers");
    }

    public DetailedOpenPositionsPage clickSearchForOpenPositions(){
        clickElm(searchOpenCareersBtnElm);
        switchBrowserWindow();
        return new DetailedOpenPositionsPage(driver);
    }
}
