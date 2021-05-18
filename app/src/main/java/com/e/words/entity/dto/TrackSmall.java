package com.e.words.entity.dto;

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
