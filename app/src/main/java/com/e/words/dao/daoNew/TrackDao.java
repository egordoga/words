package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.e.words.abby.abbyEntity.dto.dto_new.TrackSmall;
import com.e.words.entity.entityNew.Track;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("select * from Track where id = :id")
    Track findTrackById(long id);

    @Query("select * from Track where name = :name")
    Track findTrackByName(String name);

    @Query("select * from Track")
    List<Track> findAllTrack();

    @Query("select id, name from Track order by name")
    List<TrackSmall> findAllTrackSmall();

    @Query("select name from Track order by name")
    List<String> findTrackNames();

    @Update
    void updateTrack(Track track);

    @Insert
    void insertTrack(Track track);
}
