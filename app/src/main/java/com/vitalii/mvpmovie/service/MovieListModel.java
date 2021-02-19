package com.vitalii.mvpmovie.service;

import android.util.Log;

import com.vitalii.mvpmovie.contract.MovieListContract;
import com.vitalii.mvpmovie.model.Movie;
import com.vitalii.mvpmovie.model.MovieListResponse;
import com.vitalii.mvpmovie.networks.ApiClient;
import com.vitalii.mvpmovie.networks.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListModel implements MovieListContract.Model {

    private final String TAG = "MovieListModel";
    private int pageNumber = 1;

    @Override
    public void getMovieList(OnFinishedListener onFinishedListener, int pageNumber) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieListResponse> call = apiService.getPopularMovies(ApiClient.API_KEY, pageNumber);

        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.i(TAG, "Number of movies received: "+movies.size());

                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
