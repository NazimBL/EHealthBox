package com.example.dell.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by DELL on 12/06/2018.
 */

public class ThreadCommunication extends Thread {

    InputStream connectedinputStream;
    OutputStream connectedoutputStream;
    BluetoothSocket bluetoothSocket;

    public ThreadCommunication(BluetoothSocket connectedbtsocket) {
        //add String op as an argument

        InputStream inputStream = null;
        OutputStream outputStream = null;
        bluetoothSocket = connectedbtsocket;

        try {
            inputStream = connectedbtsocket.getInputStream();
            outputStream = connectedbtsocket.getOutputStream();

        } catch (Exception e) {
            Log.d("Nazim", e.toString());
        }

        connectedinputStream = inputStream;
        connectedoutputStream = outputStream;

    }

    @Override
    public void run() {

        while (true) {
            try {

                InputStreamReader isr = new InputStreamReader(connectedinputStream);
                BufferedReader br = new BufferedReader(isr);
                String read="";
                String line="";
                Log.d("Nazim","index "+MainActivity.i);

                int rec=0;


                //final String readMessage=read;
                //Log.d("dyabond","pre msg :"+readMessage);
                final String readMessage=""+(int)br.read();
                Log.d("dyabond","read return :"+rec);

                MainActivity.myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            //int float_value = Integer.parseInt(readMessage);

                            if(MainActivity.codes[MainActivity.i]=='1') MainActivity.text.setText(readMessage);
                            else if(MainActivity.codes[MainActivity.i]=='2')MainActivity.text2.setText(readMessage);



                        } catch (Exception e) {
                            Log.d("dyabond", "fuckoff exeption:");
                        }
                    }
                });

                    Thread.sleep(30);
            }
            catch (IOException e) {
                Log.d("dyabond", "Input stream was disconnected", e);
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void write(String op){
        try {
            connectedoutputStream.write(op.getBytes());

        } catch (IOException e) {
            Log.d("Nazim",e.toString());
        }
    }

}
