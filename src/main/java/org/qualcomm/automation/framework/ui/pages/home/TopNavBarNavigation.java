package org.qualcomm.automation.framework.ui.pages.home;

import org.openqa.selenium.By;
import org.qualcomm.automation.framework.ui.infra_components.NavBar;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

import java.util.HashMap;
import java.util.Map;

public class TopNavBarNavigation extends NavBar<QualCommHomeTopNavigationItems> {
    private static final By productsItemBy = By.id("div[class='sc-ewnqHT fIlswb'] button[id]:nth-child(1)");
    private static final By supportItemBy = By.id("div[class='sc-ewnqHT fIlswb'] button[id]:nth-child(2)");
    private static final By companyItemBy = By.cssSelector("div[class='sc-ewnqHT fIlswb'] button[id]:nth-child(3)");

    public TopNavBarNavigation(DriverWrapper driver) {
        super(driver, createSideCompanyItemMap());
    }

    private static Map<Enum<QualCommHomeTopNavigationItems>, By> createSideCompanyItemMap() {
        Map<Enum<QualCommHomeTopNavigationItems>, By> itemsMap = new HashMap<>();
        itemsMap.put(QualCommHomeTopNavigationItems.PRODUCTS, productsItemBy);
        itemsMap.put(QualCommHomeTopNavigationItems.SUPPORT, supportItemBy);
        itemsMap.put(QualCommHomeTopNavigationItems.COMPANY, companyItemBy);


        return itemsMap;
    }
}
