package com.e.words.dao;

import androidx.room.Insert;
import androidx.room.Transaction;

import com.e.words.abby.depricated.ExampleOld;
import com.e.words.entity.ForeignWord;
import com.e.words.entity.Translate;

import java.util.List;

public abstract class FullWordObjDao {

    @Insert
    abstract long addForeignWord(ForeignWord word);

    @Insert
    abstract long addExample(ExampleOld exampleOld);

    @Insert
    abstract void addTranslate(Translate translate);

    @Transaction
    public void addFullWord(ForeignWord word, List<Translate> translList, List<ExampleOld> explList) {
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
