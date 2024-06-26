package com.example.cookidea_app.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookidea_app.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashPage extends AppCompatActivity {
    private final static int splashTimer = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_page);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashPage.this, MainActivity.class));
                SplashPage.this.finish();
            }
        }, splashTimer);
    }
}