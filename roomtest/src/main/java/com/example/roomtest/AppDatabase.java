package com.example.roomtest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)  // 指定版本号和数据表数据类型
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
