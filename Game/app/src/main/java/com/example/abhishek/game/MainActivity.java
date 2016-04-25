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
    static int BulletsAllowed;
    double count_rssi_values;
    double cumulative_power;

    /*
    * Receives bluetooth broadcast
     * updates the score of player
    * */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //Toast.makeText(getApplicationContext(), "Started discovery", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Toast.makeText(getApplicationContext(), "finished  discovery", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                short RSSI_STRENGTH = (intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));

                RSSI_STRENGTH += (100.0);

                answer = "Device name:" + device.getName() + "\nRSSI=" + RSSI_STRENGTH + "\n";

                cumulative_power += Math.sqrt(Math.abs((double) RSSI_STRENGTH));

                answer += "cumulative power" + cumulative_power + "\n";

                count_rssi_values += (1.0d);

                BulletsAllowed += Math.floor(cumulative_power / count_rssi_values);

                p("Bullets:" + BulletsAllowed);
                rssi_value_viewer.setText(rssi_value_viewer.getText() + "\n" + answer);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rssi_value_viewer = (TextView) findViewById(R.id.tv_device_info);
        answer = "";
        count_rssi_values = 0;
        //starts decreasing player's score
        Thread decrease_power = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BulletsAllowed--;
                }
            }
        });
        decrease_power.start();

    }
    //called when Start scanning button is clicked.
    void start_scanner(View v) {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {     //if the android device doesn't have a bluetooth device.
            Toast.makeText(getApplicationContext(), "No bluetooth available in this device", Toast.LENGTH_LONG).show();
        } else if (!adapter.isEnabled()) {
            p("Trying to enable bluetooth");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        } else {
            finally_start_scan();
        }
    }


    //after the bluetooth started
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Enable bluetooth to get support", Toast.LENGTH_LONG).show();
        }
        finally_start_scan();

    }

    //scanning the vicinity for supporters
    void finally_start_scan() {
        Toast.makeText(getApplicationContext(), "Seeking supporters to become more VICIOUS...", Toast.LENGTH_LONG).show();
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

    //on click listener for starting the game.
    void start_game(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

    //release the resources before destroying the app.
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
