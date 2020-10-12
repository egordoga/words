package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
