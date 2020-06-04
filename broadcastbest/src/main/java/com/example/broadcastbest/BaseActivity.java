package com.example.broadcastbest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ForceOfflineRexeiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);  //将当前活动添加到集合中
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);  //当此活动被销毁时将其从集合中移除
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);  //取消广播接收器注册
            receiver = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastbest.FORCE_OFFLINE");
        receiver = new ForceOfflineRexeiver();
        registerReceiver(receiver, intentFilter);  //注册广播接收器
    }

    private class ForceOfflineRexeiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);  //创建对话框对象
            builder.setTitle("Warning");  //设置标题
            builder.setMessage("You are forced to be offline. Please try to login again.");  //设置显示内容
            builder.setCancelable(false);  //设置是否可被取消
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {  //注册确定按钮并添加点击事件
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();  //销毁所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);  //从新打开登陆界面
                }
            });
            builder.show();  //显示对话框
        }
    }
}
