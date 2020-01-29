package com.e.words.dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.e.words.entity.Example;

public interface ExampleDao {

  //  @Insert(onConflict = OnConflictStrategy.IGNORE)
    long add(Example example);
}
