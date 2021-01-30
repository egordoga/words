package com.e.words.repository;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.RequiresApi;
import androidx.room.Transaction;

import com.e.words.abby.abbyEntity.dto.dto_new.TrackSmall;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.dao.daoNew.TrackDao;
import com.e.words.dao.daoNew.WordDao;
import com.e.words.db.WordDb;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.worker.FileWorker;
import com.e.words.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TrackRepo {

    private final TrackDao trackDao;
    private final WordDao wordDao;
    private final Context ctx;

    public TrackRepo(Context ctx) {
        WordDb db = WordDb.getDatabase(ctx);
        this.trackDao = db.trackDao();
        this.wordDao = db.wordDao();
        this.ctx = ctx;
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
        return CompletableFuture.supplyAsync(trackDao::findAllTrack).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<TrackSmall> findAllTrackSmall() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(trackDao::findAllTrackSmall).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> findTrackNames() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(trackDao::findTrackNames).get();
    }

//    public void addToTrack(String trackName, String word) {
//        WordDb.dbExecutor.execute(() -> {
//            Track track = trackDao.findTrackByName(trackName);
//            addToTrack(track, word);
//            if (track.wordIds == null || track.wordIds.length() == 0) {
//                track.wordIds = String.valueOf(wordId);
//            } else {
//                track.wordIds = track.wordIds + ";;" + wordId;
//            }
//            trackDao.updateTrack(track);
//        });
//    }

    public void addToTrack(Track track, String word, TextToSpeech tts) {
        WordDb.dbExecutor.execute(() -> {
            if (track.words == null || track.words.length() == 0) {
                track.words = word;
            } else {
                track.words = track.words + ";;" + word;
            }
            trackDao.updateTrack(track);
            makeTranslateFiles(word, track.name, tts);
        });
    }

    public void updateTrack(Track track) {
        WordDb.dbExecutor.execute(() ->
            trackDao.updateTrack(track));
    }

    @Transaction
    public void insertTrack(String trackName, String word, TextToSpeech tts) {
        WordDb.dbExecutor.execute(() -> {
            Track track = new Track(trackName, word);
            trackDao.insertTrack(track);
            makeTranslateFiles(word, trackName, tts);
        });
    }

    private void makeTranslateFiles(String word, String trackName, TextToSpeech tts) {
        FileWorker worker = new FileWorker(ctx);
        WordObj wordObj = wordDao.findWordObjByWord(word);
        List<String> fileNames = worker.ttsToFiles(ctx, wordObj, tts);
        //  if (wordObj.word.fileNames == null || wordObj.word.fileNames.length() == 0) {
        wordObj.word.fileNames = word + ".wav" + Util.ListToStringForDB(fileNames);
        wordObj.word.trackName = trackName;
//        } else {
//            wordObj.word.fileNames += (";;" + Util.ListToStringForDB(fileNames));
//        }
        wordDao.updateWord(wordObj.word);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public List<WordWithId> findWordWithIdFromTrack(long trackId) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() -> {
//            Track track = trackDao.findTrackById(trackId);
//            String[] ids = track.wordIds.split(";;");
//            List<WordWithId> list = wordDao.findAllWordWithIdById(ids);
//            return list;
//        }).get();
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> findWordsFromTrack(long trackId) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            Track track = trackDao.findTrackById(trackId);
            String[] words = track.words.split(";;");
            return Arrays.asList(words);
        }).get();
    }

    public void deleteTrack(Track track) {
        WordDb.dbExecutor.execute(() -> {
            List<Word> words = wordDao.findWordsByTrackName(track.name);
            FileWorker worker = new FileWorker(ctx);
            for (Word word : words) {
                String[] files = word.fileNames.split(";;");
                for (int i = 1; i < files.length; i++) {
                    if ("silent".equalsIgnoreCase(files[i])) {
                        continue;
                    }
                    worker.deleteFile(files[i], ctx);
                }
                word.fileNames = null;
                word.trackName = null;
                wordDao.updateWord(word);
            }
            trackDao.deleteTrack(track);
        });
    }
}
