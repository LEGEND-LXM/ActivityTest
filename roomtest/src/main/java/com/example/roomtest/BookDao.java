package com.example.roomtest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void interBooks(Book book);

    @Delete
    void deleteBooks(Book... book);

    @Update
    void updateBook(Book newBook);

    @Query("select * from books")
    List<Book> getAllBook();

    @Query("select * from books where id = :id")
    Book getIdBook(int id);
}
