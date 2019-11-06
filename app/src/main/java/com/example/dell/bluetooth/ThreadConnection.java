package com.example.dell.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by DELL on 12/06/2018.
 */

public class ThreadConnection extends Thread {

    private BluetoothSocket bluetoothSocket = null;
    private BluetoothDevice bluetoothDev;
    private BluetoothAdapter BA;


    public ThreadConnection(BluetoothDevice bD) {
        this.bluetoothDev = bD;
        BluetoothSocket socket=null;


        try{
            BA=BluetoothAdapter.getDefaultAdapter();
            bluetoothDev=BA.getRemoteDevice(bluetoothDev.getAddress());
            socket=bluetoothDev.createInsecureRfcommSocketToServiceRecord(MainActivity.MY_UUID_SECURE);


        }catch (Exception e){
            e.printStackTrace();
        }
        bluetoothSocket=socket;
    }

    @Override
    public void run() {


        try{
            bluetoothSocket.connect();
            MainActivity.socket=bluetoothSocket;
            MainActivity.connected=true;
            MainActivity.myHandler.post(new Runnable() {
                @Override
                public void run() {

                   Toast.makeText(MainMenu.context,"Connected",Toast.LENGTH_SHORT).show();
                    MainMenu.paired=true;

                }
            });

        }catch (Exception e){
            MainMenu.connected=false;
            MainMenu.myHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainMenu.context,"Connection Error Try again",Toast.LENGTH_SHORT).show();

                }
            });

            try{
                bluetoothSocket.close();

            }catch (Exception ex){
                Log.d("Nazim",ex.toString());
            }
        }

    }
}
