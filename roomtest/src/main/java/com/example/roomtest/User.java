package com.example.roomtest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")     // 申明为实体类，指定数据表名
public class User {
    @PrimaryKey(autoGenerate = true)    // 设置为主键，自动生成
    @ColumnInfo(name = "userId")        // 指定列名称
    public int id;                      // 注意，当属性指定为 private 时需要创建对应的get 、set 方法

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "age")
    public int age ;

    // 初始化中不能出现 _firstName 带下划线的变量（有特殊含义）
    public User( String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
