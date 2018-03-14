package com.lasalle.mdpa.trinder;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.lasalle.mdpa.trinder.manager.UserManager;
import com.lasalle.mdpa.trinder.network.AuthRest;
import com.lasalle.mdpa.trinder.network.ProfileRest;
import com.lasalle.mdpa.trinder.network.interceptor.AuthTokenInterceptor;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class TrinderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Scope applicationScope = Toothpick.openScope(this);
        installToothPickModules(applicationScope);
    }

    public void installToothPickModules(Scope scope) {
        TrinderApplication application = this;
        scope.installModules(new Module() {{
            bind(UserManager.class);
        }});
    }

    /*
    private BudgetingDatabase createBudgetingDatabaseInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                BudgetingDatabase.class, "database-name").build();
    }
    */

    private SharedPreferences initSharedPreferences() {
        return getSharedPreferences("TrinderApplication", Context.MODE_PRIVATE);
    }
}
