package com.e.words.abby.depricated;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = Translate.class, parentColumns = "id", childColumns = "translateId"))
public class ExampleOld {

 //   @PrimaryKey(autoGenerate = true)
    public Long id;

    public String forein;
    public String translate;
    public Byte[] foreinSound;
    public Byte[] translateSound;
    public Long translateId;

    public ExampleOld(String forein, String translate, Byte[] foreinSound, Byte[] translateSound) {
        this.forein = forein;
        this.translate = translate;
        this.foreinSound = foreinSound;
        this.translateSound = translateSound;
    }
}
