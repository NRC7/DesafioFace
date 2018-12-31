package com.desafiolatam.desafioface.networks.favorites;

import android.os.AsyncTask;
import android.util.Log;

import com.desafiolatam.desafioface.models.Developer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class PutFcmToken extends AsyncTask<String, Integer, Integer> {

    @Override
    protected Integer doInBackground(String... strings) {
        int code = 111;
        String token = strings[0];
        Favorites favorites = new FavoriteInterceptor().get();
        Call<String> call = favorites.putFcmToken(token);
        try {
            Response<String> response = call.execute();
            code = response.code();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
