package com.splash.screen.with.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SplashActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        new CountDownTimer(4500, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),OneActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
        final ImageView splash = findViewById(R.id.logo);
        TranslateAnimation animation = new TranslateAnimation(0, 0, -300, 0);
        animation.setDuration(3000);
        splash.startAnimation(animation);
    }
}