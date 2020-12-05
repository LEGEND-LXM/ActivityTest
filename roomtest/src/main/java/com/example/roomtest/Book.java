package com.example.roomtest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.PublicKey;

@Entity (tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "bookName")
    public String bookName;

    @ColumnInfo(name = "pages")
    public int pages;


    @ColumnInfo(name = "author")
    public String author;

    public Book(String bookName, int pages, String author) {
        this.bookName = bookName;
        this.pages = pages;
        this.author = author;
    }

}
