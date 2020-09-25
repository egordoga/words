package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Word {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(index = true)
    public String word;
 //   public String json;
    public String transcript;
  //  public String transcrUS;
    @Ignore
    public String article;

    public Word() {
    }

    @Ignore
    public Word(String word, /*String json,*/ String transcript/*, String transcrUS*/) {
        this.word = word;
     //   this.json = json;
        this.transcript = transcript;
      //  this.transcrUS = transcrUS;
    }
}
