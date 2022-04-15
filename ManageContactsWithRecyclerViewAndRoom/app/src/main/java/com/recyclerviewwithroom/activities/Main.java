package com.recyclerviewwithroom.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.recyclerviewwithroom.AppDatabase;
import com.recyclerviewwithroom.R;
import com.recyclerviewwithroom.adapters.ContactsAdapter;
import com.recyclerviewwithroom.adapters.Tabs;
import com.recyclerviewwithroom.fragments.AddContact;
import com.recyclerviewwithroom.fragments.ContactsList;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class Main extends AppCompatActivity {
    public static AppDatabase database;
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    public static TabLayout myTabLayout;

    private Tabs tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "contacts").allowMainThreadQueries().build();

        ActivityCompat.requestPermissions(Main.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE
                },
                1);

        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyContacts";
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }


        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My Contacts");
        myViewPager = findViewById(R.id.main_tabs_pager);

        tabs = new Tabs(getSupportFragmentManager());
        myViewPager.setAdapter(tabs);

        myTabLayout = findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ContactsList.list = database.contactDao().getAll();
                ContactsList.adapter = new ContactsAdapter(Main.this, ContactsList.list);
                ContactsList.recyclerView.setAdapter(ContactsList.adapter);
                ContactsList.adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    ContactsList.list.clear();
                    ContactsList.list.addAll(database.contactDao().findByKeyword(s));
                    ContactsList.adapter.notifyDataSetChanged();
                } else {
                    ContactsList.list.clear();
                    ContactsList.list.addAll(database.contactDao().getAll());
                    ContactsList.adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.home) {
            TabLayout.Tab tab = Main.myTabLayout.getTabAt(0);
            tab.select();
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(Main.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                AddContact.photoUriPath = resultUri.getPath();
                Picasso.get().load(resultUri).into(AddContact.photo);
            }
        }
    }


}
