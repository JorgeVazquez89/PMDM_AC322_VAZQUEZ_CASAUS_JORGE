package com.example.pmdm_ac322_vazquez_casaus_jorge;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class BoundedService extends Service {
    private IBinder binder = new ServiceBinder();
    private boolean binded = false;
    public class ServiceBinder extends Binder {
        public BoundedService getService() {
            return BoundedService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        binded = true;
        Toast.makeText(this, "Servicio Enlazado", Toast.LENGTH_SHORT).show();
        return binder;
    }

    @Nullable
    @Override
    public void onRebind(Intent intent) {
        binded = true;
        Toast.makeText(this, "Servicio Reenlazado", Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }

    @Nullable
    @Override
    public boolean onUnbind(Intent intent) {
        binded = false;
        Toast.makeText(this, "Servicio Desenlazado", Toast.LENGTH_SHORT).show();
        return binded;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio Creado", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Servicio Iniciado", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if(!binded){
            Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
            super.onDestroy();
        }
    }
}
