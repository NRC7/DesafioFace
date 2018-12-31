package com.desafiolatam.desafioface.fcm;

import android.util.Log;

import com.desafiolatam.desafioface.notificactions.FavoriteNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        //TODO get the notificacion
        String email = remoteMessage.getData().get("email");
        //This String.valueOf is just in case of reciving a notification that comes from console instead of server or a server notification without get("email")
        //that will be equal to null and app will crash
        Log.d("EMAIL", String.valueOf(email));

        //This trigger notification
        FavoriteNotification.notify(this, email);
    }
}
