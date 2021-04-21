package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Track implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(index = true)
    public String name;
    //  public String wordIds; // строка с ID слов, разделенных ";;", чтобы не мучаться со списком
 //   public String words; // строка слов, разделенных ";;", чтобы не мучаться со списком
    public boolean isLast;

    public Track() {
    }

//    public Track(String name, String words) {
//        this.name = name;
//        this.words = words;
//    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Track)) return false;
//        Track track = (Track) o;
//        return id == track.id &&
//                Objects.equals(name, track.name) &&
//                Objects.equals(words, track.words);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, words);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        Track track = (Track) o;
        return id == track.id &&
                name.equals(track.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
