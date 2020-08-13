package com.e.words.entity.entityNew;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Example {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String en;
    public String ru;
    public int translId;

    public Example(String en, String ru, int translId) {
        this.en = en;
        this.ru = ru;
        this.translId = translId;
    }
}
