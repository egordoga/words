package com.e.words.abby.abbyEntity.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;

import java.util.List;

public class TrackWithWords {

    @Embedded
    public Track track;

    @Relation(parentColumn = "id", entity = Word.class, entityColumn = "trackId")
    public List<Word> wordList;

    public TrackWithWords(Track track) {
        this.track = track;
    }
}
