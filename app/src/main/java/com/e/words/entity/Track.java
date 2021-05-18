package com.e.words.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    public boolean isLast;

    public Track() {
    }

    @Ignore
    public Track(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }

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
