package com.example.dell.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.dell.bluetooth.MainActivity.BA;
import static com.example.dell.bluetooth.MainActivity.btdevice;
import static com.example.dell.bluetooth.MainActivity.connected;
import static com.example.dell.bluetooth.MainActivity.pairedDevices;
import static com.example.dell.bluetooth.MainActivity.socket;

public class MainMenu extends Activity {

    public static boolean connected=false,paired=false;
    public static Handler myHandler=new Handler();
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

       // waitAGodDamnMinute();
        context=MainMenu.this;

        ConstraintLayout layout=(ConstraintLayout)findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!paired){
                    BA= BluetoothAdapter.getDefaultAdapter();
                    if(!connected){
                        if(!BA.isEnabled())turnOnBt();
                        connectToDevice();
                    }else turnOffBt();
                }else startActivity(new Intent(MainMenu.this,TemperatureActivity.class));
               // startActivity(new Intent(MainMenu.this,TemperatureActivity.class));

            }
        });
    }

    public void turnOnBt(){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turning On Bluetooth",Toast.LENGTH_LONG).show();
        }
    }
    void turnOffBt(){

        if(BA.isEnabled() && connected){
            connected=false;
            try{
                socket.close();
            }catch (Exception e){
            }
        }

    }
    void connectToDevice(){
        pairedDevices = BA.getBondedDevices();
        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices) {
            list.add(bt.getName());

            if (bt.getName().equals("WameedhBT_1")) {
                btdevice = bt;
                Log.d("Nazim", "detected");
            }
        }
        if (btdevice != null) {
            ThreadConnection connection = new ThreadConnection(btdevice);
            connection.start();
            //startActivity(new Intent(MainMenu.this,TemperatureActivity.class));

        }
    }

    void waitAGodDamnMinute(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000);
                    startActivity(new Intent(MainMenu.this,HeartBeat.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
