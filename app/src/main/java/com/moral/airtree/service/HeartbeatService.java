package com.moral.airtree.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.common.AConstants;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by bin.shen on 6/26/16.
 */
public class HeartbeatService extends Service implements Runnable {

    private Thread thread;
    private String userID;
    private int sleepInterval = 2;
    private boolean threadRunning = false;
    @Override
    public void run() {
        while (threadRunning) {
            try {
                sendHeartbeatPackage(userID);
                Thread.sleep(1000 * sleepInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userID = intent.getExtras().getString("LoginUserID");
        thread.start();
        threadRunning = true;
        Log.d("HeartbeatService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendHeartbeatPackage(String userID) {
        String url = AConstants.MORAL_API_BASE_PATH + "/user/" + userID + "/online";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("HeartbeatService", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonRequest);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.d("HeartbeatService", "onCreate");
        thread = new Thread(this);
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        threadRunning = false;
        Log.d("HeartbeatService", "onDestroy");
        super.onDestroy();
    }
}
