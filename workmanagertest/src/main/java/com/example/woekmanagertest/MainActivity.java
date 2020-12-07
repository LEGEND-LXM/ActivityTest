package com.example.woekmanagertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.Btn);

        OneTimeWorkRequest requset = new OneTimeWorkRequest.Builder(SimpleWorker.class)
                .addTag("第一")           // 添加标签
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                .setInitialDelay(5, TimeUnit.SECONDS)       // 延迟5s之后执行
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getBaseContext())
                        .getWorkInfoByIdLiveData(requset.getId())
                        .observe((LifecycleOwner) getBaseContext(), new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                switch(workInfo.getState()) {
                                    case SUCCEEDED:
                                        Log.d("MainActivityTest", "do work succeed!");
                                        break;
                                    case FAILED:
                                        Log.d("MainActivityTest", "do work failed!");
                                        break;
                                    default:
                                        break;
                                }


                            }
                        });
            }
        });
    }
}