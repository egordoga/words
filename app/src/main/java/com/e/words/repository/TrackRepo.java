package com.e.words.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.abby.abbyEntity.dto.dto_new.TrackSmall;
import com.e.words.dao.daoNew.TrackDao;
import com.e.words.db.WordDb;
import com.e.words.entity.entityNew.Track;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TrackRepo {

    private TrackDao trackDao;

    public TrackRepo(Context ctx) {
        WordDb db = WordDb.getDatabase(ctx);
        this.trackDao = db.trackDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Track findTrackById(long id) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackById(id)).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Track> findAllTrack() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findAllTrack()).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<TrackSmall> findAllTrackSmall() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findAllTrackSmall()).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> findTrackNames() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackNames()).get();
    }

    public void addToTrack(String trackName, long wordId) {
        WordDb.dbExecutor.execute(() -> {
            Track track = trackDao.findTrackByName(trackName);
            if (track.wordIds == null || track.wordIds.length() == 0) {
                track.wordIds = String.valueOf(wordId);
            } else {
                track.wordIds = track.wordIds + ";;" + wordId;
            }
            trackDao.updateTrack(track);
        });
    }

    public void insertTrack(String trackName, long wordId) {
        WordDb.dbExecutor.execute(() -> {
            Track track = new Track(trackName, String.valueOf(wordId));
            trackDao.insertTrack(track);
        });
    }
}
