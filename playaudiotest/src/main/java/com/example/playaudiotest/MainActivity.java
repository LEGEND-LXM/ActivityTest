package com.example.playaudiotest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private  MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button stop = findViewById(R.id.stop);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {  // 判断是否在播放音乐，没有则播放
                    mediaPlayer.start();  // 开始播放
                }
                Log.d("Self", String.valueOf(mediaPlayer.getCurrentPosition()));
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {  // 判断是否在播放音乐，在则暂停
                    mediaPlayer.pause();  // 暂停播放
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {  // 判断是否在播放音乐，在则停止
                    mediaPlayer.reset();  // 停止播放
                    try {
                        initMediaPlayer();  // 初始化，在停止之后可以继续播放
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }



    private void initMediaPlayer() throws IOException {
        AssetManager assetManager = getAssets();  // 得到 AssetManager 实例，可用于读取 assets 目录下的任何资源
        AssetFileDescriptor fd = assetManager.openFd("music.mp3");  // 打开音频文件句柄
        // 做播放前准备
        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());;
        mediaPlayer.prepare();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
