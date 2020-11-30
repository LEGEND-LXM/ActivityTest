package com.example.roomtest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "users_db")     // 申明为实体类，指定数据表名
public class User {

    @PrimaryKey(autoGenerate = true)    // 设置为主键，自动生成
    @ColumnInfo(name = "userId")        // 指定 column 名（列名） 为 userId
    private long id = 0;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "age")
    private int age ;

    @Ignore
    public User(String _firstName) {
        this.firstName = _firstName;
    }

    public User(String _firstName, String _lastName, int _age) {
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.age = _age;

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

}
