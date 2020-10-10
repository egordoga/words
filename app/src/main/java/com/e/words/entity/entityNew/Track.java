package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Track {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(index = true)
    public String name;
    public String wordIds; // строка с ID слов, разделенных ";;", чтобы не мучаться со списком
    public boolean isLast;

    public Track() {
    }

    public Track(String name, String wordIds) {
        this.name = name;
        this.wordIds = wordIds;
    }
}
