package com.example.litepal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.Connector;

import java.util.List;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase = findViewById(R.id.create_database);
        Button addDatabase = findViewById(R.id.add_data);
        Button updateDatabase = findViewById(R.id.update_data);
        Button deleteDatabase = findViewById(R.id.delete_data);
        Button queryDatabase = findViewById(R.id.quary_data);

        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });

        addDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAuthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.95);
                book.setPress("Unknown");
                book.save();
            }
        });

        updateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //方式一
                /*Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAuthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.95);
                book.setPress("Unknown");
                book.save();
                book.setPrice(10.21);
                book.save();*/
                //方式二
                Book book = new Book();
                book.setPrice(14.26);
                book.setPress("Anchor");
                book.updateAll("name = ? and author=?", "The Da Vinci Code", "Dan Brown");
                book.setName("I love you");
                book.update(1);
            }
        });

        deleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Book.class, "id = ?", "3");
                LitePal.delete(Book.class, 4);
            }
        });

        queryDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询所有
                /*List<Book> books = LitePal.findAll(Book.class);
                for (Book book : books) {  //遍历列表
                    Log.d("MainActivity", "id : " + book.getId());
                    Log.d("MainActivity", "name : " + book.getName());
                    Log.d("MainActivity", "author : " + book.getAuthor());
                    Log.d("MainActivity", "pages : " + book.getPages());
                    Log.d("MainActivity", "price : " + book.getPrice());
                    Log.d("MainActivity", "press : " + book.getPress());
                }*/

                //查询否一行（id查询）
                Book book = LitePal.find(Book.class, 1);
                Log.d("MainActivity", "id : " + book.getId());
                Log.d("MainActivity", "name : " + book.getName());
                Log.d("MainActivity", "author : " + book.getAuthor());
                Log.d("MainActivity", "pages : " + book.getPages());
                Log.d("MainActivity", "price : " + book.getPrice());
                Log.d("MainActivity", "press : " + book.getPress());

            }
        });
    }
}
