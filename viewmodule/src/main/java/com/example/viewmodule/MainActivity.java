package com.example.viewmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private UIHandler handler;
    private TextView textView;
    private MainViewModule viewModule;

    private static final int UP_DATA_TEXT = 1;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharePf = getSharedPreferences("data", MODE_PRIVATE);
        int countReserved = sharePf.getInt("count_reserved", 0);

        // 获取 viewModule 实例
        viewModule = new ViewModelProvider(this, new MainViewModuleFactory(countReserved)).get(MainViewModule.class);

        // 获取控件实例
        Button button = findViewById(R.id.plusOneBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        textView = findViewById(R.id.infoText);

        handler = new UIHandler(this);

        clearBtn.setOnClickListener(this);
        button.setOnClickListener(this);

        viewModule.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // 在此处来对数据的变化做出响应
                // 更新UI
                refreshCounter(integer);
                Log.d("MainActivityTest", "changed:"+integer);
            }
        });

        refreshCounter(countReserved);
    }

    /**
     * 点击事件处理函数
     * 集成度更高（代码精简）
     * */
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.plusOneBtn :  // 实现计数器+1
                viewModule.pushOneCounter();
                // refreshCounter(viewModule.getCounter());
                break;
            case R.id.clearBtn :   // 清除计数
                // viewModule板块学习
                //viewModule.setCounter(0);
                viewModule.clearCountValue();
                // refreshCounter(viewModule.getCounter());
                break;
            default:
                break;
        }
    }

    private void refreshCounter(int count) {
        Message message = new Message();
        message.what = UP_DATA_TEXT;
        message.obj = textView;
        message.arg1 = count;
        handler.sendMessage(message);
    }

    /**
     * 内部类，用于实现更新UI
     * */
    private static class UIHandler extends Handler {
        WeakReference<MainActivity> mWeakRef;

        public UIHandler(MainActivity ref) {
            mWeakRef = new WeakReference<MainActivity>(ref);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mWeakRef.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case UP_DATA_TEXT :
                    TextView textView = (TextView) msg.obj;
                    String str = String.valueOf(msg.arg1);
                    textView.setText(str);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("count_reserved", viewModule.getCounter());
        editor.apply();
    }
}
