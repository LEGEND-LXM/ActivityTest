package com.example.sharedpreferencestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button saveButton = findViewById(R.id.save_data);  //存储btn
        Button restoreButton = findViewById(R.id.restore_data);  //读取btn
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取 SharedPreferences.Editor 对象
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                //保存数据
                editor.putString("name", "tom");
                editor.putInt("age", 19);
                editor.putBoolean("married", false);
                //提交保存
                editor.apply();

            }
        });

        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                String name = preferences.getString("name", "");
                int age = preferences.getInt("age", -1);
                Boolean married = preferences.getBoolean("married", false);
                Log.d("MainActivity", "name:"+name);
                Log.d("MainActivity", "age:"+age);
                Log.d("MainActivity", "married:"+married);


            }
        });
    }
}
