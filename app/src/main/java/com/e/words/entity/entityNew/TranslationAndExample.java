package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class TranslationAndExample implements Serializable {
    public int id;
    public String translation;
    @ColumnInfo(name = "idx")
    public int index;
    @Ignore
    public Boolean isChecked = false;

    @Relation(parentColumn = "id", entityColumn = "translId")
    public List<Example> examples;

    public TranslationAndExample() {
    }


}
