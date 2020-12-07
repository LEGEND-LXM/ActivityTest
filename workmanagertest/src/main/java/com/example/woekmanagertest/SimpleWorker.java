package com.example.woekmanagertest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SimpleWorker extends Worker {
    public SimpleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("SimpleWorkerTest", "do work in SimpleWorker");

        // 返回值 Result.success() 表示成功执行
        return Result.success();
    }
}
