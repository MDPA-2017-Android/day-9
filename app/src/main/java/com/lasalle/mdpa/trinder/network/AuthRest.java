package com.lasalle.mdpa.trinder.network;

import android.arch.lifecycle.Observer;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class AuthRest {

    protected interface RestAPI {

        @POST("auth")
        @FormUrlEncoded
        Call<ResponseBody> login(@Field(value="type") String type,
                                 @Field(value="username") String username,
                                 @Field(value="password") String password);

    }

    private Retrofit retrofit;
    private RestAPI restAPI;

    public AuthRest() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://mdpa17.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restAPI = retrofit.create(RestAPI.class);
    }

    public void loginBasicUser(String username, String password, Observer<String> observer) {
        restAPI.login("basic", username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String bodyContent = response.body().string();
                    String jwt = new JSONObject(bodyContent).getString("jwt");
                    observer.onChanged(jwt);
                } catch (IOException e) {
                    e.printStackTrace();
                    observer.onChanged(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                    observer.onChanged(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TrinderApplication", t.getMessage());
                observer.onChanged(null);
            }
        });
    }

}
