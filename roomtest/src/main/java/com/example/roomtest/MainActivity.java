package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    UserDao userDao = AppDatabase.getInstance(getApplication()).userDao();

    User user1 = new User("Tom", "Brady", 40);
    User user2 = new User("Tom", "Hanks", 65);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addBtn = findViewById(R.id.addUserBtn);
        Button updateBtn = findViewById(R.id.updateUserBtn);
        Button deleteBtn = findViewById(R.id.deleteUserBtn);
        Button queryBtn = findViewById(R.id.queryUserBtn);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.addUserBtn :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        user1.setId(userDao.insertUser(user1));
                        user2.setId(userDao.insertUser(user2));
                    }
                }).start();
                break;
            case R.id.updateUserBtn :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        user1.setAge(42);
                        userDao.updateUser(user1);
                    }
                }).start();
                break;
            case R.id.deleteUserBtn :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userDao.deleteUserByLastName("Hanks");
                    }
                }).start();
                break;
            case R.id.queryUserBtn :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (User user : userDao.loadAllUsers()) {
                            Log.d("MainActivityTest", user.toString());
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
