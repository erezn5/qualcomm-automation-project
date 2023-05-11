package org.qualcomm.automation.framework.ui.infra_components;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

import java.util.List;

public class DropDownMenu extends PageElement{
    @FindBy(css = "div[class='ReactVirtualized__Grid__innerScrollContainer'] div[role='row']")
    private List<WebElement> itemLstElm;
    private final WebElement dropDownMenuBthElm;

    public DropDownMenu(DriverWrapper driver, WebElement dropDownMenuBthElm) {
        super(driver);
        this.dropDownMenuBthElm = dropDownMenuBthElm;
    }

    private void openDropDownMenu(){
        scrollElmIntoView(dropDownMenuBthElm);
        clickElm(dropDownMenuBthElm);
    }

    public void selectItemFromDropDownMenuWithName(String inputTxt){
        openDropDownMenu();
        for(WebElement itemInList: itemLstElm){
            if(itemInList.getText().contains(inputTxt)){
                clickElm(itemInList);
            }
        }
    }
}
