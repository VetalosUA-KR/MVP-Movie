package com.vitalii.mvpmovie.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vitalii.mvpmovie.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from movies")
    LiveData<List<Movie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(List<Movie> movies);
}
