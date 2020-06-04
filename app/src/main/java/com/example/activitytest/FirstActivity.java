package com.example.activitytest;

import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirstActivity extends BaseActivity {
    //Fruit类型列表
    private List<Fruit> fruitList = new ArrayList<>();
    //Fruit名称数组
    private String[] fruitsName = new String[] {"Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Apple", "Banana",
            "0range", "Watermelon","Pear","Grape", "Pineapple", "Strawberry", "Cherry"};
    //Fruit名称对于图片id
    private int[] fruitsImageId = new int[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);  //为活动加载布局文件
        initFruits();  //初始化Fruits列表（初始化数据）
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL);  //创建StaggeredGridLayoutManager对象
        recyclerView.setLayoutManager(layoutManager);  //设置排列方式
        RecyFruitAdaper adaper = new RecyFruitAdaper(fruitList);  //创建并初始化适配器
        recyclerView.setAdapter(adaper);


    }
    private void initFruits() {  //针对fruitList的初始化
        for (int i=0; i<fruitsName.length ;i++) {
            Fruit fruit = new Fruit(getRandomLengthName(fruitsName[i]), R.mipmap.ic_launcher);
            fruitList.add(fruit);
        }
    }

    private String getRandomLengthName(String name) {  //通过更改名字长度来是子项高度不一样
        Random random = new Random();
        int length = random.nextInt(20) + 1 ;  //创建1~20之间的随机数
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < length; i++) {
            builder.append(name);  //名字重复产生的随机数遍
        }
        return builder.toString();
    }
}
