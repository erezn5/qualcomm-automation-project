package org.qualcomm.automation.framework.ui.pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qualcomm.automation.framework.ui.infra_components.BasePage;
import org.qualcomm.automation.framework.ui.infra_components.PageElement;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

import java.util.List;

public class QualCommHomePage extends BasePage {
    @FindBy(css = "div[class='sc-ewnqHT jpDiiu'] div:nth-child(4) div a")
    private List<WebElement> companyElmList;
    private final TopNavBarNavigation topNavBarNavigation;
    private final PopupWindow popupWindow;
    public QualCommHomePage(DriverWrapper driver) {
        super(driver, "/home");
        topNavBarNavigation = new TopNavBarNavigation(driver);
        popupWindow = new PopupWindow(driver);
    }

    private void clickOnTopBarOption(QualCommHomeTopNavigationItems item){
        topNavBarNavigation.clickItem(item);
    }

    public void clickOnOurCompanyOption(String option){
        clickOnTopBarOption(QualCommHomeTopNavigationItems.COMPANY);
        for(WebElement item: companyElmList){
            if(item.getText().equals(option)){
                clickElm(item);
            }
        }

        popupWindow.clickYesOrNo(true);

    }

    private static class PopupWindow extends PageElement {
        @FindBy(css = "div[role='dialog'] a[aria-label*='No']")
        private WebElement noThankYouBtnElm;
        @FindBy(css = "div[role='dialog'] a[aria-label*='Yes']")
        private WebElement yesThankYouBtnElm;
        public PopupWindow(DriverWrapper driver) {
            super(driver);
        }

        public void clickYesOrNo(boolean flag){
            boolean isVisible = waitForElmBecomeVisible(By.cssSelector("div[role='dialog'] a[aria-label*='No']"));
            if(isVisible) {
                if (flag) {
                    clickElm(noThankYouBtnElm);
                } else {
                    clickElm(yesThankYouBtnElm);
                }
            }
        }
    }
}
