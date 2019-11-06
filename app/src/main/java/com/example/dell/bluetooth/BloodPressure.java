package com.example.dell.bluetooth;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import com.ubidots.ApiClient;
import com.ubidots.Variable;

import java.util.HashMap;
import java.util.Map;

import static com.example.dell.bluetooth.UbidtosActivity.API_KEY;

public class BloodPressure extends AppCompatActivity {

    Button start;
    ImageButton Done,skip;
    EditText edit,edit1;
    final String pressure_ID="5b348d4ac03f97013ac02d12";

    VideoView simple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

       // start=(Button)findViewById(R.id.start_id);
        skip=(ImageButton)findViewById(R.id.skip_id3);
        Done=(ImageButton)findViewById(R.id.done_id3);
        skip.setBackgroundColor(Color.TRANSPARENT);
        edit=(EditText)findViewById(R.id.editText1);
        edit1=(EditText)findViewById(R.id.editText2);
        Done.setBackgroundColor(Color.TRANSPARENT);
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //ThreadCommunication threadCommunication=new ThreadCommunication(MainActivity.socket);
//                //threadCommunication.write("d");
//                //digital write tension
//            }
//        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              // ThreadCommunication threadCommunication=new ThreadCommunication(MainActivity.socket);
                //threadCommunication.write("x");

                startActivity(new Intent(BloodPressure.this,HeartBeat.class));
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    new ApiUbidots().execute(Integer.parseInt(edit.getText().toString()));
                   // new ApiUbidots().execute(Integer.parseInt(edit1.getText().toString()));
//                    ThreadCommunication threadCommunication=new ThreadCommunication(MainActivity.socket);
//                    threadCommunication.write("c");
                    startActivity(new Intent(BloodPressure.this,HeartBeat.class));

                }catch(Exception e){
                    Toast.makeText(BloodPressure.this, "Couldn't send data", Toast.LENGTH_SHORT).show();
                }
            }
        });




        simple=  (VideoView) findViewById(R.id.pressvideo);
        simple.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.pressur));
        simple.start();
        simple.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                simple.start(); //need to make transition seamless.
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(simple.isPlaying())simple.pause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(simple.isPlaying())simple.resume();
        else simple.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(simple.isPlaying())simple.resume();
        else simple.start();
    }

    public class ApiUbidots extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... params) {
            ApiClient apiClient = new ApiClient(API_KEY);

            Variable batteryLevel = apiClient.getVariable(pressure_ID);

//            Map<String, Object> contextValue = new HashMap<String,Object>();
//            contextValue.put("","lat:36.832729, lng:3.546992");

           // batteryLevel.saveValue(params[0],contextValue);
            batteryLevel.saveValue(params[0]);
            return null;
        }
    }
}
