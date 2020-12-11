package com.example.advancedskill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        person person1 = new person("Tom", 20);
        Intent intent = new Intent();
        intent.putExtra("Tom", person1);

    }
}