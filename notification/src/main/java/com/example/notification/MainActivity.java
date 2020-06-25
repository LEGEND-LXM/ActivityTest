package com.example.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // NotificationManager对象声明
    private NotificationManager manager;
    // 通知渠道id
    private String CHANNEL_ID = "my_channel_01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton = findViewById(R.id.send_notice);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.send_notice :

                        createNotificationChannel();

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.title))
                                .setContentText(getString(R.string.text))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        manager.notify(1, builder.build());

                        /**Notification notification = new NotificationCompat.Builder(MainActivity.this, v.getId())
                                .setContentTitle("This is Title")
                                .setContentText("This is Text")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .build();
                        manager.notify(1, notification);*/
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel () {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 通知渠道的id CHANNEL_ID
        // 用户可以看到的通知渠道的名字.
        CharSequence name = getString(R.string.channel_name);
        // 用户可以看到的通知渠道的描述
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        // 配置通知渠道的属性
        mChannel.setDescription(description);
        // 设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        // 设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        //最后在notificationmanager中创建该通知渠道
        manager.createNotificationChannel(mChannel);
    }

}
