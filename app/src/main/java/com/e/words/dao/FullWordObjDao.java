package com.e.words.dao;

import androidx.room.Insert;
import androidx.room.Transaction;

import com.e.words.entity.Example;
import com.e.words.entity.ForeignWord;
import com.e.words.entity.Translate;

import java.util.List;

public abstract class FullWordObjDao {

    @Insert
    abstract long addForeignWord(ForeignWord word);

    @Insert
    abstract long addExample(Example example);

    @Insert
    abstract void addTranslate(Translate translate);

    @Transaction
    public void addFullWord(ForeignWord word, List<Translate> translList, List<Example> explList) {
        long wid = addForeignWord(word);
        if (wid != -1L) {
            for (Translate translate : translList) {
                translate.foreignId = wid;
                addTranslate(translate);
            }
           /* for (Example example : explList) {
                example.
            }*/
        }
    }
}
