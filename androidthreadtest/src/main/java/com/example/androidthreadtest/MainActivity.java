package com.example.androidthreadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int updataText = 1;  // 表示更新TextView这个动作
    private TextView textView;

    @SuppressLint("HandlerLeak")
    private Handler handler =  new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {   // 对具体的Message进行处理
                case updataText :
                    // 进行UI更新
                    textView.setText("Nice to meet you.");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeBtn = findViewById(R.id.changeTextBtn);
        textView = findViewById(R.id.textView);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = updataText;
                        // 将Message对象发送出去，handleMessage 方法对它进行处理
                        // 注意 handleMessage 是运行在主线程中的
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
