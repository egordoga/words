package com.e.words.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Word.class, parentColumns = "id", childColumns = "wordId",
        onDelete = CASCADE))
public class Json {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String json;

    @ColumnInfo(index = true)
    public long wordId;

    public Json() {
    }

    @Ignore
    public Json(String json, long wordId) {
        this.json = json;
        this.wordId = wordId;
    }
}
