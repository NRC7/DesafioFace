package com.desafiolatam.desafioface.views.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.background.RecentUserService;
import com.desafiolatam.desafioface.views.main.MainActivity;
import com.desafiolatam.desafioface.views.login.LoginActivity;


public class SplashActivity extends AppCompatActivity implements LoginCallback {

    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Immersive view
        View view = findViewById(R.id.root);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        intentFilter = new IntentFilter();
        intentFilter.addAction(RecentUserService.USERS_FINISHED);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                if (RecentUserService.USERS_FINISHED.equals(action)) {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                }
            }
        };

        new LoginValidation(this).init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
        Toast.makeText(this, "onResume Splash", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        Toast.makeText(this, "onPause Splash", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signed() {


        /*This works as well, without a service
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }
        }, 1200);
        Toast.makeText(this, "signed", Toast.LENGTH_SHORT).show();*/

        //Service initialization
        RecentUserService.startActionRecentUsers(this);

    }

    @Override
    public void signUp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            }
        }, 1200);
        Toast.makeText(this, "signUp", Toast.LENGTH_SHORT).show();
    }
}
