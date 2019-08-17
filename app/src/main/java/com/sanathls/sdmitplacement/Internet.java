package com.sanathls.sdmitplacement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;

public class Internet {


    protected static boolean hasInternetAccess(Context context)
    {
        boolean isConnect = false;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null)
            { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    // connected to wifi
                    isConnect = true;
                }
                else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    // connected to the mobile provider's data plan
                    isConnect = true;
                }
            }
        }
        return isConnect;
    }
}