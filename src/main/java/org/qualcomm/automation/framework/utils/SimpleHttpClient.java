package org.qualcomm.automation.framework.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;

public class SimpleHttpClient {
    private String BASE_URL = null;
    private final OkHttpClient httpClient = new OkHttpClient();

    public SimpleHttpClient(String baseUrl){
        BASE_URL = baseUrl;
    }
    Headers.Builder builder = new Headers.Builder();
    public String sendGetRequest(String path, HashMap<String, String> headersMap) throws IOException {
        Headers headers = addHeaders(headersMap);
        Request request = new Request.Builder()
                .url(String.format("%s/%s", BASE_URL, path)).headers(headers).build();
        return getResponse(request);
    }

    private String getResponse(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            assert response.body() != null;
            final String responseBody = response.body().string();
            if (!(response.code() >= 200 && response.code() < 300)) {
                System.out.println("Could not complete request with returned code: " + response.code());
            }

            System.out.printf("Response data is: =[%s]%n", responseBody);
            return responseBody;
        }
    }

    private Headers addHeaders(HashMap<String, String> headersMap){
        headersMap.forEach((key, value) -> builder.add(key, value));
        return builder.build();
    }

    public String sendPostRequest(String path, String body, HashMap<String, String> headersMap) throws IOException {
        Headers headers = addHeaders(headersMap);
        RequestBody requestBody = RequestBody.create(
                body, MediaType.parse("application/json; charset=utf-8"));
        String requestUrl = String.format("%s%s", BASE_URL, path);
        Request request = new Request.Builder().url(requestUrl).headers(headers).post(requestBody).build();
        return getResponse(request);
    }
}
