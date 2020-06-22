package com.example.runtimepermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button makeButton = findViewById(R.id.make_call);

        makeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0版本以下
                /*try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:10086"));
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }*/

                //6.0及以上版本
                /** 判断用户是否已经授权该权限
                 * 方法：ContextCompat.checkSelfPermission
                 * 参数1 ：context  ； 参数2 ：相关权限名
                 * 将返回值与对应值进行判断，相等则为已授权
                 * 授予后直接执行拨打电话的逻辑
                **/
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
                    /**
                     * 进行权限授予
                     * 方法 ：ActivityCompat.requestPermissions
                     * 参数1 ：Activity实例  ； 参数2 ：申请的权限名，放在数组中   ； 参数3 ：请求码，是唯一值即可（这里用1）
                     * 此方法在调用后会弹出一个权限申请对话框
                     * 无论用户同意或拒绝都会调用onRequestPermissionsResult()方法
                     **/
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }

            }
        });

    }
    /**
     * 拨打电话逻辑
     * */
    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            //此处的电话可以捕获编辑框输入的号码
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 1:  //此处的 1 为请求码，在权限申请时传入
                /**
                 * 判断权限申请返回结果
                 * 返回的值为 grantResults
                 * 下列代码意为 ：同意则调用拨打电话代码，拒绝则显示未授权文本
                 **/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
