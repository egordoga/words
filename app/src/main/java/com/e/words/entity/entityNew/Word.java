package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(index = true)
    public String word;
    public String json;
    public String transcrGB;
    public String transcrUS;

    public Word() {
    }

    public Word(String word, String json, String transcrGB, String transcrUS) {
        this.word = word;
        this.json = json;
        this.transcrGB = transcrGB;
        this.transcrUS = transcrUS;
    }
}
