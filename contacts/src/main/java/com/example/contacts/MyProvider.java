package com.example.contacts;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {

    /**
     * 新增4个微型常量
     * TABLE1_DIR ：访问table1表的全部数据
     * TABLE1_ITEM ：访问table1表中的单条数据
     * TABLE2_DIR ：访问table2表的全部数据
     * TABLE2_ITEM ：访问table2表中的单条数据
     * */
    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    public static UriMatcher uriMatcher;  // 创建 UriMatcher 实例

    static {
        /**
         * 对 UriMatcher 实例进行初始化；
         * 调用addURI()将期望匹配的内容URI格式传入进去；
         * 传入的参数可以使用通配符；
         * 当调用query()方法时会通过 UriMatcher 对象的match()方法对传入的URI，
         * 与addURI()事先传入的进行匹配，如果匹配成功会返回自定义代码，从而进行对应操作；
         * delete()、insert()、update()的使用与query()相似
         * */
        uriMatcher = new UriMatcher(uriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.app.provide", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.app.provide", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.app.provide", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.app.provide", "table2/#", TABLE2_ITEM);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR :
                // 查询table1表中的所有数据
                break;
            case TABLE1_ITEM :
                // 查询table1表中的单条数据
                break;
            case TABLE2_DIR :
                // 查询table2表中的所有数据
                break;
            case TABLE2_ITEM :
                // 查询table2表中的单条数据
                break;
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
    /**
     * 关于getType
     * 作用 ：用于获取Uri对象所对应的MIME类型
     * 内容URI对应的MIME组成 ：
     *      必须以vnd开头
     *      如果内容URI以路径结尾，则后接android. cursor.dir/,如果内容URI以id结尾,则后接android. cursor.item/
     *      最后接上 vnd.<authority>.<path>
     *   例 ：content://com.example.app.provider/table1 的MIME类型 vnd.android. cursor.dir/vnd.com.example.app.provider.table1
     *   例 ：content://com.example.app.provider/table1/1 的MIME类型 vnd.android. cursor.item/vnd.com.example.app.provider.table1
     * */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR :
                return "vnd.android. cursor.dir/vnd.com.example.app.provider.table1";
            case TABLE1_ITEM :
                return "vnd.android. cursor.item/vnd.com.example.app.provider.table1";
            case TABLE2_DIR :
                return "vnd.android. cursor.dir/vnd.com.example.app.provider.table2";
            case TABLE2_ITEM :
                return "vnd.android. cursor.item/vnd.com.example.app.provider.table2";
            default:
                break;
        }
        return null;
    }
}
