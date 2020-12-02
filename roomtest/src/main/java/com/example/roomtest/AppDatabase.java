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

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}


//@Database(entities = {User.class}, version = 1)  // 指定版本号和数据表数据类型
//
//public abstract class AppDatabase extends RoomDatabase {
//
//    private final static String DB_NAME = "users_db";  // 定义数据库名称
//
//    private static AppDatabase mInstance;
//
//    public abstract UserDao userDao();
//
//    public static AppDatabase getInstance(Context context) {
//        Log.d("MainActivityTest", "AppDatabase 对象 null 检查");
//        if (mInstance == null) {
//        Log.d("MainActivityTest", "AppDatabase 为 null");
//            synchronized (AppDatabase.class) {
//                if (mInstance == null) {
//                    Log.d("MainActivityTest", "准备创建 AppDatabase 对象");
//                    mInstance = buildDatabase(context);
//                    Log.d("MainActivityTest", "创建 AppDatabase 对象成功");
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    private static AppDatabase buildDatabase(final Context context) {
//        Log.d("MainActivityTest", "开始进行 AppDatabase 对象创建");
//        // 保存数据表名
//        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
//                .build();
//    }
//
//    public void insertData(final User user) {
//        runInTransaction(new Runnable() {
//            @Override
//            public void run() {
//                userDao().insertUser(user);
//            }
//        });
//    }
//}
