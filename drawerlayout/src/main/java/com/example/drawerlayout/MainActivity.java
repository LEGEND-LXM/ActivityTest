package com.example.drawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);

        setSupportActionBar(toolbar);   // 获取ActionBar实例（本例中由toolbar实现）
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 显示导航按钮（按钮名称为：HomeAsUp）
            actionBar.setHomeAsUpIndicator(R.drawable.setting2);    // 设置导航按钮图标
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // 加载菜单文件
        getMenuInflater().inflate(R.menu.toolbar, menu);    // 加载菜单布局
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
            case android.R.id.home :  // 按钮唤出滑动菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }

        return true;
    }
}
