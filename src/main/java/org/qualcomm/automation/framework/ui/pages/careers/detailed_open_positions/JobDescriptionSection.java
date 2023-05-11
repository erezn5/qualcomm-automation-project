package org.qualcomm.automation.framework.ui.pages.careers.detailed_open_positions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qualcomm.automation.framework.ui.infra_components.PageElement;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

public class JobDescriptionSection extends PageElement {
    @FindBy(css = "section[data-automation-id='jobDetails'] a[href*='details']")
    private WebElement applyElmBtn;
    public JobDescriptionSection(DriverWrapper driver) {
        super(driver);
    }

    public void clickApplyJob(){
        clickElm(applyElmBtn);
    }

}
