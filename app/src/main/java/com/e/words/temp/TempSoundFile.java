package com.e.words.temp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.R;
import com.e.words.abby.rest.RestRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static android.provider.Settings.Global.getString;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TempSoundFile {
    public static final Path PATH = Paths.get("D:\\sound.wav");

    public void recordFile() {
        byte[] sound = RestRequest.getSoundBytes("look.wav");
        try {
            System.out.println("HHDDHH  " + sound.length);
            Files.write(PATH, sound, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  // static String request = getString(R.string.str);

  /*  public static byte[] getBytesFromString() {
        byte[] arr = request.getBytes();
        System.out.println("DFDFD  " + arr.length);
        return arr;
    }*/
}
