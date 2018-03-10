package com.lasalle.mdpa.busybudgeter.manager.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lasalle.mdpa.busybudgeter.database.entity.Budget;
import com.lasalle.mdpa.busybudgeter.manager.BudgetManager;
import com.lasalle.mdpa.busybudgeter.network.BudgetRestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

public class BudgetManagerImpl implements BudgetManager {

    @Inject BudgetRestAPI budgetRestAPI;

    private MutableLiveData<List<Budget>> budgetList;

    public BudgetManagerImpl() {
        budgetList = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Budget>> getBudgetList() {
        return budgetList;
    }

    @Override
    public void insert(Budget budget) {

    }

    @Override
    public void fetchAll() {
        budgetRestAPI.fetchList(budgets -> {
            budgetList.postValue(budgets);
        });
    }
}
