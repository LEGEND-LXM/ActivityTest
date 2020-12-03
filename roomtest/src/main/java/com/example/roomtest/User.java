package com.example.roomtest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//@Entity
//public class User {
//    @PrimaryKey(autoGenerate = true)    // 设置为主键，自动生成
//    public int id;
//
//    @ColumnInfo(name = "user_name")
//    public String userName;
//
//    @ColumnInfo(name = "password")
//    public String passWord;
//
//    public User(String userName, String passWord) {
//        this.userName = userName;
//        this.passWord = passWord;
//    }
//}


@Entity(tableName = "users")     // 申明为实体类，指定数据表名
public class User {
    @PrimaryKey(autoGenerate = true)    // 设置为主键，自动生成
    @ColumnInfo(name = "userId")
    public int id;

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "age")
    public int age ;

    @Ignore
    public User(String firstName) {
        this.firstName = firstName;
    }

    public User( String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;

    }

//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public int getAge() {
//        return age;
//    }
}
