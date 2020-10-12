package com.e.words.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.dao.daoNew.WordDao;
import com.e.words.db.WordDb;
import com.e.words.entity.entityNew.Json;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WordObjRepo {

    private WordDao wordDao;
   // private WordDb db;

    public WordObjRepo(Context ctx) {
        WordDb db = WordDb.getDatabase(ctx);
        this.wordDao = db.wordDao();
    }

    public void addWord(WordObj wordObj, String json, List<byte[]> sounds) {
        WordDb.dbExecutor.execute(() -> {
            wordDao.addWord(wordObj, json, sounds);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WordObj findWordByWord(String word) throws ExecutionException, InterruptedException {
//        WordObj w = null;
//        try {
//            w = new FindWordAsyncTask().get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        WordDb.dbExecutor.execute(() -> {
//            Word ww = wordDao.findWordByWord("look");
//            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH " + ww.word);
//           WordObj w = wordDao.findWordObjByWord(word);
//            System.out.println(w.word.transcript);
//            System.out.println(w.translations.get(0).examples.get(0).example);
//            System.out.println(w.word.id);
//        });
//        Word ww = wordDao.findWordByWord("look");
//        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH " + ww.word);
//        WordObj w = wordDao.findWordObjByWord(word);
//        System.out.println(w);
        return CompletableFuture.supplyAsync(() ->wordDao.findWordObjByWord(word)).get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Json findJsonByWordId(long wordId) throws ExecutionException, InterruptedException {
      //  CompletableFuture<Json> future = (CompletableFuture<Json>) WordDb.dbExecutor.submit(() -> wordDao.findJsonByWordId(wordId));
      //  CompletableFuture<Json> future =  CompletableFuture.supplyAsync(() -> wordDao.findJsonByWordId(wordId));
        return CompletableFuture.supplyAsync(() -> wordDao.findJsonByWordId(wordId)).get();
    }

    public List<VocabularyDto> findAllVocabularyDto() {
        return wordDao.findAllVocabularyDto();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<WordObj> findAllWordByIds(String[] in) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> wordDao.findAllWordByIds(in)).get();
    }

    public void deleteWordById(long id) {
        WordDb.dbExecutor.execute(() -> {
            wordDao.deleteWordById(id);
        });
    }

    public void deleteWordByWord(String word) {
        WordDb.dbExecutor.execute(() -> {
            wordDao.deleteWordByWord(word);
        });
    }

    public void updateWord(WordObj wordObj) {
        WordDb.dbExecutor.execute(() -> {
            wordDao.updateTranslationAndExample(wordObj);
        });
    }

    public void printCount() {
        WordDb.dbExecutor.execute(() -> {
            System.out.println("EX " + wordDao.getCountEx());
            System.out.println("TR " + wordDao.getCountTr());
            System.out.println("WORD " + wordDao.getCountWord());
        });
    }


}
