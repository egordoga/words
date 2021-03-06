package com.e.words.abby.rest;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthToken {

    private static final String URL = "https://developers.lingvolive.com/api/v1.1/authenticate";
    private static final String KEY = "Basic NjAwNjdjOGYtNTg3Ni00ZWQ2LTk0N2MtNDE1NmM5YmY5ZDRjOmRiZWUzYTMxMmFlNDQyYmM5NTM0ZDI0OWFjM2NkZTE4";
    private static String instance; // = "ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmxlSEFpT2pFMU9UYzROakEyTURnc0lrMXZaR1ZzSWpwN0lrTm9ZWEpoWTNSbGNuTlFaWEpFWVhraU9qVXdNREF3TENKVmMyVnlTV1FpT2pJNE1UQXNJbFZ1YVhGMVpVbGtJam9pTmpBd05qZGpPR1l0TlRnM05pMDBaV1EyTFRrME4yTXROREUxTm1NNVltWTVaRFJqSW4xOS5FenhzZk4xc0lhLVZhMGJEMVhHTVVzODYzNVQ3cXQ0ZUhZY1E3Yzh4c1ZJ";

    private static String getToken() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("Authorization", KEY)
                .post(body)
                .build();
        String token = null;
        try {
            Response response = client.newCall(request).execute();
            token = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String getInstance() {
        if (instance == null) {
            synchronized (AuthToken.class) {
                if (instance == null) {
                    instance = getToken();
                }
            }
        }
        return instance;
    }
}
