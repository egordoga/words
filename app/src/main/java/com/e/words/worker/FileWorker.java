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

public class FileWorker /*implements TextToSpeech.OnInitListener*/ {
 //   TextToSpeech tts;
    boolean isInit;
    private List<StrLocale> listStr = new ArrayList<>();
    private List<StrLocale> ruListStr = new ArrayList<>();
    private List<StrLocale> enListStr = new ArrayList<>();
    private Context ctx;

    public FileWorker(Context ctx) {
        this.ctx = ctx;
    }

        public List<String> ttsToFiles(Context ctx, WordObj wordObj, TextToSpeech tts) {
        List<String> fileNames = new ArrayList<>();
//        tts = new TextToSpeech(ctx, status -> {
//            if (status == TextToSpeech.SUCCESS) {
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
//                tts.stop();
//                tts.shutdown();
//            } else {
//                Toast.makeText(ctx, "Init failed", Toast.LENGTH_SHORT).show();
//            }
//        });


        return fileNames;
    }

//    public List<String> ttsToFiles(Context ctx, WordObj wordObj/*, TextToSpeech ttss*/) {
//        List<String> fileNames = new ArrayList<>();
////        tts = new TextToSpeech(ctx, status -> {
////            if (status == TextToSpeech.SUCCESS) {
//
//
////        long start = new Date().getTime();
////           tts = new TextToSpeech(ctx, this);
////
////             while (true) {
////            long end = new Date().getTime();
////            if (start + 2000 < end) {
////                System.out.println("BBBBBB");
////                return null;
////            }
////          System.out.println("IS_INIT " + isInit);
////
////
////           if (isInit) {
//        System.out.println("VCVCV");
//        int count = 1;
//                Locale enLoc = new Locale("en");
//                Locale ruLoc = new Locale("ru");
////                File dir = ctx.getFilesDir();
////                File file;
//        String fileName;
//        for (TranslationAndExample tae : wordObj.translations) {
//            //   tts.setLanguage(ruLoc);
//            fileName = wordObj.word.word + count++ + ".wav";
//            fileNames.add(fileName);
//            ruListStr.add(new StrLocale(tae.translation.translation, fileName, ruLoc));
//            //    file = new File(dir, fileName);
//            //    int i = tts.synthesizeToFile(tae.translation.translation, null, file, null);
//         //   synthesizeFile(tae.translation.translation, fileName, ctx, true);
//            //   tts.setLanguage(enLoc);
//            fileNames.add("Silent");
//            for (Example ex : tae.examples) {
//                String[] arr = ex.example.split("—");
//                fileName = wordObj.word.word + count++ + ".wav";
//                fileNames.add(fileName);
//                enListStr.add(new StrLocale(arr[0], fileName, enLoc));
//                //   file = new File(dir, fileName);
//                //  int j = tts.synthesizeToFile(arr[0], null, file, null);
//                synthesizeFile(arr[0], fileName, ctx, false);
//                fileNames.add("Silent");
//            }
//        }
////                tts.stop();
////                tts.shutdown();
////        if (isInit) {
////            aaa(ctx, 0);
////        }
//        testTts();
//        return fileNames;
//    }
    //     }
//            } else {
//                Toast.makeText(ctx, "Init failed", Toast.LENGTH_SHORT).show();
//            }
//        });


    //      return fileNames;
//    }

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

    public void saveSoundFile(StrLocale str, TextToSpeech tts) {

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

//    @Override
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//            isInit = true;
//        }
//    }
//
//
//    private void synthesizeFile(String str, String fileName, Context ctx, boolean isRU) {
//        tts = new TextToSpeech(ctx, this);
//        if (isRU) {
//            tts.setLanguage(new Locale("ru"));
//        } else {
//            tts.setLanguage(new Locale("en"));
//        }
//        File dir = ctx.getFilesDir();
//        File file = new File(dir, fileName);
//        //   if (isInit) {
//        tts.synthesizeToFile(str, null, file, null);
//
//        //   }
//    }
//
//
//    private void aaa(Context ctx, int i) {
////        tts = new TextToSpeech(ctx, status -> {
////            if (status == TextToSpeech.SUCCESS) {
//        //  for (StrLocale strLocale : list) {
//        //     int i = 0;
//        if (i >= listStr.size()) {
//            return;
//        }
//        synthesizeFile(listStr.get(i), ctx, tts);
//        i++;
//        int finalI = i;
//        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
//            @Override
//            public void onStart(String utteranceId) {
//
//            }
//
//            @Override
//            public void onDone(String utteranceId) {
//                aaa(ctx, finalI);
//                System.out.println("DONE");
//            }
//
//            @Override
//            public void onError(String utteranceId) {
//
//            }
//        });
//        //  }
////            }
////        });
//    }
//
//    private void synthesizeFile(StrLocale str, Context ctx, TextToSpeech tts) {
//        tts.setLanguage(str.locale);
//        File dir = ctx.getFilesDir();
//        File file = new File(dir, str.fileName);
//        //   if (isInit) {
//        tts.synthesizeToFile(str.str, null, file, null);
//
//        //   }
//    }
//
//    public void testTts() {
//
//        Locale enLoc = new Locale("en");
//        Locale ruLoc = new Locale("ru");
//        tts = new TextToSpeech(ctx, status -> {
//            if (status == TextToSpeech.SUCCESS) {
//                tts.setLanguage(ruLoc);
//                File dir = ctx.getFilesDir();
//                for (StrLocale strLocale : ruListStr) {
//                    File file = new File(dir, strLocale.fileName);
//                    tts.synthesizeToFile(strLocale.str, null, file, null);
//                }
//                tts.setLanguage(enLoc);
//                for (StrLocale strLocale : enListStr) {
//                    File file = new File(dir, strLocale.fileName);
//                    tts.synthesizeToFile(strLocale.str, null, file, null);
//                }
////                File file = new File(dir, "a.wav");
////                //   if (isInit) {
////                tts.synthesizeToFile(, null, file, null);
////                file = new File(dir, "b.wav");
////                tts.synthesizeToFile("эта штука сможет ходить", null, file, null);
////                file = new File(dir, "aa.wav");
////                tts.synthesizeToFile("You look very pretty", null, file, null);
////                tts.setLanguage(ruLoc);
////                file = new File(dir, "c.wav");
////                tts.synthesizeToFile("только бы получить товар", null, file, null);
////                tts.setLanguage(enLoc);
////                file = new File(dir, "bb.wav");
////                tts.synthesizeToFile("Your new hairdo is not bad", null, file, null);
////                file = new File(dir, "cc.wav");
////                tts.synthesizeToFile("Thank you for picking me up", null, file, null);
//            }
//        });
////        tts.stop();
////        tts.shutdown();
//    }
}
