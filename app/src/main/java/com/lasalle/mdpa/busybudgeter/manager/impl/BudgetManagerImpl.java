package com.lasalle.mdpa.busybudgeter.manager.impl;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.lasalle.mdpa.busybudgeter.database.BudgetingDatabase;
import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.manager.BudgetManager;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

public class BudgetManagerImpl implements BudgetManager {

    static final String BUDGET_CACHE_KEY = "budget_cache";
    static final int CACHE_TIME_MS = 5000;


    @Inject BudgetRestAPI budgetRestAPI;
    @Inject SharedPreferences sharedPreferences;
    @Inject BudgetingDatabase budgetingDatabase;

    public BudgetManagerImpl() {
    }

    @Override
    public LiveData<List<Budget>> getBudgetList() {
        return budgetingDatabase.getBudgetDao().getAll();
    }

    @Override
    public void insert(Budget budget) {
        // TODO
    }

    @Override
    public void fetchAll() {
        if(shouldServerBeCalled())
        {
            fetchFromServerAndCache();
        }
    }

    private void fetchFromServerAndCache()
    {
        budgetRestAPI.fetchList(budgets -> {
            // insert into database
            Budget[] budgetArray = new Budget[budgets.size()];
            budgetArray = budgets.toArray(budgetArray);

            new RefreshDatabase().execute(budgetArray);
            recordNewCacheTime();
        });
    }

    private boolean shouldServerBeCalled()
    {
        long lastCachedTime = sharedPreferences.getLong(BUDGET_CACHE_KEY, 0);
        long currentTimeMillis = getCurrentTimeInMillis();

        boolean shouldRefreshCache = (lastCachedTime + CACHE_TIME_MS) < currentTimeMillis;
        return shouldRefreshCache;
    }

    private long getCurrentTimeInMillis()
    {
        long currentTimeMillis = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTimeMillis = Instant.now().toEpochMilli();
        }
        else
        {
            currentTimeMillis = System.currentTimeMillis();
        }
        return currentTimeMillis;
    }

    private void recordNewCacheTime()
    {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putLong(BUDGET_CACHE_KEY, getCurrentTimeInMillis());
        sharedPreferencesEditor.apply();
    }

    private class RefreshDatabase extends AsyncTask<Budget, Void, Void>
    {
        @Override
        protected Void doInBackground(Budget[] budgetList) {
            budgetingDatabase.getBudgetDao().refreshDatabase(budgetList);
            return null;
        }
    }
}
