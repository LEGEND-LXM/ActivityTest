package com.example.advancedskill;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    @SuppressWarnings("StaticFieldLeak")    // 忽略静态变量内存泄漏警告
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
