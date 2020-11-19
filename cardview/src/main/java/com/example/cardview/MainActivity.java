package com.example.cardview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Fruit[] fruits = {
            new Fruit("Apple", R.drawable.photo1), new Fruit("Banana", R.drawable.photo2),
            new Fruit("Orange", R.drawable.photo3), new Fruit("Watermelon", R.drawable.photo4),
            new Fruit("Pear", R.drawable.photo5), new Fruit("Grape", R.drawable.photo6),
            new Fruit("Pineapple", R.drawable.photo7), new Fruit("Strawberry", R.drawable.photo8),
            new Fruit("Cherry", R.drawable.photo9), new Fruit("Mango", R.drawable.photo10)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("CardView123", "1");
        Toolbar toolbar = findViewById(R.id.toolbar);
        Log.d("CardView123", "2");
        setSupportActionBar(toolbar);
        Log.d("CardView123", "3");
        mDrawerLayout = findViewById(R.id.drawerLayout);
        Log.d("CardView123", "4");
        ActionBar actionBar = getSupportActionBar();
        Log.d("CardView123", "5");
        FloatingActionButton fab = findViewById(R.id.fab);
        Log.d("CardView123", "6");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.done32);
        }
        Log.d("CardView123", "7");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CardView123", "8");
                Toast.makeText(MainActivity.this, "按下", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("CardView123", "9");
        initFruits();
        Log.d("CardView123", "10");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Log.d("CardView123", "11");
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        Log.d("CardView123", "12");
        recyclerView.setLayoutManager(layoutManager);
        Log.d("CardView123", "13");
        adapter = new FruitAdapter(fruitList);
        Log.d("CardView123", "14");
        recyclerView.setAdapter(adapter);
        Log.d("CardView123", "15");
    }

    /**
     * 初始化存放Fruit对象的数组
     * 首先对数组进行清空
     * 然后再进行赋值
     * 由于只是测试，所以进行随机赋值
     * */
    private void initFruits() {
        fruitList.clear();
        for (int i=0; i<50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }
}
