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

    private final static int COUNT_REPEAT_DEF_VALUE = 2;
    private final static int WORD_PAUSE_DEF_VALUE = 700;
    private final static int TRANSL_PAUSE_DEF_VALUE = 500;
    private final static float SPEED_TTS_DEF_VALUE = 1f;
    private final static float PITCH_DEF_VALUE = 1f;

    private final SharedPreferences prefs;

    private AppProperty(Context ctx) {
        prefs = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        countRepeat = prefs.getInt("countRepeat", COUNT_REPEAT_DEF_VALUE);
        wordPause = prefs.getInt("wordPause", WORD_PAUSE_DEF_VALUE);
        translPause = prefs.getInt("translPause", TRANSL_PAUSE_DEF_VALUE);
        speedTts = prefs.getFloat("speedTts", SPEED_TTS_DEF_VALUE);
        pitch = prefs.getFloat("pitch", PITCH_DEF_VALUE);
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
