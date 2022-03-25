package com.splash.screen.with.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class OneActivity extends AppCompatActivity {
    EditText username, password;
    private String TAG = "GLSID2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        Log.d(TAG, "onCreate vient d'être appelée !");
        Toast.makeText(this, "onCreate vient d'être appelée !", Toast.LENGTH_SHORT).show();

        username = findViewById(R.id.usernameTextView);
        password = findViewById(R.id.passwordTextView);
    }

    public void envoyer(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String usernameString = username.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        if (usernameString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.fill_in), Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("username", username.getText().toString().trim());
            bundle.putString("password", password.getText().toString().trim());
            intent.putExtras(bundle);

            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart vient d'être appelée !");
        Toast.makeText(this, "onStart vient d'être appelée !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart vient d'être appelée !");
        Toast.makeText(this, "onRestart vient d'être appelée !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume vient d'être appelée !");
        Toast.makeText(this, "onResume vient d'être appelée !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause vient d'être appelée !");
        Toast.makeText(this, "onPause vient d'être appelée !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop vient d'être appelée !");
        Toast.makeText(this, "onStop vient d'être appelée !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy vient d'être appelée !");
        Toast.makeText(this, "onDestroy vient d'être appelée !", Toast.LENGTH_SHORT).show();
    }
}