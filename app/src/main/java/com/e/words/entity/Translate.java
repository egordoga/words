package com.e.words.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = ForeignKey.class, parentColumns = "id", childColumns = "foreignId"))
public class Translate {

  //  @PrimaryKey(autoGenerate = true)
    public Long id;
    public String word;
    public Byte[] sound;
    public Boolean isMain;
    public Long foreignId;

    public Translate(String word) {
        this.word = word;
    }
}
