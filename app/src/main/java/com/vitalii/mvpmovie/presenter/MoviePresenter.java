package com.vitalii.mvpmovie.presenter;

import com.vitalii.mvpmovie.contract.MovieListContract;
import com.vitalii.mvpmovie.model.Movie;

import java.util.List;

public class MoviePresenter implements MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private MovieListContract.View movieListView;
    private MovieListContract.Model movieListModel;

    public MoviePresenter(MovieListContract.View movieListView, MovieListContract.Model movieListModel) {
        this.movieListView = movieListView;
        this.movieListModel = movieListModel;
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

    @Override
    public void requestDataFromServer() {
        if(movieListView != null) {
            movieListView.showProgress();
        }

        movieListModel.getMovieList(this, 1);
    }

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









