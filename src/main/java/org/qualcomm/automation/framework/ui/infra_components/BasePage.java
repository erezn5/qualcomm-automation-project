package org.qualcomm.automation.framework.ui.infra_components;


import org.openqa.selenium.support.PageFactory;
import org.qualcomm.automation.framework.conf.EnvConf;
import org.qualcomm.automation.framework.ui.selenium.DriverWrapper;

public class BasePage extends PageElement{
    private final String url;
    protected final static String URL_ADDRESS= EnvConf.getProperty("base.url");

    public BasePage(DriverWrapper driver, String path){
        super(driver);
        this.url = URL_ADDRESS + "/" + path;
        PageFactory.initElements(driver, this);
    }

    private void navigate(){
        driver.get(url);
    }

    public void navigateAndVerify(){
        if(url.equals(driver.getCurrentUrl())){
            refresh();
        }else{
            navigate();
        }
    }

    private void refresh() {
        driver.navigate().refresh();
    }

}
