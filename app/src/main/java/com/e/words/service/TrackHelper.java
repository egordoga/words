package com.e.words.service;

import com.e.words.entity.Track;
import com.e.words.util.Util;

import java.util.List;

public class TrackHelper {

    public static Track currentTrack;
    public static int position = 0;
    public static List<Track> allTrack;
    public static Util.CallSpin fragPlay;

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
            currentTrack = allTrack.get(position);
        } else {
            currentTrack = allTrack.get(++position);
        }
        fragPlay.refreshSpin(position);
        return currentTrack;
    }

    public static Track getPrevious() {
        if (position == 0) {
            position = allTrack.size() - 1;
            currentTrack = allTrack.get(position);
        } else {
            currentTrack = allTrack.get(--position);
        }
        fragPlay.refreshSpin(position);
        return currentTrack;
    }
}
