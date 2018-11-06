package com.notepoint4ugmail.newsontip;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    boolean isNetworkAvailable = networkState();
                    if (isNetworkAvailable) {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }else {
                        startActivity(new Intent(SplashScreen.this, Network.class));
                    }

                } catch (InterruptedException e) {
                    Toast.makeText(SplashScreen.this, "" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };
        timer.start();
    }

    public boolean networkState(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
