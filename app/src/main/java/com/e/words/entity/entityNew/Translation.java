package com.e.words.entity.entityNew;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Translation {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String translation;
    public int wordId;

    public Translation(String translation, int wordId) {
        this.translation = translation;
        this.wordId = wordId;
    }
}
