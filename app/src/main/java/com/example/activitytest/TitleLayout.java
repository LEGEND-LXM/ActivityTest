package com.example.activitytest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button back = findViewById(R.id.title_back);  //获取自定义控件的内部控件
        Button edit = findViewById(R.id.title_Edit);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = getParent().getParent().getClass().getSimpleName();
                Log.d("TitleLayout", str);
            }
        });

        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
