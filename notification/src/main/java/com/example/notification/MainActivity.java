package com.example.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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


        createNotificationChannel();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.send_notice :

                        createNotificationChannel();

                        Intent intent = new Intent(MainActivity.this, MyNotification.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                        /**
                         * 创建通知内容
                         * setContentTitle(getString(R.string.title)) ：设置通知的标题
                         * setContentText("This is Text") ：设置通知的正文
                         * setSmallIcon(R.mipmap.ic_launcher) ：设置通知小图标
                         * setPriority(NotificationCompat.PRIORITY_DEFAULT) ：设置通知优先级
                         * setStyle() ：设置通知的样式模板
                         * setAutoCancel(true) : 点按通知后自动移除通知
                         * setContentIntent(pendingIntent) : 添加点按操作
                         * */
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.title))
                                .setContentText(getString(R.string.text))
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        manager.notify(1, builder.build());
                        break;
                    default:
                        break;
                }
            }
        });

    }
    /**
     * importance ：此参数确定出现任何属于此渠道的通知时如何打断用户
     * Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
     *      Build.VERSION.SDK_INT ：获取当前android版本号
     *      此句的作用是获取版本号并判断，这样在低版本时就不需要创建通知渠道
     * */
    private void createNotificationChannel () {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.channel_name);
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
            }
    }

}
