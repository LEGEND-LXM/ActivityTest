package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private AppDatabase appDatabase;
    private UserDao userDao;

    private Button getBtn;
    private Button addBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button queryBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getInstance(this);
        userDao = appDatabase.userDao();

        getBtn = findViewById(R.id.getUserBtn);
        addBtn = findViewById(R.id.addUserBtn);
        updateBtn = findViewById(R.id.updateUserBtn);
        deleteBtn = findViewById(R.id.deleteUserBtn);
        queryBtn = findViewById(R.id.queryUserBtn);
        textView = findViewById(R.id.messageView);

        getBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d("调试接口", "进入监听器");
        switch(v.getId()) {
            case R.id.addUserBtn :
                Log.d("调试接口", "进入 addUserBtn");
                User user1 = new User("Tom", "Brady", 40);
                User user2 = new User("Tom", "Hanks", 65);
                Log.d("调试接口", "定义数据完成");
                userDao.insertAll(user1, user2);
                Log.d("调试接口", "数据插入成功");
                Log.d("MainActivityTest", "user1Id:"+ user1.id);
                Log.d("MainActivityTest", "user2Id:"+ user2.id);
                Log.d("调试接口", "信息打印成功1");
                break;
            case R.id.updateUserBtn :
                Log.d("调试接口", "进入 updateUserBtn");
                User user = userDao.selectIdUser(1);
                Log.d("调试接口", "获取实例成功");
                if (user != null) {
                    Log.d("调试接口", "判断为非mull");
                    user.age = 42;
                    userDao.updateUser(user);
                    Log.d("调试接口", "更新数据成功");
                    Log.d("MainActivityTest", ""+ user.id + "," +user.firstName + ",=" + user.lastName + ","+ user.age);
                    Log.d("调试接口", "信息打印成功2");
                }
                break;
            case R.id.deleteUserBtn :
                Log.d("调试接口", "进入 deleteUserBtn");
                userDao.deleteUserByLastName("Hanks");
                Log.d("调试接口", "数据删除成功3");
                break;
            case R.id.queryUserBtn :
                Log.d("调试接口", "进入 queryUserBtn");
                for (User user3 : userDao.loadAllUsers()) {
                    Log.d("MainActivityTest", ""+ user3.id + "," +user3.firstName + ",=" + user3.lastName + ","+ user3.age );
                }
                Log.d("调试接口", "信息打印成功4");
                break;
            default:
                break;
        }
    }
}
