package com.e.words.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.e.words.db.dao.TrackDao;
import com.e.words.db.dao.WordDao;
import com.e.words.entity.Example;
import com.e.words.entity.Json;
import com.e.words.entity.Track;
import com.e.words.entity.Translation;
import com.e.words.entity.Word;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class, Example.class, Track.class, Translation.class, Json.class}, version = 18, exportSchema = false)
public abstract class WordDb extends RoomDatabase {
    private static WordDb instance;
    private static final String DB_NAME = "word.db";

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract WordDao wordDao();

    public abstract TrackDao trackDao();

    public static WordDb getDatabase(final Context context) {
        if (instance == null) {
            synchronized (WordDb.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), WordDb.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
