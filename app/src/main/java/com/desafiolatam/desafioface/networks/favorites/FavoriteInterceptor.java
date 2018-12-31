package com.desafiolatam.desafioface.networks.favorites;

import com.desafiolatam.desafioface.data.CurrentUserQueries;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.desafiolatam.desafioface.networks.sessions.LoginInterceptor.BASE_URL;

public class FavoriteInterceptor {

    //Put or Post cant retry because you can get a duplicated object

    public Favorites get() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("authtoken", new CurrentUserQueries().get().getAuth_token())
                        .header("Source", "mobile")
                        .build();

                Response response = chain.proceed(request);

                return response;
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Favorites service = interceptor.create(Favorites.class);

        return service;
    }
}
