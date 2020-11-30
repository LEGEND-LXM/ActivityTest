package com.example.roomtest;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(version = 1, entities = {User.class})  // 指定版本号和数据表数据类型
public abstract class AppDatabase extends RoomDatabase {

    // 保存数据表名
    private static String TABLE_NAME = "users_db";

    private static AppDatabase mInstance;

    public abstract UserDao userDao();

    public static AppDatabase getInstance(Application context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = buildDatabase(context);
                }
            }
        }
        return mInstance;
    }



    private static AppDatabase buildDatabase(final Application appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, TABLE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d("AppDatabaseTask", "database Create");
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.d("AppDatabaseTask", "database onOpen");
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

//    public UserDao getUserDao() {
//        return this.userDao();
//    }

    public void insertData(final User user) {
        runInTransaction(new Runnable() {
            @Override
            public void run() {
                userDao().insertUser(user);
            }
        });
    }


}
