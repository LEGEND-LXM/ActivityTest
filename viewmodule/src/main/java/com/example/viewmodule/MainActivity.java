package com.example.viewmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MainViewModule viewModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModule = ViewModelProvider(Context).get(MainActivity.class);

    }
}
