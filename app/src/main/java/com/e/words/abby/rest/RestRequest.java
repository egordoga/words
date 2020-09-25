package com.e.words.abby.rest;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.sound.SoundTrack;
import com.e.words.temp.TestTTS;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestRequest {
    private static final String URL_BASE = "https://developers.lingvolive.com/api/v1/";
  //  private static OkHttpClient client = new OkHttpClient.Builder().build();
    public static byte[] arrBytes;

    public static URL getURLSound(String fileName) {
        Uri uri  = Uri.parse(URL_BASE + "sound").buildUpon()
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
        Uri uri  = Uri.parse(URL_BASE + "Article").buildUpon()
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

    private static String getWordResult(/*OkHttpClient client, */URL url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();



        System.out.println("Before WORD RES");



        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer  " + AuthToken.getInstance())
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        int code = response.code();

        System.out.println("WORD CODE" + code);


        return code == 200 ? response.body().string() : "" + code;
    }

    private static String getSoundResult(/*OkHttpClient client, */URL url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();


        System.out.println("before SOUND RES");


        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer  " + AuthToken.getInstance())
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        int code = response.code();


        System.out.println("sound code " + code);


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

//    public void playSound() {
//        new SoundTask().execute(getURLSound("issue.wav"));
//    }
//
//    public /*static*/ byte[] getSoundBytes(String fileName) {
//        try {
//            return new SoundTask().execute(getURLSound(fileName)).get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String str) {



//            SoundTrack.playTrack(bytes);
//           System.out.println("SIZE   " + str.length());
//            System.out.println(str);
//            str = str.substring(1, str.length() - 1);
//            System.out.println(Arrays.toString(Base64.getDecoder().decode(str)));
         //   new TestTTS().saveSoundFile();
        }
    }


}
