package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Translation.class, parentColumns = "id", childColumns = "translId",
        onDelete = CASCADE, onUpdate = CASCADE))
public class Example {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String example;
    public int index;
    @ColumnInfo(index = true)
    public long translId;
    @Ignore
    public Boolean isChecked = false;

    public Example() {
    }

    @Ignore
    public Example(String example, int index) {
        this.example = example;
        this.index = index;
    }

    @Ignore
    public Example(String example, int index, Boolean isChecked) {
        this.example = example;
        this.index = index;
        this.isChecked = isChecked;
    }
}
