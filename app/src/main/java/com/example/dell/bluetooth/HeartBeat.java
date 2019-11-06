package com.example.dell.bluetooth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class HeartBeat extends Activity {

    ImageButton done,skip;

    VideoView simple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat);

        skip=(ImageButton)findViewById(R.id.skip_id2);
        done=(ImageButton)findViewById(R.id.done_id2);
        skip.setBackgroundColor(Color.TRANSPARENT);
        done.setBackgroundColor(Color.TRANSPARENT);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ThreadCommunication threadCommunication=new ThreadCommunication(MainActivity.socket);
                threadCommunication.write("p");
                startActivity(new Intent(HeartBeat.this,GlucoseActivity.class));
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ThreadCommunication threadCommunication=new ThreadCommunication(MainActivity.socket);
                //threadCommunication.write("x");
                startActivity(new Intent(HeartBeat.this,GlucoseActivity.class));
            }
        });

        simple=  (VideoView) findViewById(R.id.heartvideo);

        simple.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hear));
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
}
