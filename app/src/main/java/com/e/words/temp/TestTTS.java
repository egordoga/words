package com.e.words.temp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.speech.tts.TextToSpeech;
//
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

import androidx.annotation.RequiresApi;

import com.e.words.abby.depricated.dto.StrWithLocaleDto;
import com.e.words.abby.rest.RestRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TestTTS {

    public void playTrack(List<StrWithLocaleDto> trackString, TextToSpeech textToSpeech) {
        HashMap<String, String> myHash = new HashMap<String, String>();

        myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "done");

        for (int i = 0; i < trackString.size(); i++) {

            if (i == 0) { // Use for the first splited text to flush on audio stream

                textToSpeech.setLanguage(trackString.get(i).locale);
                textToSpeech.speak(trackString.get(i).str.trim(),TextToSpeech.QUEUE_FLUSH, /*myHash*/  null,null);

            } else { // add the new test on previous then play the TTS

                textToSpeech.setLanguage(trackString.get(i).locale);
                textToSpeech.speak(trackString.get(i).str.trim(), TextToSpeech.QUEUE_ADD, /*myHash*/null,null);
            }

            textToSpeech.playSilentUtterance(750, TextToSpeech.QUEUE_ADD, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playSoundFile(String fileName, Context ctx) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANT)
          //      .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        SoundPool sSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        File dir = ctx.getFilesDir();
        File file = new File(dir, fileName);
        int id = sSoundPool.load(file.getPath(), 1);
        //int id1 = sSoundPool.load(ctx, R.raw.sound11, 1);

        sSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sSoundPool.play(id, 1, 1, 0, 1, 1);
            }
        });

//        FileInputStream fin = null;
//        try {
//            fin = ctx.openFileInput("testFile");
//            byte[] bytes = new byte[fin.available()];
//            fin.read(bytes);
//            String text = new String (bytes);
//         //   System.out.println(text);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        MediaPlayer mp=new MediaPlayer();
//        try{
////            FileInputStream fis = new FileInputStream(new File("issue.wav"));
//            FileDescriptor fd = fin.getFD();
//            mp.setDataSource(fd);//Write your location here
//         //   mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
////            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////                @Override
////                public void onPrepared(MediaPlayer mp) {
////                    mp.start();
////                }
////            });
//
//                mp.prepare();
//            mp.start();
//            System.out.println("OOO");
//
//        }catch(Exception e){e.printStackTrace();}

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveSoundFile(String fileName, Context ctx) {
        String str = RestRequest.getSoundString(fileName);
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        if (str.endsWith("\"")) {
            str = str.substring(0, str.length() - 1);
        }
    //    str = Arrays.toString(Base64.getDecoder().decode(str));
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(fileName, MODE_PRIVATE);
            fos.write(Base64.getDecoder().decode(str));
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testSaveFile(Context ctx, String str) {
        //String str = "FFDDFFDFDFDFDFFFD";
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput("testFile", MODE_PRIVATE);
            fos.write(/*str.getBytes() */   Base64.getDecoder().decode(str));
            fos.flush();
            fos.close();
            System.out.println("FILE SAVED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTestFile(Context ctx) {
        FileInputStream fin = null;
        try {
            fin = ctx.openFileInput("issue.wav");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testAddFile(Context ctx) {
//        try {
//            AudioInputStream clip1 = AudioSystem.getAudioInputStream(new File(wavFile1));
//            AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(wavFile2));
//
//            AudioInputStream appendedFiles =
//                    new AudioInputStream(new SequenceInputStream(clip1, clip2),
//                            clip1.getFormat(),
//                            clip1.getFrameLength() + clip2.getFrameLength());
//
//            AudioSystem.write(appendedFiles,
//                    AudioFileFormat.Type.WAVE,
//                    new File("D:\\wavAppended.wav"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testFullTrack(String soundFile, List<StrWithLocaleDto> strs, Context ctx, TextToSpeech tts) {
       // playSoundFile(soundFile, ctx);
        playTrack(strs, tts);
    }
}
