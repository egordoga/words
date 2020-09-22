package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.e.words.entity.ExampleOld;

import java.util.List;

@Dao
public interface ExampleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long add(ExampleOld exampleOld);

    @Insert
    void addAll(List<ExampleOld> exampleOlds);

    @Delete
    void remove(ExampleOld exampleOld);

    @Delete
    void removeAll(List<ExampleOld> exampleOld);

    @Update
    void update(ExampleOld exampleOld);

    @Update
    void updateAll(List<ExampleOld> exampleOld);

    @Query("select * from Example")
    List<ExampleOld> findAll();
}
