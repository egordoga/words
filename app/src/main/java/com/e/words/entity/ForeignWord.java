package com.e.words.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity
public class ForeignWord {

 //   @PrimaryKey(autoGenerate = true)
    public Long id;

    public String word;
    public String transcription;
    public Byte[] sound;

    public ForeignWord(String word, String transcription, Byte[] sound) {
        this.word = word;
        this.transcription = transcription;
        this.sound = sound;
    }

    public ForeignWord(String word) {
        this.word = word;
    }
}
