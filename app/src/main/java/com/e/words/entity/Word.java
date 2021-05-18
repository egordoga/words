package com.e.words.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Word {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(index = true)
    public String word;
    public String transcript;
    public String fileNames;
    public Long trackId;
    @Ignore
    public String article;

    public Word() {
    }

    @Ignore
    public Word(String word, String transcript) {
        this.word = word;
        this.transcript = transcript;
    }
}
