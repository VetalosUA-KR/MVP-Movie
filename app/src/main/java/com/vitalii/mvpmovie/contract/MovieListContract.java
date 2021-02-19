package com.vitalii.mvpmovie.contract;

import com.vitalii.mvpmovie.model.Movie;

import java.util.List;

public interface MovieListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<Movie> moviesArrayList);
            void onFailure(Throwable throwable);
        }

        void getMovieList(OnFinishedListener onFinishedListener, int pageNumber);
    }

    interface View {

        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(List<Movie> movieListArray);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {

        void onDestroy();
        void getMoreData(int pageNumber);
        void requestDataFromServer();
    }
}
