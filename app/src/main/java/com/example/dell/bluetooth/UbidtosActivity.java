package com.example.dell.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ubidots.ApiClient;
import com.ubidots.Variable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UbidtosActivity extends AppCompatActivity {



    final public  static String TOKEN="A1E-Hcq3ROhzedfaUo30s5lJL7HhBENdQg";
    final public  static String API_KEY="A1E-edde32421cca7949ca5e7a5cc2125f0419b3";
    final public static String VAR_ID="5b32c41dc03f973a034d12bb";

    TextView consumption;
    Button get;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubidtos);
        get=(Button)findViewById(R.id.get_id);
        consumption=(TextView)findViewById(R.id.consumption);



        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int level=46;
                consumption.setText(Integer.toString(level) + "%");
                new ApiUbidots().execute(level);

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {

        super.onStop();
    }
    public class ApiUbidots extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... params) {
            ApiClient apiClient = new ApiClient(API_KEY);
            Variable batteryLevel = apiClient.getVariable(VAR_ID);

            Map<String, Object> contextValue = new HashMap<String,Object>();
            contextValue.put("","lat:36.832729, lng:3.546992");

            batteryLevel.saveValue(params[0],contextValue);
            return null;
        }
    }

    }

