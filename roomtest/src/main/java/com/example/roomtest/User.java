package com.example.roomtest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "users")     // 申明为实体类，指定数据表名
public class User {

    @PrimaryKey(autoGenerate = true)    // 设置为主键，自动生成
    @ColumnInfo(name = "userid")        // 指定 column 名（列名） 为 userid
    private final long id = 0;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "age")
    private int age ;

    @ColumnInfo(name = "last_update")
    private Date mDate;

    @Ignore
    public User(String _firstName) {
        this.firstName = _firstName;
    }

    public User(String _firstName, String _lastName, int _age, Date _mDate) {
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.age = _age;
        this.mDate = _mDate;

    }
}
