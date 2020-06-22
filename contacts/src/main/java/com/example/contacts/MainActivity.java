package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取ListView控件实例
        ListView contactsView = findViewById(R.id.contacts_view);
        // 初始化适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        // 设置适配器
        contactsView.setAdapter(adapter);
        /**
         * 运行时权限获取
         * 此处授权权限为联系人读取权限
         * */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }

    private void readContacts() {  // 读取联系人列表
        Cursor cursor = null;
        try {
            // 进行联系人查询， "ContactsContract.CommonDataKinds.Phone.CONTENT_URI" 系统包装好的Uri对象
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {  //判断查询结果飞空
                while(cursor.moveToNext()) {  // 非空情况下一项一项读取
                    // 获取联系人名称，"ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME" 联系人姓名这一列对应的常亮
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人电话，"ContactsContract.CommonDataKinds.Phone.NUMBER" 联系人电话这一列对应的常亮
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    // 添加打ListView控件中
                    contactsList.add(displayName + "\n" + number);
                }
                // 刷新ListVIew
                adapter.notifyDataSetChanged();
            }
            } catch (Exception e) {
                e.printStackTrace();
        } finally {
            if (cursor != null) {
                // 最后需要关闭cursor
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 权限授予的结果回调判断
         * */
        switch (requestCode) {
            case 1 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
