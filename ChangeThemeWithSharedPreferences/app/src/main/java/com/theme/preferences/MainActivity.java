package com.theme.preferences;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    public static final String RED_THEME = "RED";
    public static final String YELLOW_THEME = "YELLOW";
    public static final String BLACK_THEME = "BLACK";
    public static final String ORANGE_THEME = "ORANGE";
    public static final String PURPLE_THEME = "PURPLE";
    public static final String GREEN_THEME = "GREEN";
    public static final String BLUE_THEME = "BLUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String theme = getUsedTheme();

        switch (theme) {
            case RED_THEME:
                setTheme(R.style.Theme_Red);
                break;
            case YELLOW_THEME:
                setTheme(R.style.Theme_Yellow);
                break;
            case BLACK_THEME:
                setTheme(R.style.Theme_Black);
                break;
            case BLUE_THEME:
                setTheme(R.style.Theme_Blue);
                break;
            case GREEN_THEME:
                setTheme(R.style.Theme_Green);
                break;
            case PURPLE_THEME:
                setTheme(R.style.Theme_Purple);
                break;
            case ORANGE_THEME:
                setTheme(R.style.Theme_Orange);
                break;
        }

        setContentView(R.layout.activity_main);

    }

    private String getUsedTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ArrayList<String> themes = new ArrayList<>();
        themes.addAll(Arrays.asList(RED_THEME, YELLOW_THEME, BLACK_THEME, BLUE_THEME, PURPLE_THEME, ORANGE_THEME, GREEN_THEME));
        String selectedTheme = "";
        for (String theme : themes) {
            if (prefs.getBoolean(theme, false)) {
                selectedTheme = theme;
                break;
            }
        }
        return selectedTheme;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}