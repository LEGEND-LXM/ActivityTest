package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SQLiteOpenHelper 实例进行初始化
        dbHelper = new DatabaseHelper(this, "BookStore.db", null, 2);
        Button create_database = findViewById(R.id.create_database);
        Button add_data = findViewById(R.id.add_data);
        Button update_data = findViewById(R.id.update_data);
        Button delete_data = findViewById(R.id.delete_data);
        Button quary_data = findViewById(R.id.quary_data);

        create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取SQLiteDatabase对象
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //创建ContentValues对象来保存数据
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                //插入第一条数据
                db.insert("Book", null, values);
            }
        });

        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取SQLiteDatabase对象
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //创建ContentValues对象来保存需要更新的数据
                ContentValues values = new ContentValues();
                values.put("price", 10.00);
                db.update("Book", values, "name=?", new String[] {"The Da Vinci Code"});
            }
        });

        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取SQLiteDatabase对象
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "id=?", new String[] {"2"});
            }
        });

        quary_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取SQLiteDatabase对象
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中的所有数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if(cursor.moveToFirst()) {
                    do {
                        //遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        String str = "name ：" + name + "\n" + "name ：" + name + "\n" + "author ：" + author + "\n" + "pages ：" + pages + "\n" + "price ：" + price ;
                        Log.d("MainActivity", str);

                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
