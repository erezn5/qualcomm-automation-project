package com.qualcomm.automation.tests;

import org.qualcomm.automation.framework.conf.EnvConf;


public class BaseTest {
    protected String API_BASE_URL = EnvConf.getProperty("base.api.url");
    protected String UI_BASE_URL = EnvConf.getProperty("base.ui.url");
}
