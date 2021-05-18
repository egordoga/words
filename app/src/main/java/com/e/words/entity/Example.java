package com.e.words.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Example)) return false;
        Example example1 = (Example) o;
        return example.equals(example1.example);
    }

    @Override
    public int hashCode() {
        return Objects.hash(example);
    }
}
