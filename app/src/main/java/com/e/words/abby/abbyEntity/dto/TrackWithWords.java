package com.e.words.abby.abbyEntity.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;

import java.io.Serializable;
import java.util.List;

public class TrackWithWords implements Serializable {

    @Embedded
    public Track track/* = new Track()*/; // TODO

    @Relation(parentColumn = "id", entity = Word.class, entityColumn = "trackId")
    public List<Word> wordList;

//    public TrackWithWords(Track track) {
//        this.track = track;
//    }

    public TrackWithWords(Track track, List<Word> wordList) {
        this.track = track;
        this.wordList = wordList;
    }
}
