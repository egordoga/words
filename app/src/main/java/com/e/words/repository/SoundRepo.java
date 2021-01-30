package com.e.words.repository;

//import android.content.Context;
//
//import com.e.words.dao.daoNew.SoundDao;
//import com.e.words.db.WordDb;
//import com.e.words.entity.entityNew.Sound;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//public class SoundRepo {
//    private final SoundDao soundDao;
//
//    public SoundRepo(Context ctx) {
//        WordDb db = WordDb.getDatabase(ctx);
//        soundDao = db.soundDao();
//    }
//
//    public Sound findSoundByWordId(long wordId) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() -> soundDao.findSoundByWordId(wordId)).get();
//    }
//}
