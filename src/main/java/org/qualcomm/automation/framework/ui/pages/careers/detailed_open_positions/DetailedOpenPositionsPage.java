package org.qualcomm.automation.framework.ui.pages.careers.detailed_open_positions;

import org.openqa.selenium.WebElement;
import org.qualcomm.automation.framework.ui.infra_components.PageElement;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

import java.util.List;

public class DetailedOpenPositionsPage extends PageElement {
    private final TopDropDownMenuToolBarSection topDropDownMenuToolBarSection;
    private final JobDescriptionSection jobDescriptionSection;
    private final StartYourApplicationPopupWindow startYourApplicationPopupWindow;
    public DetailedOpenPositionsPage(DriverWrapper driver) {
        super(driver);
        topDropDownMenuToolBarSection = new TopDropDownMenuToolBarSection(driver);
        jobDescriptionSection = new JobDescriptionSection(driver);
        startYourApplicationPopupWindow = new StartYourApplicationPopupWindow(driver);
    }

    public void selectItemFromJobCategoryDropDownMenu(String itemToCheckTxt){
        topDropDownMenuToolBarSection.selectItemFromJobCategoryDropDownMenu(itemToCheckTxt);
    }

    public void filterSearchJobTxt(String jobTypeTxt){
        topDropDownMenuToolBarSection.filterSearchJobTxt(jobTypeTxt);
    }

    public List<WebElement> getOpenedPositions(){
        return createElmListFromCss("section[data-automation-id='jobResults'] ul[role='list'] li[class*='1q'] a");
    }

    public void applyJob(){
        jobDescriptionSection.clickApplyJob();
    }

    public String getApplicationPopupTitle(){
        return startYourApplicationPopupWindow.getPopupTitle();
    }

}
