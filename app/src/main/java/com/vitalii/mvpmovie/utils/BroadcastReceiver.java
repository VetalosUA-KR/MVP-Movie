package com.vitalii.mvpmovie.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    public static Boolean isConnected = false;
    
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
        }
    }
}
