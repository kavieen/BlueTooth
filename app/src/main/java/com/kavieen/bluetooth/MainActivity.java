package com.kavieen.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static final private int  REQUEST_ENABLE_BT = 0x02;
    private Button searchBtn;
    private ListView oldDeviceLV;
    private ListView newDeviceLV;
    private BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        oldDeviceLV = (ListView) findViewById(R.id.oldDeviceLV);
        newDeviceLV = (ListView) findViewById(R.id.newDeviceLV);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //检查是否支持蓝牙设备
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, R.string.deviceNoBluetooth,Toast.LENGTH_SHORT).show();
        }
        else{
            //启动蓝牙
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            switch (resultCode){
                case  RESULT_OK:
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                    // 如果存在已经配对的蓝牙设备
                    if (pairedDevices.size() > 0) {
                        // Loop through paired devices
                        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
                        for (BluetoothDevice device : pairedDevices) {
                            // Add the name and address to an array adapter to show in a ListView
                            adapter.add(getString(R.string.blueName)+device.getName() + getString(R.string.blueAddress) + device.getAddress());
                        }
                        oldDeviceLV.setAdapter(adapter);
                    }
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, R.string.blueToothNoOpen,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.searchBtn:
                break;
            default:break;
        }
    }
}
