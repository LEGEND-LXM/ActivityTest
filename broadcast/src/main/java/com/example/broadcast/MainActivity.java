package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.NetworkInterface;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);  //获取实例
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                自定义广播内容
//                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
//                sendOrderedBroadcast(intent, null);  //发送广播
                Intent intent = new Intent("com.example.broadcast.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);  //发送本地广播
            }
        });
//        发送标准广播
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.zeample.broadcast.LOCAL_BROADCAST");  //添加action（此处为网络状态发生改变）
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);  //注册广播

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcast.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);  //注册本地广播监听器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        发送标准广播
//        unregisterReceiver(networkChangeReceiver);  //取消广播

        localBroadcastManager.unregisterReceiver(localReceiver);  //取消注册
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取 ConnectivityManager 实例（系统服务类，专门管理网络连接）
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo实例
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {  //判断是否有网络
                Toast.makeText(context, "net is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "net is unavailable", Toast.LENGTH_SHORT).show();
            }

//            Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();
        }
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}
