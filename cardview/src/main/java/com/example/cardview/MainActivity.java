package com.example.cardview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
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
    private SwipeRefreshLayout swipeRefresh;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        ActionBar actionBar = getSupportActionBar();
        FloatingActionButton fab = findViewById(R.id.fab);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.done32);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "按下", Toast.LENGTH_SHORT).show();
            }
        });

        initFruits();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);  // 设置下拉刷新进度条的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // 设置下拉刷新的监听器
            @Override
            public void onRefresh() {  // 检测到下拉后调用
                refreshFruits();
            }
        });
    }
    /**
     * 刷新界面
     * */
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {  // 切换回主线程（主线程才能更新UI）
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();  // 通知数据发生了变化
                        swipeRefresh.setRefreshing(false);  // 表示刷新事件结束，隐藏那个刷新进度条
                    }
                });
            }
        }).start();
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
