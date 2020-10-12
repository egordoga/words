package com.e.words.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.abby.abbyEntity.dto.dto_new.TrackSmall;
import com.e.words.abby.abbyEntity.dto.dto_new.WordWithId;
import com.e.words.dao.daoNew.TrackDao;
import com.e.words.dao.daoNew.WordDao;
import com.e.words.db.WordDb;
import com.e.words.entity.entityNew.Track;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TrackRepo {

    private TrackDao trackDao;
    private WordDao wordDao;

    public TrackRepo(Context ctx) {
        WordDb db = WordDb.getDatabase(ctx);
        this.trackDao = db.trackDao();
        this.wordDao = db.wordDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Track findTrackById(long id) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackById(id)).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Track findTrackByName(String trackName) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackByName(trackName)).get();
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
            addToTrack(track, wordId);
//            if (track.wordIds == null || track.wordIds.length() == 0) {
//                track.wordIds = String.valueOf(wordId);
//            } else {
//                track.wordIds = track.wordIds + ";;" + wordId;
//            }
//            trackDao.updateTrack(track);
        });
    }

    public void addToTrack(Track track, long wordId) {
        WordDb.dbExecutor.execute(() -> {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<WordWithId> findWordWithIdFromTrack(long trackId) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            Track track = trackDao.findTrackById(trackId);
            String[] ids = track.wordIds.split(";;");
            List<WordWithId> list = wordDao.findAllWordWithIdById(ids);
            return list;
        }).get();
    }
}
