package com.desafiolatam.desafioface.networks.favorites;

import com.desafiolatam.desafioface.models.Developer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Favorites {

    //Both use the same interceptor because both have same behavior and use the same headers
    //So, they can be inside the same interface

    @FormUrlEncoded
    @PUT("users/fcm_token")
    Call<String> putFcmToken(@Field("users[fcm_token]") String token);

    //@Path is for dynamic portion of url, the one espected to change
    @POST("users/{id}/favorite")
    Call<Developer> postFavorite(@Path("id") long id);

}
