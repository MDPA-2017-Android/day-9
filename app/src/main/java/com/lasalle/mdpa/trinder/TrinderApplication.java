package com.lasalle.mdpa.trinder;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.lasalle.mdpa.trinder.manager.UserManager;
import com.lasalle.mdpa.trinder.network.AuthRest;

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
            bind(AuthRest.class).toInstance(new AuthRest());
            bind(UserManager.class);
            /*
            bind(UserManager.class).to(UserManagerImpl.class);
            bind(BudgetingDatabase.class).toInstance(createBudgetingDatabaseInstance());
            bind(BudgetRestAPI.class).toInstance(new BudgetRestApiRetrofit());
            bind(BudgetManager.class).to(BudgetManagerImpl.class);
            bind(SharedPreferences.class).toInstance(initSharedPreferences());
            */
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
