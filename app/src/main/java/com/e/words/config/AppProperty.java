package com.e.words.config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppProperty {

    private static AppProperty instance;
    public int countRepeat;
    public float speedTts;
    public float pitch;
    public boolean isPropsChange = false;
    public int wordPause;
    public int translPause;

    private final SharedPreferences prefs;

    private AppProperty(Context ctx) {
        prefs = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        countRepeat = prefs.getInt("countRepeat", 2);
        wordPause = prefs.getInt("wordPause", 700);
        translPause = prefs.getInt("translPause", 500);
        speedTts = prefs.getFloat("speedTts", 1f);
        pitch = prefs.getFloat("pitch", 1f);
    }

    public static AppProperty getInstance(Context ctx) {
        if (instance == null) {
            synchronized (AppProperty.class) {
                if (instance == null) {
                    instance = new AppProperty(ctx);
                }
            }
        }
        return instance;
    }

    public void saveProps() {
        if (isPropsChange) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("countRepeat", countRepeat);
            editor.putInt("wordPause", wordPause);
            editor.putInt("translPause", translPause);
            editor.putFloat("speedTts", speedTts);
            editor.putFloat("pitch", pitch);
            editor.apply();
        }
    }
}
