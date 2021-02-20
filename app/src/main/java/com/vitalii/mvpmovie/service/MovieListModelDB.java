package com.vitalii.mvpmovie.service;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.vitalii.mvpmovie.contract.MovieListContract;
import com.vitalii.mvpmovie.model.Movie;
import com.vitalii.mvpmovie.database.AppDatabase;
import com.vitalii.mvpmovie.database.MovieDao;

import java.util.List;

public class MovieListModelDB implements MovieListContract.Model.RoomDatabase {

    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public MovieListModelDB(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    @Override
    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    @Override
    public void insertMovie(List<Movie> movieList) {
        new InsertMovieAsyncTask(movieDao).execute(movieList);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<List<Movie>, Void, Void> {

        private MovieDao movieDao;

        public InsertMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }


        @Override
        protected Void doInBackground(List<Movie>... lists) {
            if(lists != null && lists.length > 0) {
                movieDao.insertMovie(lists[0]);
            }
            return null;
        }
    }





}













