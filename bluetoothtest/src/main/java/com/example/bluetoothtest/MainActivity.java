package com.example.bluetoothtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver broadcastReceiver;  // 定义广播接收器
    private LocalBroadcastManager localBroadcastManager;  // 本地广播管理器

    private BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();  // 定义蓝牙适配器
    private BluetoothSocket bluetoothSocket;  // 建立连接的Bluetooth Socket
    private static final int ENABLE_BLUETOOTH = 1;  // 开启蓝牙请求码
    private static final int DISCOVERY_REQUEST = 2;  // 开启可被发现性请求码

    private static final int PERMISSION_LOCATION = 1;  // 位置权限运行时申请请求码



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);  //获取实例

        Button blueBegin = findViewById(R.id.blueBtn);
        Button blueFind = findViewById(R.id.enableFind);
        Button findRemoteDevice = findViewById(R.id.broadcastBtn);
        Button scanRemoteDevice = findViewById(R.id.scanBtn);

        scanRemoteDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled() && !bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.startDiscovery();  // 开始扫描
                    Toast.makeText(MainActivity.this, "开始扫描", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "蓝牙未打开or已开启扫描", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findRemoteDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBroadcastReceiver();
            }
        });

        blueFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable_discoverable();
            }
        });

        blueBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 运行时位置权限获取并打开蓝牙
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}
                            , PERMISSION_LOCATION);
                } else {
                    initBluetooth();
                }
            }
        });
    }

    // 蓝牙初始化
    private void initBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            // 如果没开启，则开启蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, ENABLE_BLUETOOTH);
        } else {
            // 已打开蓝牙，相关处理逻辑
            Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
        }
    }
    // 开启可被发现性
    private void enable_discoverable() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(intent, DISCOVERY_REQUEST);
    }

    // 开启Activity回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ENABLE_BLUETOOTH :  // 开启蓝牙回调
                if (resultCode == RESULT_OK) {
                    // 成功打开蓝牙，相关运行逻辑
                    Toast.makeText(this, "蓝牙开启成功", Toast.LENGTH_SHORT).show();
                }
            case DISCOVERY_REQUEST :  // 请求可被发现回调
                if (resultCode != RESULT_CANCELED) {
                    // 可被发现性已打开
                    Toast.makeText(this, "可被发现"+resultCode, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "不可被发现", Toast.LENGTH_SHORT).show();
                }
        }
    }
    // 运行时回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_LOCATION :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initBluetooth();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
        }
    }

    // 监听蓝牙连接
    private UUID startServiceSocket() {
        String name = "bluetoothService";
        UUID uuid = UUID.randomUUID();
        try {
            final BluetoothServerSocket bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, uuid);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 监听连接
                        bluetoothSocket = bluetoothServerSocket.accept();
                        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
                        // 开始监听信息
                        listenForMessages();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    // 连接远程设备
    private void connectToServiceSocket(BluetoothDevice device, UUID uuid) {
        try {
            // 获取BluetoothSocket对象
            BluetoothSocket clientSocket = device.createRfcommSocketToServiceRecord(uuid);
            // 开始尝试连接
            clientSocket.connect();
            // 开始接收信息
            listenForMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 接收信息
    private void listenForMessages() {
        // 接收消息逻辑
    }

    // 开启广播接收器，处理发现信息
    private void initBroadcastReceiver() {
        Log.d("MyBroadcastReceiver", "开始初始化广播接收器");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);   // 添加过滤条件（开始查询）
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);  // 添加过滤条件（结束查询）
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);  // 添加过滤条件（查询中）
        broadcastReceiver = new MyBroadcastReceiver();
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);  // 注册广播接收器
        Log.d("MyBroadcastReceiver", "广播接收器初始化完成");
    }

    // 应用程序销毁前调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
