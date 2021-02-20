package com.vitalii.mvpmovie.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.vitalii.mvpmovie.MainActivity;
import com.vitalii.mvpmovie.contract.MovieListContract;
import com.vitalii.mvpmovie.model.Movie;
import com.vitalii.mvpmovie.service.MovieListModel;
import com.vitalii.mvpmovie.service.MovieListModelDB;

import java.util.List;

public class MoviePresenter implements MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private MovieListContract.View movieListView;
    private MovieListContract.Model movieListModel;
    private MovieListContract.Model.RoomDatabase movieListDB;
    private Application application;

    public MoviePresenter(MovieListContract.View movieListView, Application application) {
        this.movieListView = movieListView;
        movieListModel = new MovieListModel();
        movieListDB = new MovieListModelDB(application);
        this.application = application;
    }

    @Override
    public void onDestroy() {
        this.movieListView = null;
    }

    @Override
    public void getMoreData(int pageNumber) {

        if(movieListView != null) {
            movieListView.showProgress();
        }

        movieListModel.getMovieList(this, pageNumber);
    }

    //Query from MainActivity for load data
    @Override
    public void requestDataFromServer() {
        if(movieListView != null) {
            movieListView.showProgress();
        }

        //Turn to Model for load data
        movieListModel.getMovieList(this, 1);
    }

    public LiveData<List<Movie>> getAllMovieFromDB() {
        return movieListDB.getAllMovies();
    }

    //Method which is called in MovieListModel -> getMovieList()
    //Sending loaded data to View to display, the method setDataToRecyclerView() processed in MainActivity
    @Override
    public void onFinished(List<Movie> moviesArrayList) {
        //in this place wee will be saved our Movie in Database
        //need to create a class MovieListModelDB in package "service" which will be responsible for saved Movie in database
        //then in this class we need create a method who will be send Movie to View if we don't have a internet
        //and in View we will be have a variable which has type a LiveData, and when we don't have internet we will be display Movie from database


        movieListView.setDataToRecyclerView(moviesArrayList);
        movieListDB.insertMovie(moviesArrayList);

        /*movieListDB.getAllMovies().observe(application, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                for(Movie m : movies) {

                }
            }
        });*/

        if(movieListView != null) {
            movieListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

        movieListView.onResponseFailure(throwable);
        if(movieListView != null) {
            movieListView.hideProgress();
        }

    }
}









