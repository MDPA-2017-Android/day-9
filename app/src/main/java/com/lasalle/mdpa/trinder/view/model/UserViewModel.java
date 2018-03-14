package com.lasalle.mdpa.trinder.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.lasalle.mdpa.trinder.manager.UserManager;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class UserViewModel extends AndroidViewModel {

    @Inject UserManager userManager;

    public UserViewModel(@NonNull Application application) {
        super(application);

        Scope scope = Toothpick.openScopes(application, this);
        Toothpick.inject(this, scope);
    }

    public void login(String username, String password) {
        userManager.login(username, password);
    }
}
