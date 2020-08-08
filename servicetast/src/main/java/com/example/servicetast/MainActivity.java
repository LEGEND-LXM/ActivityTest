package com.example.servicetast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 成功绑定时调用
            downloadBinder = (MyService.DownloadBinder) service;  // 获取 DownloadBinder 实例
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 解除绑定时调用
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startService = findViewById(R.id.startServiceBtn);
        Button stopService = findViewById(R.id.stopServiceBtn);
        Button bindServiceBtn = findViewById(R.id.bindBtn);
        Button unbindServiceBtn = findViewById(R.id.unBindBtn);
        final Button startIntentService = findViewById(R.id.startIntentService);

        startIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyIntentService", String.valueOf(Thread.currentThread().getId()));
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                startService(intent);
            }
        });

        bindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(MainActivity.this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);  // 绑定服务
            }
        });

        unbindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);  // 解绑服务
            }
        });

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);  // 启动服务
            }
        });
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent); // 停止服务
            }
        });
    }


}
