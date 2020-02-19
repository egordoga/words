package com.e.words.sound;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.abby.rest.RestRequest;
import com.e.words.temp.TempSoundFile;

import static android.media.AudioTrack.MODE_STATIC;

public class SoundTrack {

    public static void playTrack(byte[] sound) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_VOICE_CALL)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        AudioFormat format = new AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(44100)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build();

      //  byte[] sound = RestRequest.getSoundBytes("look.wav");
      //  byte[] sound = TempSoundFile.getBytesFromFile();

        System.out.println("GGGGG " + sound.length);

        AudioTrack track = new AudioTrack(attributes, format, 8000, MODE_STATIC, 1);
        track.write(sound, 0, sound.length);
        track.play();
    }
}
