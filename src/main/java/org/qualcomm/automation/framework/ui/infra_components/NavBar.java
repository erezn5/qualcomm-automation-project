package org.qualcomm.automation.framework.ui.infra_components;

import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

import java.util.Map;

public class NavBar<ITEM extends Enum<ITEM>> extends PageElement{

    private final Map<Enum<ITEM>, By> navBarItemsMap;

    public NavBar(DriverWrapper driver, Map<Enum<ITEM>, By> navBarItemsMap) {
        super(driver);
        this.navBarItemsMap = navBarItemsMap;
    }

    public void clickItem(Enum item){
        clickButton(getItemByEnum(item));
        waitForLoaderSpinnerBecomeInvisible();
    }

    public boolean isItemExist(Enum item){
        return isVisible(getItemByEnum(item), Duration.TEN_SECONDS);
    }

    private By getItemByEnum(Enum item) {
        return navBarItemsMap.get(item);
    }
}
