package com.example.pmdm_ac322_vazquez_casaus_jorge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.pmdm_ac322_vazquez_casaus_jorge.BoundedService.ServiceBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private boolean binded = false;
    private BoundedService boundedService = new BoundedService();
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binded = true;
            ServiceBinder binder = (ServiceBinder) service;
            boundedService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
            boundedService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = findViewById(R.id.startServiceButton);
        startService.setOnClickListener(this);
        Button stopService = findViewById(R.id.stopServiceButton);
        stopService.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!binded) {
            intent = new Intent(MainActivity.this, BoundedService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binded) {
            binded = false;
            unbindService(connection);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binded = false;
        unbindService(connection);
        stopService(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startServiceButton:
                intent = new Intent(MainActivity.this, BoundedService.class);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                binded = true;
                startService(intent);
                break;
            case R.id.stopServiceButton:
                if (binded) {
                    unbindService(connection);
                    binded = false;
                    stopService(intent);
                }
                break;
        }
    }
}