package com.e.words.entity.entityNew;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transcription {

    @PrimaryKey
    public int id;
    public String transcription;
    public String soundFileName;
    public int wordId;
}