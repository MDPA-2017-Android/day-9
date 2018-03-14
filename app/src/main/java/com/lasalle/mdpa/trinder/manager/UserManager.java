package com.lasalle.mdpa.trinder.manager;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lasalle.mdpa.trinder.network.AuthRest;

import javax.inject.Inject;

public class UserManager {

    @Inject AuthRest authRest;

    public void login(String username, String password) {
        authRest.loginBasicUser(username, password, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("TrinderApplication", s);
            }
        });
    }

}
