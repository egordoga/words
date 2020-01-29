package com.e.words.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;

import com.e.words.entity.ForeignWordWithTranslate;

public interface ForeignWordDao {
  //  @Transaction
  //  @Query("select * from ForeignWord where id = :foreignId")
    LiveData<ForeignWordWithTranslate> findById(String foreignId);
}
