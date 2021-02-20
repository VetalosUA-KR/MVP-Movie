package com.vitalii.mvpmovie.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


public class BroadcastReceiver extends android.content.BroadcastReceiver {

    public static boolean isConnected = true;
    public static MutableLiveData<Boolean> status = new MutableLiveData<>();
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if(noConnectivity) {
                Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
                isConnected = false;
            }
            else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                isConnected = true;
            }
            status.setValue(isConnected);
        }
    }
}
