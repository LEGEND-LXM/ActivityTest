package com.example.roomtest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Book.class}, version = 2, exportSchema = false)  // 指定版本号和数据表数据类型
public abstract class AppDatabase extends RoomDatabase {

    public final static String DB_NAME = "users_db";
    public static AppDatabase mAppDatabase;
    public abstract UserDao userDao();
    public abstract BookDao bookDao();

    // 数据库版本 1->2
    static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
                //  添加新的表

                database.execSQL("create table books ("
                        +"id integer primary key autoincrement not null,"
                        +"bookName text not null,"
                        +"pages integer not null default 0)");
            Log.d("MainActivityTest", String.valueOf(database.getVersion()));
        }
    };

    // 数据库版本 1->2
    static Migration MIGRATION_1_n = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //  添加新的表

            database.execSQL("create table books ("
                    +"id integer primary key autoincrement not null,"
                    +"bookName text not null,"
                    +"pages integer not null default 0,"
                    +"author text not null)");


        }
    };

    // 数据库版本 2->3
    static Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //  添加新的表
            database.execSQL("alter table books add column author text ");
            Log.d("MainActivityTest", String.valueOf(database.getVersion()));
        }
    };


    public static AppDatabase getDatabase(final Context context) {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    mAppDatabase = createDatabase(context);
                    Log.d("MainActivityTest", "数据库升级完成");
                }
            }
        }
        return mAppDatabase;
    }

    private static AppDatabase createDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .addMigrations(MIGRATION_1_n)
                .allowMainThreadQueries()
                .build();
    }


}
