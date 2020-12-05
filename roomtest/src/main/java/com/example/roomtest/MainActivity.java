package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


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
    public UserDao userDao;     // 注意接口必须是 public 类型
    public BookDao bookDao;     // 注意接口必须是 public 类型

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getDatabase(this);
        userDao = appDatabase.userDao();
        bookDao = appDatabase.bookDao();

        Button getBtn = findViewById(R.id.getUserBtn);
        Button addBtn = findViewById(R.id.addUserBtn);
        Button updateBtn = findViewById(R.id.updateUserBtn);
        Button deleteBtn = findViewById(R.id.deleteUserBtn);
        Button queryBtn = findViewById(R.id.queryUserBtn);
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
            case R.id.addUserBtn : {
                User user1 = new User("Tom", "Brady", 40);
                User user2 = new User("Tom", "Hanks", 65);
                userDao.insertAllUser(user1, user2);    // 添加数据（添加之后数据库中的的id会自动添加，但是临时对象
                                                        //（user1、user2）的ID并不会自动补上 ）
                Book book1 = new Book("你的名字", 524, "新海诚");
                bookDao.interBooks(book1);
                break;
            }
            case R.id.updateUserBtn : {
                User user = userDao.selectIdUser(1);  // 获取id=1的行
                if (user != null) {
                    user.age = 42;  // 更改年龄
                    userDao.updateUser(user);   // 更新数据行
                }
                break;
            }

            case R.id.deleteUserBtn : {
                userDao.deleteUserByLastName("Hanks");  // 删除lastName为 "Hanks" 的数据行
                break;
            }

            case R.id.queryUserBtn : {
                for (User user3 : userDao.loadAllUsers()) {     // 获取所有数据
                    Log.d("MainActivityTest", ""+ user3.id + "," +user3.firstName + ",=" + user3.lastName + ","+ user3.age );
                }
                for (Book book : bookDao.getAllBook()) {     // 获取所有数据
                    Log.d("MainActivityTest", ""+ book.id + "," + book.bookName + "," + book.pages + "," + book.author);
                }
                break;
            }
            default:
                break;
        }
    }
}
