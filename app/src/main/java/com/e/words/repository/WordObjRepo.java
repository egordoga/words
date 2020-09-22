package com.e.words.repository;

import android.app.Application;
import android.content.Context;

import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.dao.daoNew.WordDao;
import com.e.words.db.WordDb;
import com.e.words.entity.entityNew.Word;

import java.util.List;

public class WordObjRepo {

    private WordDao wordDao;
    private WordDb db;

    public WordObjRepo(Context ctx) {
        db = WordDb.getDatabase(ctx);
        this.wordDao = db.wordDao();
    }

    public void addWord(WordObj wordObj, List<byte[]> sounds) {
        WordDb.dbExecutor.execute(() -> {
            wordDao.addWord(wordObj, sounds);
        });
    }

    public void findWordByWord(String word) {
        WordDb.dbExecutor.execute(() -> {
           Word w = wordDao.findWordByWord(word);
            System.out.println(w.transcrGB);
        });
    }
}
