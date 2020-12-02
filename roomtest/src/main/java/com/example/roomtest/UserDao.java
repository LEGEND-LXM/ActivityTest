package com.example.roomtest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract interface UserDao {

    @Query("SELECT * FROM user")
    public abstract List<User> getAll();

    @Insert
    public abstract void insertAll(User... users);

    @Query("DELETE FROM user")
    public abstract void deleteAll();
}

//@Dao        // 添加 Dao 注解，Room 会将它识别为 Dao
//public interface UserDao {
//
//    @Insert     // 插入数据返回主键ID
//    Long insertUser(User user);
//
//    @Update     // 将数据传入更新数据库
//    void updateUser(User newUser);
//
//    @Delete     // 删除传入对象的数据
//    void deleteUser(User user);
//
//    @Query("SELECT * FROM users")       // 查找所有数据
//    List<User> loadAllUsers();
//
//    @Query("select * from users where age > :age")      // 查找年龄大于age的数据
//    List<User> loadUsersOlderThan(int age);
//
//
//
//    @Query("delete from users where lastName = :lastName")      // 删除指定lastName列数据
//    int deleteUserByLastName(String lastName);                  // 使用非实体类参数来增删改数据时，都需要使用@Query注解
//
//}
