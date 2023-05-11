package org.qualcomm.automation.framework.ui.pages.careers.detailed_open_positions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qualcomm.automation.framework.ui.infra_components.DropDownMenu;
import org.qualcomm.automation.framework.ui.infra_components.PageElement;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

public class TopDropDownMenuToolBarSection extends PageElement {
    @FindBy(css = "button[data-automation-id='jobFamilyGroup']")
    private WebElement jobCategoryDropDownMenuBtnElm;
    @FindBy(css = "input[data-automation-id='keywordSearchInput']")
    private WebElement searchJobTxtElm;
    @FindBy(css = "button[data-automation-id*='key']")
    private WebElement searchBtnElm;
    @FindBy(css = "button[data-automation-id='viewAllJobsButton']")
    private WebElement viewAllJobsBtnElm;
//    @FindBy(css = "button[data-automation-id='employmentType']")
//    private WebElement employmentTypeDropDownMenuBtnElm;
//    @FindBy(css = "button[data-automation-id='distanceLocation']")
//    private WebElement distanceLocationDropDownMenuBtnElm;
//    @FindBy(css = "button[data-automation-id='more']")
//    private WebElement moreDropDownMenuBtnElm;
    private final DropDownMenu jobCategoryDropDownMenu;
//    private DropDownMenu employmentTypeDropDownMenu;
//    private DropDownMenu distanceLocationDropDownMenu;
//    private DropDownMenu moreDropDownMenu;
    public TopDropDownMenuToolBarSection(DriverWrapper driver) {
        super(driver);
        jobCategoryDropDownMenu = new DropDownMenu(driver, jobCategoryDropDownMenuBtnElm);
//        employmentTypeDropDownMenu = new DropDownMenu(driver, employmentTypeDropDownMenuBtnElm);
//        distanceLocationDropDownMenu = new DropDownMenu(driver, distanceLocationDropDownMenuBtnElm);
//        moreDropDownMenu = new DropDownMenu(driver, moreDropDownMenuBtnElm);
    }

    public void selectItemFromJobCategoryDropDownMenu(String itemToCheckTxt){
        jobCategoryDropDownMenu.selectItemFromDropDownMenuWithName(itemToCheckTxt);
        clickElm(viewAllJobsBtnElm);
    }
    public void filterSearchJobTxt(String jobTypeTxt){
        scrollTopOFThePage();
        setText(searchJobTxtElm, jobTypeTxt);
        clickElm(searchBtnElm);
    }
}
