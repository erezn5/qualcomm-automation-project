package org.qualcomm.automation.framework.ui.pages.careers.detailed_open_positions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qualcomm.automation.framework.ui.infra_components.PageElement;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

public class StartYourApplicationPopupWindow extends PageElement {
    @FindBy(css = "div[data-automation-id='wd-popup-frame'] h2")
    private WebElement popupTextTitleElm;
    public StartYourApplicationPopupWindow(DriverWrapper driver) {
        super(driver);
    }

    public String getPopupTitle(){
        return getTxt(popupTextTitleElm);
    }
}
