package com.e.words.entity.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.e.words.entity.Track;
import com.e.words.entity.Word;

import java.io.Serializable;
import java.util.List;

public class TrackWithWords implements Serializable {

    @Embedded
    public Track track/* = new Track()*/; // TODO

    @Relation(parentColumn = "id", entity = Word.class, entityColumn = "trackId")
    public List<Word> wordList;

    public TrackWithWords(Track track, List<Word> wordList) {
        this.track = track;
        this.wordList = wordList;
    }
}
