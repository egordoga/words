package com.e.words.worker;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import com.e.words.abby.abbyEntity.dto.dto_new.StrLocale;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.TranslationAndExample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileWorker {
//    private final List<StrLocale> listStr = new ArrayList<>();
//    private final List<StrLocale> ruListStr = new ArrayList<>();
//    private final List<StrLocale> enListStr = new ArrayList<>();


        public List<String> ttsToFiles(Context ctx, WordObj wordObj, TextToSpeech tts) {
        List<String> fileNames = new ArrayList<>();
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
        return fileNames;
    }

    public String saveSoundFile(Context ctx, byte[] sounds, String name) {
        File dir = ctx.getFilesDir();
        File file = new File(dir, name + ".wav");
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(sounds);
            return "OK";
        } catch (IOException e) {
            return "Звуковой файл не записан.\nУдалите слово и попробуйте сохранить его позже";
        }
    }

    public void deleteFile(String name, Context ctx) {
        File dir = ctx.getFilesDir();
        File file = new File(dir, name);
        if (file.exists()) {
            file.delete();
        }
    }

    public void deleteTranslateFiles(String[] names, Context ctx) {
        File dir = ctx.getFilesDir();
        // 1-й файл - звук слова. Пропускаем
        for (int i = 1; i < names.length; i++) {
            if ("Silent".equals(names[i])) {
                continue;
            }
            File file = new File(dir, names[i]);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void deleteFiles(String name, boolean isTranslateOnly, Context ctx) {
        File dir = ctx.getFilesDir();
        String start = "^" + name;
        String end = "\\.wav$";
        String middle = "\\d*";;
        if (isTranslateOnly) {
            middle = "\\d+";
        }
        String reg = start + middle + end;
        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            if (fileName.matches(reg) || (!isTranslateOnly && fileName.equals(name + "US.wav"))) {
                file.delete();
            }
        }
    }

}
