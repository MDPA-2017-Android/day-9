package com.lasalle.mdpa.trinder.network;

import android.arch.lifecycle.Observer;
import android.util.Log;

import com.lasalle.mdpa.trinder.model.User;
import com.lasalle.mdpa.trinder.network.interceptor.AuthTokenInterceptor;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import toothpick.Toothpick;

@Singleton
public class ProfileRest {

    @Inject AuthTokenInterceptor authTokenInterceptor;

    protected interface RestAPI {

        @GET("profile")
        Call<User> fetchProfile();

    }

    private Retrofit retrofit;
    private RestAPI restAPI;

    private void lazyInit() {
        if(retrofit != null) {
            return;
        }

        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(authTokenInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://mdpa17.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        restAPI = retrofit.create(RestAPI.class);
    }

    public void fetchProfile(Observer<User> observer) {
        lazyInit();

        restAPI.fetchProfile().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                observer.onChanged(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TrinderApplication", t.getMessage());
                observer.onChanged(null);
            }
        });
    }

}
