package com.desafiolatam.desafioface.fcm;

import android.util.Log;

import com.desafiolatam.desafioface.data.FcmToken;
import com.google.firebase.messaging.FirebaseMessagingService;


public class IdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d("TOKEN",s);
        new FcmToken(this).save(s);
    }
}
