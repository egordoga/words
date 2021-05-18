package com.e.words.db.repository;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.RequiresApi;
import androidx.room.Transaction;

import com.e.words.entity.dto.WordObj;
import com.e.words.db.dao.WordDao;
import com.e.words.db.WordDb;
import com.e.words.entity.Json;
import com.e.words.entity.Word;
import com.e.words.util.Util;
import com.e.words.util.worker.FileWorker;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WordObjRepo {

    private final WordDao wordDao;
    private final Context ctx;

    public WordObjRepo(Context ctx) {
        WordDb db = WordDb.getDatabase(ctx);
        this.wordDao = db.wordDao();
        this.ctx = ctx;
    }

    public void addWord(WordObj wordObj, String json) {
        WordDb.dbExecutor.execute(() -> wordDao.addWord(wordObj, json));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WordObj findWordObjByWord(String word) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> wordDao.findWordObjByWord(word)).get();
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Word findWordByWord(String word) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() ->wordDao.findWordByWord(word)).get();
//    }

    public Word findWordById(long id) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> wordDao.findWordById(id)).get();
    }

    public List<WordObj> findAllWordObj() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(wordDao::findAllWordObj).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Json findJsonByWordId(long wordId) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> wordDao.findJsonByWordId(wordId)).get();
    }

//    public List<VocabularyDto> findAllVocabularyDto() {
//        return wordDao.findAllVocabularyDto();
//    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public List<WordObj> findAllWordByWords(String[] in) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() -> wordDao.findAllWordByWords(in)).get();
//    }
//
//    public void deleteWordById(long id) {
//        WordDb.dbExecutor.execute(() -> {
//            try {
//                Word word = findWordById(id);
//
//                wordDao.deleteWordById(id);
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public void deleteWordByWord(String word) {
//        WordDb.dbExecutor.execute(() -> {
//            wordDao.deleteWordByWord(word);
//        });
//    }

    public void deleteWord(Word word) {
        WordDb.dbExecutor.execute(() -> wordDao.deleteWord(word));
    }

    @Transaction
    public void deleteWordObj(WordObj wordObj) {
        WordDb.dbExecutor.execute(() -> wordDao.deleteWordObj(wordObj));
    }

    public void updateTranslationAndExample(WordObj wordObj, long[] oldTranslIds, boolean isNeedFiles, TextToSpeech tts) {
        WordDb.dbExecutor.execute(() -> {
            wordDao.updateTranslationAndExample(wordObj, oldTranslIds);
            if (isNeedFiles) {
                FileWorker worker = new FileWorker();
                worker.deleteFiles(wordObj.word.word, true, ctx);
                makeTranslateFiles(wordObj, tts, worker);
            }
        });
    }

    public void updateWord(Word word) {
        WordDb.dbExecutor.execute(() -> wordDao.updateWord(word));
    }

    public void updateWords(List<Word> words) {
        WordDb.dbExecutor.execute(() -> wordDao.updateWords(words));
    }

    //TODO maybe delete
    private void makeTranslateFiles(WordObj wordObj, TextToSpeech tts, FileWorker worker) {
        List<String> fileNames = worker.ttsToFiles(ctx, wordObj, tts);
        wordObj.word.fileNames = wordObj.word.word + ".wav" + Util.ListToStringForDB(fileNames);
        wordDao.updateWord(wordObj.word);
    }

//    public String findFileNames(String word) throws ExecutionException, InterruptedException {
//        return CompletableFuture.supplyAsync(() -> wordDao.findFileNamesByWord(word)).get();
//    }
}
