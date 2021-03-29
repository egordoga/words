package com.e.words.abby.rest;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestRequest {
    private static final String URL_BASE = "https://developers.lingvolive.com/api/v1/";

    public static URL getURLSound(String fileName) {
        Uri uri = Uri.parse(URL_BASE + "sound").buildUpon()
                .appendQueryParameter("dictionaryName", "LingvoUniversal (En-Ru)")
                .appendQueryParameter("fileName", fileName)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static URL getURLWord(String word, String langFrom, String langTo) {
        Uri uri = Uri.parse(URL_BASE + "Article").buildUpon()
                .appendQueryParameter("heading", word)
                .appendQueryParameter("dict", "LingvoUniversal (En-Ru)")
                .appendQueryParameter("srcLang", langFrom)
                .appendQueryParameter("dstLang", langTo)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getWordResult(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer  " + AuthToken.getInstance())
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        int code = response.code();
        return code == 200 ? response.body().string() : "" + code;
    }

    private static String getSoundResult(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer  " + AuthToken.getInstance())
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        int code = response.code();
        return code == 200 ? response.body().string() : String.valueOf(code);
    }

    public static String getWordJson(String word, String langFrom, String langTo) {
        try {
            return new WordTask().execute(getURLWord(word, langFrom, langTo)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSoundString(String filename) {
        try {
            return new SoundTask().execute(getURLSound(filename)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class WordTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            try {
                return getWordResult(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class SoundTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            try {
                return getSoundResult(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
