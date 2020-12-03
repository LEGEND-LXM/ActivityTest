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

    private final static String DB_NAME = "users_db";  // 定义数据库名称
    private static AppDatabase mInstance;
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = buildDatabase(context);
                }
            }
        }
        return mInstance;
    }

    private static AppDatabase buildDatabase(final Context context) {
        // 保存数据表名
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }
}
