package com.e.words.temp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.R;
import com.e.words.abby.rest.RestRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static android.provider.Settings.Global.getString;

public class TempSoundFile {
  //  public static final Path PATH = Paths.get("D:\\sound.wav");

    public void recordFile() {
      //  byte[] sound = RestRequest.getSoundBytes("look.wav");
//        try {
//            System.out.println("HHDDHH  " + sound.length);
//            Files.write(PATH, sound, StandardOpenOption.WRITE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static byte[] getBytesFromFile() {
        File file = new File("cp:/data/sound.wav");
        byte[] arr = new byte[(int) file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(arr);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }


  // static String request = getString(R.string.str);

  /*  public static byte[] getBytesFromString() {
        byte[] arr = request.getBytes();
        System.out.println("DFDFD  " + arr.length);
        return arr;
    }*/
}
