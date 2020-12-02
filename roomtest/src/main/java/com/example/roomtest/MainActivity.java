package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDatabase appDatabase;
    private UserDao userDao;

    private Button buttonClear;
    private Button buttonInsert;
    private TextView textView;

//    User user1 = new User(1, "Tom", "Brady", 40);
//    User user2 = new User(2, "Tom", "Hanks", 65);
//    User user3 = new User(3, "mark", "jack", 75);

//    User user1 = new User("Tom", "Brady", 40);
//    User user2 = new User("Tom", "Hanks", 65);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivityTest", "1");
        setContentView(R.layout.activity_test);
        Log.d("MainActivityTest", "2");

        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "room_demp")
                .allowMainThreadQueries()
                .build();

        userDao = appDatabase.userDao();

        buttonClear = findViewById(R.id.button);
        buttonInsert = findViewById(R.id.button2);
        textView = findViewById(R.id.textView6);

        // update
        update();

        buttonClear.setOnClickListener(this);
        buttonInsert.setOnClickListener(this);



//        appDb =  AppDatabase.getInstance(this);
//        Log.d("MainActivityTest", "3");
//
//        Button addBtn = findViewById(R.id.addUserBtn);
//        Button updateBtn = findViewById(R.id.updateUserBtn);
//        Button deleteBtn = findViewById(R.id.deleteUserBtn);
//        Button queryBtn = findViewById(R.id.queryUserBtn);
//
//        addBtn.setOnClickListener(this);
//        updateBtn.setOnClickListener(this);
//        deleteBtn.setOnClickListener(this);
//        queryBtn.setOnClickListener(this);

    }

    public void update() {
        List<User> list = userDao.getAll();
        String text = "";

        for (int i=0; i<list.size(); i++) {
            User user = list.get(i);
            text = text+" "+ user.id + ",username=" +user.userName + "; password=" + user.passWord + "\n";
            textView.setText(text);
        }

        if (text.equals("")) {
            textView.setText("hahaha");
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button :
                User user1 = new User("tom", "123156");
                User user2 = new User("jerry", "283394");
                userDao.insertAll(user1, user2);

                // update
                update();
                break;
            case R.id.button2 :
                userDao.deleteAll();

                // update
                update();
                break;

            default:
                break;
        }

//        switch(v.getId()) {
//            case R.id.addUserBtn :
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        appDb.insertData(user1);
//                        appDb.insertData(user1);
//
//                        Log.d("MainActivityTest", "user1id:"+user1.getId());
//                        Log.d("MainActivityTest", "user1id:"+user2.getId());
//                    }
//                }).start();
//                break;
//            case R.id.updateUserBtn :
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        user1.setAge(42);
//                        appDb.userDao().updateUser(user1);
//                    }
//                }).start();
//                break;
//            case R.id.deleteUserBtn :
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        appDb.userDao().deleteUserByLastName("Hanks");
//                    }
//                }).start();
//                break;
//            case R.id.queryUserBtn :
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (User user : appDb.userDao().loadAllUsers()) {
//                            Log.d("MainActivityTest", user.toString());
//                        }
//                    }
//                }).start();
//                break;
//            default:
//                break;
//        }
    }
}
