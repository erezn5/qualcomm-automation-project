package com.qualcomm.automation.tests.api_tests;

import com.google.gson.*;
import com.qualcomm.automation.tests.BaseTest;
import org.qualcomm.automation.framework.utils.JsonHandler;
import org.qualcomm.automation.framework.utils.SimpleHttpClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.HashMap;

public class RestAPITestHelper extends BaseTest {
    protected SimpleHttpClient httpClient;
    protected final HashMap<String, String> headersMap = new HashMap<>();
    protected Gson gson;
    protected int ID = 0;

    @BeforeClass(alwaysRun = true)
        public void setupHelper(){
        headersMap.put("Content-Type", "application/json");
        httpClient = new SimpleHttpClient(API_BASE_URL);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @DataProvider(name = "requestStudentsDataProvider")
    public Object[][] requestStudentsDataProvider() {
        return new Object[][]{
                {"requestJson.json", "/studentsDetails"}
        };
    }

    @DataProvider(name = "techDetailsDataProvider")
    public Object[][] techDetailsDataProvider() {
        return new Object[][]{
                {"TechDetails.json", "/technicalskills"}
        };
    }

    @DataProvider(name = "addressesDataProvider")
    public Object[][] addressesDataProvider() {
        return new Object[][]{
                {"addresses.json", "/addresses"}
        };
    }

    @DataProvider(name = "finalStudentsDetailsDataProvider")
    public Object[][] finalStudentsDetailsDataProvider() {
        return new Object[][]{
                {"/FinalStudentDetails/" + ID}
        };
    }

    int getFirstIdValueFromJsonArray(String responseBody) {
        JsonArray jsonArray = JsonHandler.returnJsonFromString(responseBody, JsonArray.class);

        if (jsonArray.size() > 0) {
            JsonElement jsonElement = jsonArray.get(0);

            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject().get("id").getAsInt();
            } else {
                System.out.println("Invalid JSON object in the JSON array");
            }
        } else {
            System.out.println("Empty JSON array");
        }
        return 0;
    }

    String replacePropertyInJson(String jsonString) {
        JsonObject jsonObject = JsonHandler.returnJsonFromString(jsonString, JsonObject.class);

        jsonObject.addProperty("id", ID);
        jsonObject.addProperty("st_id", String.valueOf(ID));

        return gson.toJson(jsonObject);
    }


}
