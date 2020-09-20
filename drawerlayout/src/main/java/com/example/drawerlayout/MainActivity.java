package com.example.drawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.drawerlayout.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // 加载菜单文件
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 处理按钮的点击事件
        switch (item.getItemId()) {
            case R.id.backup :
                Log.d("ToolBarTest", "backup");
                break;
            case R.id.delete :
                Log.d("ToolBarTest", "delete");
                break;
            case R.id.setting :
                Log.d("ToolBarTest", "setting");
                break;
            default:
                break;
        }

        return true;
    }
}
