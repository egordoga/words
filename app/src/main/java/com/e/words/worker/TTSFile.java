package com.e.words.worker;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.TranslationAndExample;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TTSFile implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    private boolean isInit;
    private boolean isSave;


    public List<String> ttsToFiles(Context ctx, WordObj wordObj/*, TextToSpeech ttss*/) {
        List<String> fileNames = new ArrayList<>();
//        tts = new TextToSpeech(ctx, status -> {
//            if (status == TextToSpeech.SUCCESS) {


        long start = new Date().getTime();
        tts = new TextToSpeech(ctx, this::onInit);

        while (true) {
            long end = new Date().getTime();
            if (start + 2000 < end) {
                System.out.println("BBBBBB");
                return null;
            }
            //  System.out.println("IS_INIT " + isInit);


            if (isInit) {
                System.out.println("VCVCV");
                int count = 1;
                Locale enLoc = new Locale("en");
                Locale ruLoc = new Locale("ru");
                File dir = ctx.getFilesDir();
                File file;
                String fileName;
                for (TranslationAndExample tae : wordObj.translations) {
                    tts.setLanguage(ruLoc);
                    fileName = wordObj.word.word + count++ + ".wav";
                    fileNames.add(fileName);
                    file = new File(dir, fileName);
                    tts.synthesizeToFile(tae.translation.translation, null, file, null);
                    tts.setLanguage(enLoc);
                    fileNames.add("Silent");
                    for (Example ex : tae.examples) {
                        String[] arr = ex.example.split("—");
                        fileName = wordObj.word.word + count++ + ".wav";
                        fileNames.add(fileName);
                        file = new File(dir, fileName);
                        tts.synthesizeToFile(arr[0], null, file, null);
                        fileNames.add("Silent");
                    }
                }
                tts.stop();
                tts.shutdown();
                return fileNames;
            }
        }
//            } else {
//                Toast.makeText(ctx, "Init failed", Toast.LENGTH_SHORT).show();
//            }
//        });


        //      return fileNames;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isInit = true;
        }
    }

    private void synthesizeFile(String str, String fileName, Context ctx) {
        tts = new TextToSpeech(ctx, this);
        File dir = ctx.getFilesDir();
        File file = new File(dir, fileName);
         //   if (isInit) {
                tts.synthesizeToFile(str, null, file, null);
         //   }
    }
}
