package com.e.words.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = ForeignWord.class, parentColumns = "id", childColumns = "wordId"))
public class Lesson {

  //  @PrimaryKey(autoGenerate = true)
    public Long id;

    public String name;
    public Long wordId;
}
