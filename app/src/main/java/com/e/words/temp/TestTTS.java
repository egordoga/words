package com.e.words.temp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.rtp.AudioStream;
import android.os.Build;
import android.speech.tts.TextToSpeech;
//
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

import androidx.annotation.RequiresApi;

import com.e.words.R;
import com.e.words.abby.JsonData;
import com.e.words.abby.abbyEntity.dto.StrWithLocaleDto;
import com.e.words.abby.abbyEntity.dto.WordDto;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.util.Arrays;
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
    public void playSound(/*Context ctx*/) {
//        AudioAttributes attributes = new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_GAME)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build();
//        SoundPool sSoundPool = new SoundPool.Builder()
//                .setAudioAttributes(attributes)
//                .build();
//        int id = sSoundPool.load("issue.wav", 1);
//        sSoundPool.play(id, 1, 1, 0, 0, 1);
//        sSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                ctx.loa
//            }
//        });

        MediaPlayer mp=new MediaPlayer();
        try{
            FileInputStream fis = new FileInputStream(new File("issue.wav"));
            FileDescriptor fd = fis.getFD();
            mp.setDataSource(fd);//Write your location here
            mp.prepare();
            mp.start();
            System.out.println("OOO");

        }catch(Exception e){e.printStackTrace();}

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveSoundFile(String fileName, String str, Context ctx) {
      //  File file = new File(fileName);
       // String str = new JsonData().LOOK_WAV;
        str = Arrays.toString(Base64.getDecoder().decode(str));
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(fileName, MODE_PRIVATE);
            fos.write(str.getBytes()/*Base64.getDecoder().decode(str)*/);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testSaveFile(Context ctx) {
        String str = "FFDDFFDFDFDFDFFFD";
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput("testFile", MODE_PRIVATE);
            fos.write(str.getBytes()/*Base64.getDecoder().decode(str)*/);
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
            fin = ctx.openFileInput("testFile");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
