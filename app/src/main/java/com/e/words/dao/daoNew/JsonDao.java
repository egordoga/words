package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Query;

import com.e.words.entity.entityNew.Json;

@Dao
public interface JsonDao {

    @Query("select * from Json where wordId = :wordId")
    Json findJsonByWordId(String wordId);
}
