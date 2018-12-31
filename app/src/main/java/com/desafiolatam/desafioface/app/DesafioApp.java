package com.desafiolatam.desafioface.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.views.login.LoginActivity;
import com.orm.SugarApp;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

//Session expired behavior
//On Manifest name is replaced for .app.DesafioApp
//Sugar still works because is extending in scale, DesafioApp from SugarApp from Application
public class DesafioApp extends SugarApp {

    public static final String SESSION_EXPIRED = "com.desafiolatam.desafioface.app.action.SESSION_EXPIRED";
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        
        intentFilter = new IntentFilter();
        intentFilter.addAction(SESSION_EXPIRED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (SESSION_EXPIRED.equals(intent.getAction())) {
                    //Here user session is closed
                    CurrentUser.deleteAll(CurrentUser.class);
                    //Here is going to LoginActivity
                    Intent goToLogin = new Intent(context, LoginActivity.class);
                    goToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(goToLogin);
                    Toast.makeText(context, "Sesión Expirada", Toast.LENGTH_SHORT).show();
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
