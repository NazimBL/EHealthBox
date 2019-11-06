package com.example.dell.bluetooth;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import com.ubidots.ApiClient;
import com.ubidots.Variable;

import static com.example.dell.bluetooth.UbidtosActivity.API_KEY;

public class GlucoseActivity extends AppCompatActivity {

    VideoView simple;
    ImageButton done,skip;
    EditText gluc;
    final String sugar="5b348d3cc03f970237e3db52";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose);

        skip=(ImageButton)findViewById(R.id.skip_id4);
        done=(ImageButton)findViewById(R.id.done_id4);
        skip.setBackgroundColor(Color.TRANSPARENT);
        done.setBackgroundColor(Color.TRANSPARENT);

        simple=  (VideoView) findViewById(R.id.sugarvideo);

        simple.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sugar));
        simple.start();
        simple.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                simple.start(); //need to make transition seamless.
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
try{
    new ApiUbidots().execute(Integer.parseInt(gluc.getText().toString()));

            }catch(Exception e){
                Toast.makeText(GlucoseActivity.this, "Couldn't send data", Toast.LENGTH_SHORT).show();
            }


                startActivity(new Intent(GlucoseActivity.this,ShowOff.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GlucoseActivity.this,ShowOff.class));
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

            Variable batteryLevel = apiClient.getVariable(sugar);

//            Map<String, Object> contextValue = new HashMap<String,Object>();
//            contextValue.put("","lat:36.832729, lng:3.546992");

            // batteryLevel.saveValue(params[0],contextValue);
            batteryLevel.saveValue(params[0]);
            return null;
        }
    }
}
