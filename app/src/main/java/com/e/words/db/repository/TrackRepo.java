package com.e.words.db.repository;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.RequiresApi;
import androidx.room.Transaction;

import com.e.words.entity.dto.TrackWithWords;
import com.e.words.entity.dto.WordObj;
import com.e.words.db.dao.TrackDao;
import com.e.words.db.dao.WordDao;
import com.e.words.db.WordDb;
import com.e.words.entity.Track;
import com.e.words.entity.Word;
import com.e.words.util.Util;
import com.e.words.util.worker.FileWorker;

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
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Track findTrackById(long id) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() -> trackDao.findTrackById(id)).get();
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Track findTrackByName(String trackName) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackByName(trackName)).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public TrackWithWords findTrackWithWordsByName(String trackName) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackWithWordsByName(trackName)).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public TrackWithWords findTrackWithWordsById(long id) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> trackDao.findTrackWithWordsById(id)).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Track> findAllTrack() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(trackDao::findAllTrack).get();
    }

    public List<TrackWithWords> findAllTrackWithWords() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(trackDao::findAllTrackWithWords).get();
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public List<TrackSmall> findAllTrackSmall() throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(trackDao::findAllTrackSmall).get();
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> findTrackNames() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(trackDao::findTrackNames).get();
    }

    public void addToTrack(Track track, Word word, TextToSpeech tts) {
        WordDb.dbExecutor.execute(() -> {
            word.trackId = track.id;
            wordDao.updateWord(word);
            makeTranslateFiles(word.word, tts);
        });
    }

//    public void addToTrackWithWords(TrackWithWords trackWw, String wordStr, TextToSpeech tts) {
//        WordDb.dbExecutor.execute(() -> {
//            Word word = wordDao.findWordByWordStr(wordStr);
//            trackWw.wordList.add(word);
//            trackDao.updateTrackWithWords(trackWw);
//            makeTranslateFiles(wordStr, trackWw.track.name, tts);
//        });
//    }

    public void updateTrack(Track track) {
        WordDb.dbExecutor.execute(() ->
                trackDao.updateTrack(track));
    }

//    @Transaction
//    public void insertTrack(String trackName, String word, TextToSpeech tts) {
//        WordDb.dbExecutor.execute(() -> {
//            Track track = new Track(trackName, word);
//            trackDao.insertTrack(track);
//            makeTranslateFiles(word, trackName, tts);
//        });
//    }

    @Transaction
    public void insertTrackWithWords(String trackName, Word word, TextToSpeech tts) {
        WordDb.dbExecutor.execute(() -> {
            Track track = new Track(trackName);
            word.trackId = trackDao.insertTrack(track);
            wordDao.updateWord(word);
            makeTranslateFiles(word.word, tts);
        });
    }

    private void makeTranslateFiles(String word, TextToSpeech tts) {
        FileWorker worker = new FileWorker();
        WordObj wordObj = wordDao.findWordObjByWord(word);
        List<String> fileNames = worker.ttsToFiles(ctx, wordObj, tts);
        wordObj.word.fileNames = word + ".wav" + Util.ListToStringForDB(fileNames);
        wordDao.updateWord(wordObj.word);
    }


//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public List<String> findWordsFromTrack(long trackId) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() -> {
//            TrackWithWords track = trackDao.findTrackWithWordsById(trackId);
//            return track.wordList.stream().map(w -> w.word).collect(Collectors.toList());
//        }).get();
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Word> findWordsFromTrack(long trackId) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            TrackWithWords track = trackDao.findTrackWithWordsById(trackId);
            return track.wordList;
        }).get();
    }


    public void deleteTrack(TrackWithWords trackWw) {
        WordDb.dbExecutor.execute(() -> {
            FileWorker worker = new FileWorker();
            for (Word word : trackWw.wordList) {
                word.trackId = null;
                worker.deleteFiles(word.word, true, ctx);
            }
            wordDao.updateWords(trackWw.wordList);
            trackDao.deleteTrack(trackWw.track);
        });
    }

    public void deleteTrack(Track track) {
        WordDb.dbExecutor.execute(() -> trackDao.deleteTrack(track));
    }

    @Transaction
    public void deleteTrackWithLastWord(Track track, WordObj wordObj) {
        WordDb.dbExecutor.execute(() -> {
            trackDao.deleteTrack(track);
            wordDao.deleteWordObj(wordObj);
        });
    }
}
