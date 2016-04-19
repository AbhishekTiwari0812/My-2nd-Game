package com.example.abhishek.game;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter adapter;
    TextView rssi_value_viewer;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rssi_value_viewer = (TextView) findViewById(R.id.tv_device_info);
        answer = "";
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    //called when Start scanning button is clicked.
    void start_scanner(View v) {
        adapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, filter);
        Thread t = new Thread() {
            @Override
            public void run() {
                int count = 0;
                while (count < 10000) {
                    count++;
                    adapter.startDiscovery();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(getApplicationContext(), "Started discovery", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //p("finished searching for devices nearby");
                Toast.makeText(getApplicationContext(), "finished  discovery", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                answer = "Device name:" + device.getName() + "\nRSSI=" + (intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)) + "\n";
                //p("Device name:" + device.getName());
                //p("RSSI=" + intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                rssi_value_viewer.setText(rssi_value_viewer.getText() + "\n" + answer);
            }
        }
    };

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            //broadcastReceiver was not registered!
        }
        super.onDestroy();
    }

    public void start_sending(View v) {
        p("This works!");
    }

    void p(String str) {
        System.out.println("" + str);
    }
}
