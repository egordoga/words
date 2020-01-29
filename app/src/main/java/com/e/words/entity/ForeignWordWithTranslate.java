package com.e.words.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ForeignWordWithTranslate {
  //  @Embedded
    public ForeignWord word;
//    @Relation(parentColumn = "id", entity = Translate.class, entityColumn = "foreignId")
    public List<Translate> translateList;

    public ForeignWordWithTranslate(ForeignWord word, List<Translate> translateList) {
        this.word = word;
        this.translateList = translateList;
    }
}
