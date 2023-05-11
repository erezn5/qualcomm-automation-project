package org.qualcomm.automation.framework.utils;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonHandler {
    private static final Gson GSON = new GsonBuilder().create();
    private JsonHandler() { }

    public static String jsonFileReader(String jsonFileName){
        JsonObject jsonObject= null;
        String filePath = String.format("src/test/test_data/%s", jsonFileName);
        try {
            String content = readFileAsString(filePath);
            jsonObject = JsonParser.parseString(content).getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }

    private static String readFileAsString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }

        return content.toString();
    }

    public static <T> T returnJsonFromString(String jsonString, Class<T> type) {
        JsonElement jsonElement = JsonParser.parseString(jsonString);

        if (type == JsonObject.class) {
            return GSON.fromJson(jsonElement, type);
        } else if (type == JsonArray.class) {
            return GSON.fromJson(jsonElement, type);
        } else {
            throw new IllegalArgumentException("Invalid type. Only JsonObject and JsonArray are supported.");
        }
    }

    public static JsonObject asListObject(String json, String path) {
        String[] paths = path.split("\\.");
        JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);
        for (int i = 0; i < paths.length - 1; i++) {
            jsonObject = jsonObject.getAsJsonObject(paths[i]);
        }
        return jsonObject.getAsJsonObject(paths[paths.length - 1]);
    }
    public static JsonArray asList(String json, String path) {
        String[] paths = path.split("\\.");
        JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);
        for (int i = 0; i < paths.length - 1; i++) {
            jsonObject = jsonObject.getAsJsonObject(paths[i]);
        }
        return jsonObject.getAsJsonArray(paths[paths.length - 1]);
    }

    public static <T> List<T> getJsonAsClassObjectList(String toParse , Class<T[]> clazz){
        T[] list = GSON.fromJson(toParse, clazz);
        return Arrays.asList(list);
    }

    public static <T> T getJsonAsClassObject(String toParse, Class<T> clazz){
        return GSON.fromJson(toParse, clazz);
    }
}
