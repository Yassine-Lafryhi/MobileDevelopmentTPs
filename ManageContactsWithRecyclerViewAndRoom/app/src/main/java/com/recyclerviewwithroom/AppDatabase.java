package com.recyclerviewwithroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.recyclerviewwithroom.daos.ContactDao;
import com.recyclerviewwithroom.entities.Contact;


@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}

