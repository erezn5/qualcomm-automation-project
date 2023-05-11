package com.qualcomm.automation.tests.api_tests;

import com.google.gson.JsonObject;
import org.qualcomm.automation.framework.utils.JsonHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RestAPIRestAPITest extends RestAPITestHelper {

    @Test(priority = 10, dataProvider = "requestStudentsDataProvider")
    public void getStudentsDetailsAndVerifyId(String jsonFileName, String studentsDetailsPath) throws IOException {
        String jsonBodyPayload = JsonHandler.jsonFileReader(jsonFileName);
        String responseAsString = httpClient.sendPostRequest(studentsDetailsPath, jsonBodyPayload, headersMap);

        ID = getFirstIdValueFromJsonArray(responseAsString);
        Assert.assertTrue(ID != 0);
    }

    @Test(priority = 20, dataProvider = "techDetailsDataProvider", dependsOnMethods = "getStudentsDetailsAndVerifyId")
    public void getTechnicalSkillsAndPrintResponse(String techSkillsJsonFileName, String techSkillsPath) throws IOException {
        String jsonBodyPayload = JsonHandler.jsonFileReader(techSkillsJsonFileName);
        jsonBodyPayload = replacePropertyInJson(jsonBodyPayload);

        String responseAsString = httpClient.sendPostRequest(techSkillsPath, jsonBodyPayload, headersMap);
        Assert.assertFalse(responseAsString.isEmpty());
        System.out.println(responseAsString);
    }

    @Test(priority = 30, dataProvider = "addressesDataProvider", dependsOnMethods = "getStudentsDetailsAndVerifyId")
    public void getAddressesAndPrintResponse(String addressesJsonFileName, String addressesPath){
        String jsonBodyPayload = JsonHandler.jsonFileReader(addressesJsonFileName);

        JsonObject jsonObject = gson.fromJson(jsonBodyPayload, JsonObject.class);
        jsonObject.addProperty("stId", String.valueOf(ID));

        String modifiedJsonString = gson.toJson(jsonObject);
        Assert.assertFalse(modifiedJsonString.isEmpty());
        System.out.println(modifiedJsonString);
    }


    @Test(priority = 40, dataProvider = "finalStudentsDetailsDataProvider", dependsOnMethods = "getStudentsDetailsAndVerifyId")
    public void getStudentsDetailsAndPrint(String getStudentDetailsPath) throws IOException {
        String response = httpClient.sendGetRequest(getStudentDetailsPath, headersMap);

        Assert.assertFalse(response.isEmpty());
        System.out.println(response);
    }

}
