package com.vitalii.mvpmovie.presenter;

import android.util.Log;

import com.vitalii.mvpmovie.contract.MovieListContract;
import com.vitalii.mvpmovie.model.Movie;
import com.vitalii.mvpmovie.service.MovieListModel;

import java.util.List;

public class MoviePresenter implements MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private MovieListContract.View movieListView;
    private MovieListContract.Model movieListModel;

    public MoviePresenter(MovieListContract.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new MovieListModel();
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

    //Method which is called in MovieListModel -> getMovieList()
    //Sending loaded data to View to display, the method setDataToRecyclerView() processed in MainActivity
    @Override
    public void onFinished(List<Movie> moviesArrayList) {
        movieListView.setDataToRecyclerView(moviesArrayList);
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









