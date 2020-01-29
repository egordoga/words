package com.e.words.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.e.words.entity.Example;
import com.e.words.entity.ForeignWord;
import com.e.words.entity.Lesson;
import com.e.words.entity.Translate;

//@Database(entities = {ForeignWord.class, Example.class, Lesson.class, Translate.class}, version = 1)
public abstract class WordDb extends RoomDatabase {
    private static WordDb instance;
    private static final String DB_NAME = "word.db";

    public static WordDb getDatabase(final Context context) {
        if (instance == null) {
            synchronized (WordDb.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), WordDb.class, DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}
