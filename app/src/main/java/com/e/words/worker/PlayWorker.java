package com.e.words.worker;

import android.content.Context;
import android.net.Uri;

import com.e.words.R;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Track;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.File;

public class PlayWorker {

    private Context ctx;

    public PlayWorker(Context ctx) {
        this.ctx = ctx;
    }

//    public SimpleExoPlayer getPlayer(String fileNamesStr) {
//        SimpleExoPlayer player = new SimpleExoPlayer.Builder(ctx).build();
//        File dir = ctx.getFilesDir();
//        String[] fileNames = fileNamesStr.split(";;");
//        for (String fileName : fileNames) {
//
//        }
//        return null;
//    }

    public static Uri getSilentUri(int pause) {
        if (pause < 150) {
            return Uri.parse("android.resource://raw/" + R.raw.silent100);
        } else if (pause < 250) {
            return Uri.parse("android.resource://raw/" + R.raw.silent200);
        } else if (pause < 350) {
            return Uri.parse("android.resource://raw/" + R.raw.silent300);
        } else if (pause < 450) {
            return Uri.parse("android.resource://raw/" + R.raw.silent400);
        } else if (pause < 550) {
            return Uri.parse("android.resource://raw/" + R.raw.silent500);
        } else if (pause < 650) {
            return Uri.parse("android.resource://raw/" + R.raw.silent600);
        } else if (pause < 750) {
            return Uri.parse("android.resource://raw/" + R.raw.silent700);
        } else if (pause < 850) {
            return Uri.parse("android.resource://raw/" + R.raw.silent800);
        } else if (pause < 950) {
            return Uri.parse("android.resource://raw/" + R.raw.silent900);
        } else if (pause < 1050) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1000);
        } else if (pause < 1150) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1100);
        } else if (pause < 1250) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1200);
        } else if (pause < 1350) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1300);
        } else if (pause < 1450) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1400);
        } else if (pause < 1550) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1500);
        } else if (pause < 1650) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1600);
        } else if (pause < 1750) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1700);
        } else if (pause < 1850) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1800);
        } else if (pause < 1950) {
            return Uri.parse("android.resource://raw/" + R.raw.silent1900);
        } else {
            return Uri.parse("android.resource://raw/" + R.raw.silent2000);
        }
    }

}
