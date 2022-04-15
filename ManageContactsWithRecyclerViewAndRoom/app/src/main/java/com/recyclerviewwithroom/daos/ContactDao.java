package com.recyclerviewwithroom.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.recyclerviewwithroom.entities.Contact;

import java.util.List;


@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact " +
            "WHERE firstName LIKE '%' || :keyword || '%' " +
            "OR lastName LIKE '%' || :keyword || '%' " +
            "OR email LIKE '%' || :keyword || '%' " +
            "OR phone LIKE '%' || :keyword || '%' " +
            "OR job LIKE '%' || :keyword || '%'")

    List<Contact> findByKeyword(String keyword);

    @Insert
    long insert(Contact element);

    @Delete
    void delete(Contact element);

    @Update
    void update(Contact element);
}
