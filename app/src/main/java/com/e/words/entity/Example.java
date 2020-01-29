package com.e.words.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = Translate.class, parentColumns = "id", childColumns = "translateId"))
public class Example {

 //   @PrimaryKey(autoGenerate = true)
    public Long id;

    public String forein;
    public String translate;
    public Byte[] foreinSound;
    public Byte[] translateSound;
    public Long translateId;

    public Example(String forein, String translate, Byte[] foreinSound, Byte[] translateSound) {
        this.forein = forein;
        this.translate = translate;
        this.foreinSound = foreinSound;
        this.translateSound = translateSound;
    }
}
