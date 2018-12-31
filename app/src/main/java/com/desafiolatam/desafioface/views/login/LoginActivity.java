package com.desafiolatam.desafioface.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.background.RecentUserService;
import com.desafiolatam.desafioface.data.FcmToken;
import com.desafiolatam.desafioface.networks.favorites.PutFcmToken;
import com.desafiolatam.desafioface.views.main.MainActivity;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity implements SessionCallback{

    private TextInputLayout emailTil;
    private TextInputLayout passwordTil;
    private EditText emailEt;
    private EditText passwordEt;
    private Button signBtn;
    //This are the variables necessary for receiving the broadcast from RecentUserService
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Immersive view
        View view = findViewById(R.id.root);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        emailTil = findViewById(R.id.emailTil);
        passwordTil = findViewById(R.id.passwordTil);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        signBtn = findViewById(R.id.signBtn);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                emailTil.setVisibility(View.GONE);
                passwordTil.setVisibility(View.GONE);
                signBtn.setVisibility(View.GONE);

                //param requerid is not callback here is context instead
                new Signin(LoginActivity.this).toServer(email, password);

            }
        });

        //This is the filter what broadcast should receive
        intentFilter = new IntentFilter();
        intentFilter.addAction(RecentUserService.USERS_FINISHED);

        //Then set the action
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (RecentUserService.USERS_FINISHED.equals(action)) {

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };



    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void restoredView() {

        emailEt.setError(null);
        passwordEt.setError(null);
        emailTil.setVisibility(View.VISIBLE);
        passwordTil.setVisibility(View.VISIBLE);
        signBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void requeriedField() {
        restoredView();
        emailEt.setError("REQUERIDO");
        passwordEt.setError("REQUERIDO");


    }

    @Override
    public void mailFormat() {

        restoredView();
        emailEt.setError("Formato incorrecto");

    }

    @Override
    public void success() {

        String fcmToken = new FcmToken(this).get();
        new PutFcmToken().execute(fcmToken);

        RecentUserService.startActionRecentUsers(this);

        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure() {

        restoredView();
        Toast.makeText(this, "Mail o password incorrectos", Toast.LENGTH_SHORT).show();
    }
}
