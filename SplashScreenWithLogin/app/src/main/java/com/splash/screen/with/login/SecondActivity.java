package com.splash.screen.with.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class SecondActivity extends AppCompatActivity {
    TextView username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        username = findViewById(R.id.usernameTextView);
        password = findViewById(R.id.passwordTextView);
        Bundle bundle = getIntent().getExtras();

        username.setText(bundle.getString("username"));
        password.setText(bundle.getString("password"));
    }

    public void retour(View view) {
        Intent intent = new Intent(this, SplashActivity2.class);
        startActivity(intent);
    }
}