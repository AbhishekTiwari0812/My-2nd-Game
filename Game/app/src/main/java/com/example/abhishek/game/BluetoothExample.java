package com.example.abhishek.game;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by Abhishek on 15-04-2016.
 */
public class BluetoothExample {
    //first get the bluetooth adapter
    BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();


    //take action based on broadcast messages from the bluetooth device of the phone.
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                p("Started discovery");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                p("finished searching for devices nearby");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //if a new device is found...
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                p("Device name:" + device.getName());
                p("RSSI=" + device.EXTRA_RSSI);
            }
        }
    };


    void p(String str) {
        System.out.println("" + str);
    }
}
