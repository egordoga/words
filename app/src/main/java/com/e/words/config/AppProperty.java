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

    //  private Properties props;
    private SharedPreferences prefs;

    private AppProperty(Context ctx) {
        /*props = new Properties();
        try {
            props.load(ctx.getAssets().open("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        countRepeat = props.getProperty("countRepeat");
        speedTts = props.getProperty("speechRate");
        pitch = props.getProperty("pitch");*/

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
//            AssetManager assetManager = ctx.getAssets();
//            OutputStream os = assetManager.
//            props.setProperty("countRepeat", countRepeat);
//            props.setProperty("speechRate", speedTts);
//            props.setProperty("pitch", pitch);
//            props.store();
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
