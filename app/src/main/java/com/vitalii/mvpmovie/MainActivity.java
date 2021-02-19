package com.vitalii.mvpmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vitalii.mvpmovie.contract.MovieListContract;
import com.vitalii.mvpmovie.model.Movie;
import com.vitalii.mvpmovie.presenter.MoviePresenter;
import com.vitalii.mvpmovie.utils.BroadcastReceiver;
import com.vitalii.mvpmovie.view.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListContract.View {


    private RecyclerView rvMovieList;
    private ProgressBar pbLoading;
    private List<Movie> movieList;

    private MovieListAdapter movieListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private MoviePresenter moviePresenter;

    private int pageNumber = 1;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovieList = findViewById(R.id.rvMovieList);
        pbLoading = findViewById(R.id.pbLoading);

        movieList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        rvMovieList.setLayoutManager(linearLayoutManager);
        rvMovieList.setHasFixedSize(true);

        moviePresenter = new MoviePresenter(this);

        //Ask the presenter to load data
        moviePresenter.requestDataFromServer();

    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    //Data which we get from Presenter's, display in RecyclerView
    //this data we get from MoviePresenter -> onFinished()
    @Override
    public void setDataToRecyclerView(List<Movie> movieListArray) {
        movieList.addAll(movieListArray);
        movieListAdapter = new MovieListAdapter(movieList, MainActivity.this);
        rvMovieList.setAdapter(movieListAdapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("Error:", throwable.getMessage());
        Toast.makeText(this, "Error in getting data", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}







