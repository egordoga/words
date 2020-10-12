package com.e.words.abby.abbyEntity.dto.dto_new;

import androidx.annotation.NonNull;

public class TrackSmall {
    public long id;
    public String name;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
