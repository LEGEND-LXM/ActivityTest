package com.example.table;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button button = findViewById(R.id.button);
////        replaceFragment(new RightFragment());
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                replaceFragment((new AnotherRightFragment()));
//            }
//        });

    }

//    private void replaceFragment(Fragment fragment) {  //动态添加碎片
//        FragmentManager fragmentManager = getSupportFragmentManager();  //获取FragmentManager
//
//        RightFragment rightFragment = (RightFragment) fragmentManager.findFragmentById(R.id.right_fragment);  //获取碎片中的碎片实例
//
//        FragmentTransaction transaction = fragmentManager.beginTransaction();  //开启一个事务
//        transaction.replace(R.id.right_fragment, fragment);  //像容器内添加或替换碎片
//        transaction.addToBackStack(null);  //添加事务到返回栈，参数用于描述返回栈状态，一般用null
//        transaction.commit();  //提交事务
//    }
}
