package com.e.words.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.e.words.entity.dto.TrackWithWords;
import com.e.words.entity.dto.TrackSmall;
import com.e.words.entity.Track;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("select * from Track where id = :id")
    Track findTrackById(long id);

    @Transaction
    @Query("select * from Track where id = :id")
    TrackWithWords findTrackWithWordsById(long id);

    @Query("select * from Track where name = :name")
    Track findTrackByName(String name);

    @Query("select * from Track order by name")
    List<Track> findAllTrack();

    @Transaction
    @Query("select * from Track order by name")
    List<TrackWithWords> findAllTrackWithWords();

    @Query("select id, name from Track order by name")
    List<TrackSmall> findAllTrackSmall();

    @Query("select name from Track order by name")
    List<String> findTrackNames();

    @Update
    void updateTrack(Track track);

//    @Transaction
//    @Update
//    void updateTrackWithWords(TrackWithWords track);

    @Insert
    long insertTrack(Track track);

//    @Transaction
//    @Insert
//    void insertTrackWithWords(TrackWithWords track);

    @Delete
    void deleteTrack(Track track);

    @Transaction
    @Query("select * from Track where name = :name")
    TrackWithWords findTrackWithWordsByName(String name);

}
