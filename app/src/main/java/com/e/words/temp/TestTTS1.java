package com.e.words.temp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.tts.TextToSpeech;

import com.e.words.abby.abbyEntity.dto.dto_new.StrLocale;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TestTTS1 {
    private final Context ctx;
    private List<String> files = new ArrayList<>();

    TextToSpeech tts;


    public TestTTS1(Context ctx) {
        this.ctx = ctx;
    }


//    public void makeFileFromByteArr(WordObj wordObj) {
//        SoundRepo repo = new SoundRepo(ctx);
//        try {
//            byte[] soundArr = repo.findSoundByWordId(wordObj.word.id).soundGB;
//            File dir = ctx.getFilesDir();
//            File file = new File(dir, wordObj.word.word + ".wav");
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(soundArr);
//            fos.flush();
//            fos.close();
//        } catch (ExecutionException | InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void playFileFromMediaPlayer(WordObj wordObj) {
        File dir = ctx.getFilesDir();
        File file = new File(dir, wordObj.word.word + ".wav");
        MediaPlayer mp = new MediaPlayer();
      //  MediaPlayer mp = MediaPlayer.create();

        Uri uri = Uri.fromFile(file);
        mp.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            float speed = 1f;
            mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(speed));
            mp.setLooping(false);
            mp.setDataSource(ctx, uri);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testExoPlayer() {
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(ctx).build();
        File dir = ctx.getFilesDir();
        File file1 = new File(dir, "issue.wav");
        Uri uri1 = Uri.fromFile(file1);
//        File file2 = new File(dir, "look.wav");
//        Uri uri2 = Uri.fromFile(file2);
        MediaItem itm1 = MediaItem.fromUri(uri1);
  //      MediaItem itm2 = MediaItem.fromUri(uri2);
        player.addMediaItem(itm1);
  //      player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue1.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue2.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue3.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue4.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue5.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue6.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue7.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue8.wav"))));
//        player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File("issue9.wav"))));
 //       player.addMediaItem(itm2);
        for (int i = 1; i < 10; i++) {
            player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, "issue" + i + ".wav"))));
        }
        player.prepare();
        player.play();
    }


    public void testTts() {
        List<StrLocale> ruList = new ArrayList<>();
        List<StrLocale> enList = new ArrayList<>();
        ruList.add(new StrLocale("Японский робот", "a.wav", null));
        ruList.add(new StrLocale("эта штука сможет ходить", "b.wav", null));
        ruList.add(new StrLocale("только бы получить товар", "c.wav", null));
        enList.add(new StrLocale("You look very pretty", "aa.wav", null));
        enList.add(new StrLocale("Your new hairdo is not bad", "bb.wav", null));
        enList.add(new StrLocale("Thank you for picking me up", "cc.wav", null));
        int i = 0;
     //   TextToSpeech tts = null;
        Locale enLoc = new Locale("en");
        Locale ruLoc = new Locale("ru");
        tts = new TextToSpeech(ctx, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(ruLoc);
                File dir = ctx.getFilesDir();
                for (StrLocale strLocale : ruList) {
                    File file = new File(dir, strLocale.fileName);
                    tts.synthesizeToFile(strLocale.str, null, file, null);
                }
                tts.setLanguage(enLoc);
                for (StrLocale strLocale : enList) {
                    File file = new File(dir, strLocale.fileName);
                    tts.synthesizeToFile(strLocale.str, null, file, null);
                }
//                File file = new File(dir, "a.wav");
//                //   if (isInit) {
//                tts.synthesizeToFile(, null, file, null);
//                file = new File(dir, "b.wav");
//                tts.synthesizeToFile("эта штука сможет ходить", null, file, null);
//                file = new File(dir, "aa.wav");
//                tts.synthesizeToFile("You look very pretty", null, file, null);
//                tts.setLanguage(ruLoc);
//                file = new File(dir, "c.wav");
//                tts.synthesizeToFile("только бы получить товар", null, file, null);
//                tts.setLanguage(enLoc);
//                file = new File(dir, "bb.wav");
//                tts.synthesizeToFile("Your new hairdo is not bad", null, file, null);
//                file = new File(dir, "cc.wav");
//                tts.synthesizeToFile("Thank you for picking me up", null, file, null);
            }
        });
    }


}
