package com.example.dell.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static BluetoothAdapter BA;
    public static  char[] codes={'1','2'};
    public static Handler myHandler=new Handler();
    public static BluetoothSocket socket;
    public static BluetoothDevice btdevice;
    public static Set<BluetoothDevice> pairedDevices;
    public static boolean connected=false;



    public static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    Button connect,send;
    static TextView text,text2;
    public static Context context;
    public static int i=0;

    public static void myToast(String msg, Context activity){
        Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context=MainActivity.this;

        connect=(Button)findViewById(R.id.con_id);
        send=(Button)findViewById(R.id.send_id);
        text=(TextView)findViewById(R.id.text_id);
        text2=(TextView)findViewById(R.id.text_id2);





        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BA=BluetoothAdapter.getDefaultAdapter();
                if(!connected){
                    if(!BA.isEnabled())turnOnBt();
                    connectToDevice();
                }else turnOffBt();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected){

                 monitor();
                    
                }

                else{
                    myToast(MainActivity.this,"No bluetooth");
                }
            }
        });



    }


    void monitor(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                ThreadCommunication comm=new ThreadCommunication(socket);
                comm.start();
                while(true){

                    if(connected){

                        comm.write(""+codes[i]);
                        i++;
                        if(i>1)i=0;

                    }else myToast(MainActivity.this,"Error");


                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        }).start();


    }

    public static void myToast(Context context,String msg){

        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }
    void connectToDevice(){
        pairedDevices = BA.getBondedDevices();
        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices) {
            list.add(bt.getName());

            if (bt.getName().equals("WameedhBT_2")) {
                btdevice = bt;
                Log.d("Nazim", "detected");
            }

        }
        if (btdevice != null) {
            ThreadConnection connection = new ThreadConnection(btdevice);
            connection.start();

        }
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
}



