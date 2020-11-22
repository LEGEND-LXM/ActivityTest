package com.example.cardview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FruitActivity1", "0");
        setContentView(R.layout.activity_fruit);
        Log.d("FruitActivity1", "1");
        // 获取传入的水果 Name 和 imageId
        Intent intent = getIntent();
        Log.d("FruitActivity1", "2");
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        Log.d("FruitActivity1", "3");
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        Log.d("FruitActivity1", "4");
        // 获取控件实例
        Toolbar toolbar = findViewById(R.id.fruitToolbar);
        Log.d("FruitActivity1", "5");
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        Log.d("FruitActivity1", "6");
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);
        Log.d("FruitActivity1", "7");
        TextView fruitContentText = findViewById(R.id.fruit_content_view);
        Log.d("FruitActivity1", "8");
        // 设置 ToolBar
        setActionBar(toolbar);
        Log.d("FruitActivity1", "9");
        ActionBar actionBar = getSupportActionBar();
        Log.d("FruitActivity1", "10");
        if (actionBar != null) {
            // 启用 HomeAsUp 按钮，使用默认图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            Log.d("FruitActivity1", "11");
        }
        // 将水果名设置为标题
        collapsingToolbar.setTitle(fruitName);
        Log.d("FruitActivity1", "12");
        // 载入图片
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        Log.d("FruitActivity1", "13");
        // 设置内容文本
        String fruitContent = generateFruitContent(fruitName);
        Log.d("FruitActivity1", "14");
        fruitContentText.setText(fruitContent);
        Log.d("FruitActivity1", "15");
    }

    private String generateFruitContent(String fruitName) {
        Log.d("FruitActivity1", "16");
        StringBuffer fruitContent = new StringBuffer();
        Log.d("FruitActivity1", "17");
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        Log.d("FruitActivity1", "18");
        return fruitContent.toString();
    }

    /**
     * HomeAsUp 按钮点击处理函数
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                Log.d("FruitActivity1", "19");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}