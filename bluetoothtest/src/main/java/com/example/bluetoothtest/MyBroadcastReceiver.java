package com.example.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private List<BluetoothDevice> bluetoothDevices = new ArrayList<>();

    // 用于外部获取远程蓝牙设备对象
    public List<BluetoothDevice> getBluetoothDevices() {
        return bluetoothDevices;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyBroadcastReceiver", "接收到信息");
        switch (Objects.requireNonNull(intent.getAction())) {
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED :
                bluetoothDevices.clear();
                Log.d("MyBroadcastReceiver", "开始扫描信号接收成功:");
//                Toast.makeText(context, "开始扫描信号接收成功", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED :
                Log.d("MyBroadcastReceiver", "扫描结束信号接收成功");
//                Toast.makeText(context, "扫描结束信号接收成功", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothDevice.ACTION_FOUND :
                String remoteDeviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BluetoothDevice remoteBevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (remoteDeviceName != null && remoteBevice != null) {
                    bluetoothDevices.add(remoteBevice);
                    Log.d("MyBroadcastReceiver", "remoteDeviceName:"+remoteDeviceName);
                }
                break;
            default:
                Log.d("MyBroadcastReceiver", "未知广播，action:"+intent.getAction());
                break;
        }
    }
}
