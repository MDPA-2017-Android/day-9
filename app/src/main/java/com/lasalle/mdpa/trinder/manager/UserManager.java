package com.lasalle.mdpa.trinder.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lasalle.mdpa.trinder.model.User;
import com.lasalle.mdpa.trinder.network.AuthRest;
import com.lasalle.mdpa.trinder.network.ProfileRest;
import com.lasalle.mdpa.trinder.network.interceptor.AuthTokenInterceptor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserManager {

    @Inject AuthRest authRest;
    @Inject AuthTokenInterceptor authTokenInterceptor;
    @Inject ProfileRest profileRest;

    private MutableLiveData<User> userMutableLiveData;

    public LiveData<User> getUser() {
        return userMutableLiveData;
    }

    public void login(String username, String password) {
        // TODO: We should verify if we already have a token and if it has expired or not

        authRest.loginBasicUser(username, password, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // TODO: We should store the token in a SharedPref or account manager

                // Set the token so it can be used from now on
                authTokenInterceptor.setSessionToken(s);

                // Now we request for the user profile
                profileRest.fetchProfile(user -> {
                    userMutableLiveData.postValue(user);
                });
            }
        });
    }

}
