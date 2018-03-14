package com.lasalle.mdpa.busybudgeter;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.lasalle.mdpa.busybudgeter.database.BudgetingDatabase;
import com.lasalle.mdpa.busybudgeter.manager.BudgetManager;
import com.lasalle.mdpa.busybudgeter.manager.UserManager;
import com.lasalle.mdpa.busybudgeter.manager.impl.BudgetManagerImpl;
import com.lasalle.mdpa.busybudgeter.manager.impl.UserManagerImpl;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;
import com.lasalle.mdpa.busybudgeter.network.impl.BudgetRestApiRetrofit;

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
            bind(BudgetRestAPI.class).toInstance(new BudgetRestApiRetrofit());
            bind(BudgetManager.class).to(BudgetManagerImpl.class);
            bind(SharedPreferences.class).toInstance(initSharedPreferences());
        }});
    }

    private BudgetingDatabase createBudgetingDatabaseInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                BudgetingDatabase.class, "database-name").build();
    }

    private SharedPreferences initSharedPreferences() {
        return getSharedPreferences("BudgetingApplication", Context.MODE_PRIVATE);
    }
}
