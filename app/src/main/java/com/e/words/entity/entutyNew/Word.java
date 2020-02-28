package com.e.words.entity.entutyNew;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String word;

    public Word(String word) {
        this.word = word;
    }
}
