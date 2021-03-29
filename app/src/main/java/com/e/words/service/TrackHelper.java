package com.e.words.service;

import android.content.Context;

import com.e.words.entity.entityNew.Track;
import com.e.words.repository.TrackRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrackHelper {

    public static Track currentTrack;
    private static int position = 0;
    public static List<Track> allTrack;

    public static Track getCurrentTrack() {
        if (currentTrack == null) {
            for (int i = 0; i < allTrack.size(); i++) {
                if (allTrack.get(i).isLast) {
                    position = i;
                    currentTrack = allTrack.get(i);
                    return currentTrack;
                }
            }
            currentTrack = allTrack.get(0);
        }
        return currentTrack;
    }

    public static Track getNext() {
        if (position == allTrack.size() - 1) {
            position = 0;
            currentTrack =  allTrack.get(position);
        } else {
            currentTrack = allTrack.get(++position);
        }
        return currentTrack;
    }

    public static Track getPrevious() {
        if (position == 0) {
            position = allTrack.size() - 1;
            currentTrack = allTrack.get(position);
        } else {
            currentTrack = allTrack.get(--position);
        }
        return currentTrack;
    }
}
