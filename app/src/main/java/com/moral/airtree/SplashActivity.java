package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class SplashActivity extends Activity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        int screenWidth = screenWidth = display.getWidth();
//        int screenHeight = screenHeight = display.getHeight();

        Thread delayThread = new Thread(this);
        delayThread.start();
    }

    public void run() {
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
