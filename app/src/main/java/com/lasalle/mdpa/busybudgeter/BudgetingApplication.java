package com.lasalle.mdpa.busybudgeter;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lasalle.mdpa.busybudgeter.database.BudgetingDatabase;
import com.lasalle.mdpa.busybudgeter.manager.BudgetManager;
import com.lasalle.mdpa.busybudgeter.manager.UserManager;
import com.lasalle.mdpa.busybudgeter.manager.impl.BudgetManagerImpl;
import com.lasalle.mdpa.busybudgeter.manager.impl.UserManagerImpl;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;
import com.lasalle.mdpa.busybudgeter.network.impl.BudgetRestApiOkHttp;
import com.lasalle.mdpa.busybudgeter.network.impl.BudgetRestApiVolley;

import okhttp3.OkHttpClient;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class BudgetingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Scope applicationScope = Toothpick.openScope(this);
        installToothPickModules(applicationScope);
    }

    public void installToothPickModules(Scope scope) {
        BudgetingApplication application = this;

        scope.installModules(new Module() {{
            bind(UserManager.class).to(UserManagerImpl.class);
            bind(BudgetingDatabase.class).toInstance(createBudgetingDatabaseInstance());
            bind(RequestQueue.class).toInstance(Volley.newRequestQueue(application));
            bind(BudgetRestAPI.class).to(BudgetRestApiOkHttp.class);
            bind(BudgetManager.class).to(BudgetManagerImpl.class);
            bind(OkHttpClient.class).toInstance(new OkHttpClient());
            bind(Gson.class).toInstance(new Gson());
        }});
    }

    private BudgetingDatabase createBudgetingDatabaseInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                BudgetingDatabase.class, "database-name").build();
    }
}
