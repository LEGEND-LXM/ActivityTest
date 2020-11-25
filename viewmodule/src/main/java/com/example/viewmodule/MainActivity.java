package com.example.viewmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private UIHandler handler;

    private TextView textView;

    private static final int UP_DATA_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainViewModule viewModule = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModule.class);
        Button button = findViewById(R.id.plusOneBtn);
        textView = findViewById(R.id.infoText);

        handler = new UIHandler(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModule.pushOneCounter();
                Message message = new Message();
                message.what = UP_DATA_TEXT;
                message.obj = textView;
                Log.d("MainActivityTest","MainActivity ID查询"+textView.getId());
                message.arg1 = viewModule.getCounter();
                handler.sendMessage(message);
            }
        });


    }

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
                    Log.d("MainActivityTest","MainActivity进入处理");
                    TextView textView = (TextView) msg.obj;
                    String str = String.valueOf(msg.arg1);
                    textView.setText(str);
                    Log.d("MainActivityTest","MainActivity处理完成");
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
}
